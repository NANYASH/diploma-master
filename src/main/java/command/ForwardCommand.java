package command;

import resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(ForwardCommand.class);

    private static final String PARAM_NAME_PAGE = "page";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        String requestPage = request.getParameter(PARAM_NAME_PAGE);
        switch (requestPage){
            case "login":
                page = ConfigurationManager.getProperty("path.page.login");
                break;
            case "register":
                page = ConfigurationManager.getProperty("path.page.register");
                break;
            case "main":
                page = ConfigurationManager.getProperty("path.page.main");
                break;
            case "vacations":
                page = ConfigurationManager.getProperty("path.page.vacaton.list");
                break;
            case "trips":
                page = ConfigurationManager.getProperty("path.page.trip.list");
                break;
            case "shoppings":
                page = ConfigurationManager.getProperty("path.page.shopping.list");
                break;
            case "admin_panel":
                page = ConfigurationManager.getProperty("path.page.admin.panel");
                break;
            case "create_vacation":
                page = ConfigurationManager.getProperty("path.page.admin.create.vacation");
                break;
            case "create_trip":
                page = ConfigurationManager.getProperty("path.page.admin.create.trip");
                break;
            case "create_shopping":
                page = ConfigurationManager.getProperty("path.page.admin.create.shopping");
                break;
            case "orders":
                page = ConfigurationManager.getProperty("path.page.orders");
                break;
            case "change_password":
                page = ConfigurationManager.getProperty("path.page.change.password");
                break;
            case "create_user":
                page = ConfigurationManager.getProperty("path.page.admin.create.user");
                break;
            case "create_country":
                page = ConfigurationManager.getProperty("path.page.admin.create.country");
                break;
            default:
                page = ConfigurationManager.getProperty("path.page.login");
                break;
        }
        return page;
    }
}
