package service.impl;

import dao.exception.DAOException;
import dao.jdbc.JdbcUserDAO;
import entity.User;
import entity.UserOrderNumber;
import service.UserService;
import service.exception.ServiceException;
import util.MD5Util;
import util.Validator;
import util.exception.UtilException;
import org.apache.log4j.Logger;

import java.util.List;


public class UserServiceImpl implements UserService {

    private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private final static int USER_ID_FOR_INSERT = 0;

    private final static int USER_DEFAULT_ROLE = 0;

    private final static double USER_DEFAULT_DISCOUNT = 0;

    private final static int USER_DEFAULT_MONEY = 0;


    @Override
    public User findEntityById(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public boolean checkBalanceToAdd(Long userId, double money) throws ServiceException {
        boolean flag = false;
        if (Validator.validateBalanceToAdd(money)){
            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();

            try {
                if (userDAO.updateUserBalanceAddition(userId, money)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update user balance (adding).", e);
            }
        }
        return flag;
    }


    public static boolean checkPassword(String enterOldPass, String enterNewPass, Long userId) throws ServiceException {
        boolean flag = false;
        if (Validator.validatePassword(enterNewPass)){
            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
            String passwordOld = null;
            try {
                passwordOld = userDAO.findPasswordByUserId(userId);
            } catch (DAOException e) {
                throw new ServiceException("Failed to find password by user id.", e);
            }
            try {
                if (passwordOld.equals(MD5Util.md5Encode(enterOldPass))){
                    if (userDAO.updatePasswordByUserId(userId, MD5Util.md5Encode(enterNewPass))){
                        flag = true;
                    }
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update password by user id.", e);
            } catch (UtilException e) {
                throw new ServiceException(e);
            }
        }
        return flag;
    }

    @Override
    public boolean checkCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            flag = generalCreateUser(enterLogin,enterPass,enterEmail,enterName,enterSurname,enterRole,enterDiscount,enterMoney);
        }
        return flag;
    }


    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkEditUser(Long enterId, String enterLogin, String enterEmail, String enterName,
                                        String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            User user = new User();
            user.setId(enterId);
            user.setLogin(enterLogin);
            user.setRole(enterRole);
            user.setEmail(enterEmail);
            user.setName(enterName);
            user.setSurname(enterSurname);
            user.setDiscount(enterDiscount);
            user.setMoney(enterMoney);

            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
            String password = null;
            try {
                password = userDAO.findPasswordByUserId(user.getId());
                user.setPassword(password);

                if (userDAO.update(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update user.", e);
            }
        }
        return flag;
    }

    @Override
    public boolean checkEditUser(Long enterId, String enterLogin, String enterPass, String enterEmail, String enterName,
                                        String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            try {
            User user = new User();
            user.setId(enterId);
            user.setLogin(enterLogin);
            user.setPassword(MD5Util.md5Encode(enterPass));
            user.setRole(enterRole);
            user.setEmail(enterEmail);
            user.setName(enterName);
            user.setSurname(enterSurname);
            user.setDiscount(enterDiscount);
            user.setMoney(enterMoney);

            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
                if (userDAO.update(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update user.", e);
            } catch (UtilException e) {
                throw new ServiceException(e);
            }
        }
        return flag;
    }


    @Override
    public User findUserByName(String name) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findUserByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public boolean checkLogin(String enterLogin, String enterPass) throws ServiceException {
        boolean flag = false;
        JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
        List<User> users = null;
        try {
            users = userDAO.findAllUsers();
            for (int i = 0; i < users.size(); i++) {
                if (enterLogin.equals(users.get(i).getLogin())) {
                    if (MD5Util.md5Encode(enterPass).equals(users.get(i).getPassword())) {
                        flag = true;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to find all users.", e);
        } catch (UtilException e) {
            throw new ServiceException(e);
        }
        return flag;
    }


    public boolean checkRegister(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname)){
            flag = generalCreateUser(enterLogin, enterPass, enterEmail, enterName, enterSurname, USER_DEFAULT_ROLE, USER_DEFAULT_DISCOUNT, USER_DEFAULT_MONEY);
        }
        return flag;
    }



    @Override
    public List<UserOrderNumber> findAllUsersWithOrderCount() throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findAllUsersWithOrderCount();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public double findMoneyByUserId(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findMoneyByUserId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    private boolean generalCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException{
            boolean flag = false;
            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
            try {
                if (userDAO.findUserByName(enterLogin).getId() == 0) {
                    User user = new User();
                    user.setId(USER_ID_FOR_INSERT);
                    user.setLogin(enterLogin);
                    user.setPassword(MD5Util.md5Encode(enterPass));
                    user.setRole(enterRole);
                    user.setEmail(enterEmail);
                    user.setName(enterName);
                    user.setSurname(enterSurname);
                    user.setDiscount(enterDiscount);
                    user.setMoney(enterMoney);

                    if (userDAO.create(user)) {
                        flag = true;
                    }
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create user (Register).", e);
            } catch (UtilException e) {
                throw new ServiceException(e);
            }
        return flag;
    }
}
