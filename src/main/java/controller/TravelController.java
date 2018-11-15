package controller;

import command.ActionCommand;
import command.exception.CommandException;
import command.factory.ActionFactory;
import resource.ConfigurationManager;
import resource.MessageManager;
import resource.ResourceManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet(
        urlPatterns = "/travel",
        initParams = @WebInitParam(name = "init_log4j", value = "log4j.xml")
)
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50    // 50MB
)
public class TravelController extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(TravelController.class);
    

    public static ResourceManager resourceManager = ResourceManager.INSTANCE;
    

    public static MessageManager messageManager = MessageManager.INSTANCE;
    
    private final static String LOCALE_RU = "ru_RU";
    
    private final static String LOCALE_RU_VALUE = "ru";


    @Override
    public void init() throws ServletException {
        String prefix = getServletContext().getRealPath("/");
        String filename = getInitParameter("init_log4j");
        if (filename != null) {
            new DOMConfigurator().doConfigure(prefix + filename, LogManager.getLoggerRepository());
        }
    }


    @Override
    public void destroy() {
        super.destroy();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("locale".equals(cookie.getName())){
                request.setAttribute("lang", cookie.getValue());
                if (LOCALE_RU.equals(cookie.getValue())) {
                    TravelController.messageManager.changeResource(new Locale(LOCALE_RU_VALUE, LOCALE_RU_VALUE.toUpperCase()));
                    TravelController.resourceManager.changeResource(new Locale(LOCALE_RU_VALUE, LOCALE_RU_VALUE.toUpperCase()));
                } else {
                    TravelController.messageManager.changeResource(Locale.US);
                    TravelController.resourceManager.changeResource(Locale.US);
                }
            }
        }
        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request, response);
        try {
            page = command.execute(request, response);
        } catch (CommandException e) {
            LOG.error(e);
        }
        if (page != null) {
            LOG.debug("redirect = " + request.getAttribute("redirect"));
            LOG.debug("referer = " + request.getHeader("referer"));
            if (request.getAttribute("redirect") != null){
                response.sendRedirect(request.getAttribute("redirect").toString());
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    messageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}