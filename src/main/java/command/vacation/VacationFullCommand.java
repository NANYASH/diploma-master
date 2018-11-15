package command.vacation;

import command.ActionCommand;
import command.exception.CommandException;
import entity.User;
import entity.Vacation;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacationFullCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(VacationFullCommand.class);

    private static final String PARAM_NAME_ID = "id";
    
    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        Long iduser = (Long) request.getSession().getAttribute(PARAM_NAME_ID_USER);
        VacationServiceImpl vacationService = new VacationServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        try {
            Vacation vacation = vacationService.findEntityById(id);
            if (id == vacation.getId()) {
                request.setAttribute("vacation", vacation);
                LOG.debug("iduser = " + iduser);
                if (iduser != null) {
                    User user = userService.findEntityById(iduser);
                    LOG.debug("User name: " + user.getName());
                    request.setAttribute("userProfile", user);
                }
                page = ConfigurationManager.getProperty("path.page.vacation.full");
            } else {
                page = ConfigurationManager.getProperty("path.page.main");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
