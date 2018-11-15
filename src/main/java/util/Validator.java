package util;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private final static Logger LOG = Logger.getLogger(Validator.class);

    private final static Pattern pLogin = Pattern.compile("([A-Za-zА-Яа-я0-9_-]){3,16}");

    private final static Pattern pPassword = Pattern.compile("([A-Za-zА-Яа-я0-9_-]){8,20}");

    private final static Pattern pEmail = Pattern.compile("([A-za-z0-9_\\.-]+)@([A-za-z0-9_\\.-]+)\\.([A-za-z\\.]{2,6})");

    private final static Pattern pName = Pattern.compile("([A-Za-zА-Яа-я]){2,25}");

    private final static Pattern pSurname = Pattern.compile("([A-Za-zА-Яа-я]){2,25}");

    private final static Pattern pNameVacation = Pattern.compile("(.){1,45}");

    private final static Pattern pNameCountryAndCity = Pattern.compile("(.){1,45}");

    private final static Pattern pSummary = Pattern.compile("(.){1,255}");

    private final static Pattern pHotel = Pattern.compile("(.){1,100}");

    private final static int PRICE_MIN = 0;

    private final static int PRICE_MAX = 1000000000;

    private final static int ORDER_MIN_QUANTITY = 1;
    
    private final static int ORDER_MAX_QUANTITY = 100;
    
    private final static int BALANCE_MIN_MONEY_TO_ADD = 1;
    
    private final static int BALANCE_MAX_MONEY_TO_ADD = 10000;
    
    private final static int USER_MIN_MONEY_CREATE_USER = 0;
    
    private final static int USER_MAX_MONEY_CREATE_USER = 100000;
    
    private final static int USER_MIN_DISCOUNT = 0;
    
    private final static int USER_MAX_DISCOUNT = 1;


    /**
     * Validate login.
     *
     * @param enterLogin the enter login
     * @return true, if successful
     */
    public static boolean validateLogin(String enterLogin) {
        Matcher mLogin = pLogin.matcher(enterLogin);
        LOG.debug("Validate Login: " + mLogin.matches());
        return mLogin.matches();
    }

    /**
     * Validate password.
     *
     * @param enterPass the enter pass
     * @return true, if successful
     */
    public static boolean validatePassword(String enterPass) {
        Matcher mPassword = pPassword.matcher(enterPass);
        LOG.debug("Validate Password: " + mPassword.matches());
        return mPassword.matches();
    }

    /**
     * Validate email.
     *
     * @param enterEmail the enter email
     * @return true, if successful
     */
    public static boolean validateEmail(String enterEmail) {
        Matcher mEmail = pEmail.matcher(enterEmail);
        LOG.debug("Validate Email: " + mEmail.matches());
        return mEmail.matches();
    }

    /**
     * Validate name.
     *
     * @param enterName the enter name
     * @return true, if successful
     */
    public static boolean validateName(String enterName) {
        Matcher mName = pName.matcher(enterName);
        LOG.debug("Validate Name: " + mName.matches());
        return mName.matches();
    }

    /**
     * Validate surname.
     *
     * @param enterSurname the enter surname
     * @return true, if successful
     */
    public static boolean validateSurname(String enterSurname) {
        Matcher mSurname = pSurname.matcher(enterSurname);
        LOG.debug("Validate Surname: " + mSurname.matches());
        return mSurname.matches();
    }

    /**
     * Validate name tour.
     *
     * @param enterNameTour the enter name tour
     * @return true, if successful
     */
    public static boolean validateNameTour(String enterNameTour) {
        Matcher mNameVacation = pNameVacation.matcher(enterNameTour);
        LOG.debug("Validate NameTour: " + mNameVacation.matches());
        return mNameVacation.matches();
    }

    /**
     * Validate country or city name.
     *
     * @param enterName the enter name
     * @return true, if successful
     */
    public static boolean validateCountryAndCityName(String enterName) {
        Matcher mNameCountryAndCity = pNameCountryAndCity.matcher(enterName);
        LOG.debug("Validate country or city name: " + mNameCountryAndCity.matches());
        return mNameCountryAndCity.matches();
    }

    /**
     * Validate summary.
     *
     * @param enterSummary the enter summary
     * @return true, if successful
     */
    public static boolean validateSummary(String enterSummary) {
        Matcher mSummary = pSummary.matcher(enterSummary);
        LOG.debug("Validate Summary: " + mSummary.matches());
        return mSummary.matches();
    }

    /**
     * Validate price.
     *
     * @param enterPrice the enter price
     * @return true, if successful
     */
    public static boolean validatePrice(String enterPrice) {
        if (enterPrice.isEmpty()){
            return false;
        }
        else {
            double price = Double.parseDouble(enterPrice);
            LOG.debug("Validate Price: " + (price >= PRICE_MIN && price <= PRICE_MAX));
            return price >= PRICE_MIN && price <= PRICE_MAX;
        }
    }

    /**
     * Validate hotel.
     *
     * @param enterHotel the enter hotel
     * @return true, if successful
     */
    public static boolean validateHotel(String enterHotel) {
        Matcher mHotel = pHotel.matcher(enterHotel);
        LOG.debug("Validate Hotel: " + mHotel.matches());
        return mHotel.matches();
    }

    /**
     * Validate string.
     *
     * @param enterString the enter hotel
     * @return true, if successful
     */
    public static boolean validateString(String enterString) {
        LOG.debug("Validate String: " + !enterString.isEmpty());
        return (!enterString.isEmpty());
    }

    /**
     * Validate quantity.
     *
     * @param quantity the quantity
     * @return true, if successful
     */
    public static boolean validateQuantity(int quantity) {
        LOG.debug("Validate quantity: " + (quantity >= ORDER_MIN_QUANTITY && quantity <= ORDER_MAX_QUANTITY));
        return quantity >= ORDER_MIN_QUANTITY && quantity <= ORDER_MAX_QUANTITY;
    }

    /**
     * Validate order date.
     *
     * @param departureDate the departure date
     * @return true, if successful
     */
    public static boolean validateOrderDate(Date departureDate) {
        Date nowDate = new Date();
        LOG.debug("Validate order date: " + (nowDate.before(departureDate)));
        return nowDate.before(departureDate);
    }

    /**
     * Validate balance to add.
     *
     * @param money the money
     * @return true, if successful
     */
    public static boolean validateBalanceToAdd(double money) {
        LOG.debug("Validate money to add: " + (money >= BALANCE_MIN_MONEY_TO_ADD && money <= BALANCE_MAX_MONEY_TO_ADD));
        return money >= BALANCE_MIN_MONEY_TO_ADD && money <= BALANCE_MAX_MONEY_TO_ADD;
    }

    /**
     * Validate user create money.
     *
     * @param money the money
     * @return true, if successful
     */
    public static boolean validateUserCreateMoney(double money) {
        LOG.debug("Validate money create user: " + (money >= USER_MIN_MONEY_CREATE_USER && money <= USER_MAX_MONEY_CREATE_USER));
        return money >= USER_MIN_MONEY_CREATE_USER && money <= USER_MAX_MONEY_CREATE_USER;
    }

    /**
     * Validate user create discount.
     *
     * @param discount the discount
     * @return true, if successful
     */
    public static boolean validateUserCreateDiscount(double discount) {
        LOG.debug("Validate discount create user: " + (discount >= USER_MIN_DISCOUNT && discount <= USER_MAX_DISCOUNT));
        return discount >= USER_MIN_DISCOUNT && discount <= USER_MAX_DISCOUNT;
    }
}
