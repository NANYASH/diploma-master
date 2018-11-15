package service;

import entity.User;
import entity.UserOrderNumber;
import service.exception.ServiceException;

import java.util.List;


public interface UserService {


    boolean checkCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    boolean checkEditUser(Long enterId, String enterLogin, String enterEmail, String enterName,
                          String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException;

    boolean checkEditUser(Long enterId, String enterLogin, String enterPass, String enterEmail, String enterName,
                          String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException;

    boolean checkBalanceToAdd(Long userId, double money) throws ServiceException;

    boolean checkLogin(String enterLogin, String enterPass) throws ServiceException;


    boolean checkRegister(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname) throws ServiceException;

    double findMoneyByUserId(Long id) throws ServiceException;

    User findUserByName(String name) throws ServiceException;
    User findEntityById(Long id) throws ServiceException;



    List<UserOrderNumber> findAllUsersWithOrderCount() throws ServiceException;




}
