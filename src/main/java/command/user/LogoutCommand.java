package command.user;

import command.ActionCommand;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutCommand implements ActionCommand {
    
    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.index");
        HttpSession session = request.getSession(true);
        String lang = null;
        if (session.getAttribute("lang") != null) {
            lang = (String) session.getAttribute("lang");
        }
        session.invalidate();

        return page;
    }
}
