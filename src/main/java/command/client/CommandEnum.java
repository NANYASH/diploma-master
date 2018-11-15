package command.client;

import command.ActionCommand;
import command.ForwardCommand;
import command.city.*;
import command.country.*;
import command.order.DeleteOrderCommand;
import command.order.OrderOldListCommand;
import command.order.OrderUpcomingListCommand;
import command.shopping.*;
import command.tour.SearchListCommand;
import command.tour.TourListCommand;
import command.trip.*;
import command.user.*;
import command.vacation.*;

public enum CommandEnum {
    
    /** The login. */
    LOGIN(new LoginCommand()),
    
    /** The logout. */
    LOGOUT(new LogoutCommand()),
    
    /** The register. */
    REGISTER(new RegisterCommand()),
    
    /** The english language. */
    ENGLISH_LANGUAGE(new EnglishLanguageCommand()),
    
    /** The russian language. */
    RUSSIAN_LANGUAGE(new RussianLanguageCommand()),
    
    /** The vacation list. */
    VACATION_LIST(new VacationListCommand()),
    
    /** The vacation full. */
    VACATION_FULL(new VacationFullCommand()),
    
    /** The trip list. */
    TRIP_LIST(new TripListCommand()),
    
    /** The trip full. */
    TRIP_FULL(new TripFullCommand()),
    
    /** The shopping list. */
    SHOPPING_LIST(new ShoppingListCommand()),
    
    /** The shopping full. */
    SHOPPING_FULL(new ShoppingFullCommand()),
    
    /** The tour list. */
    TOUR_LIST(new TourListCommand()),
    
    /** The create vacation. */
    CREATE_VACATION(new CreateVacationCommand()),

    /** The create vacation page. */
    CREATE_VACATION_PAGE(new CreateVacationPageCommand()),
    
    /** The create trip. */
    CREATE_TRIP(new CreateTripCommand()),

    /** The create trip page. */
    CREATE_TRIP_PAGE(new CreateTripPageCommand()),
    
    /** The create shopping. */
    CREATE_SHOPPING(new CreateShoppingCommand()),

    /** The create shopping page. */
    CREATE_SHOPPING_PAGE(new CreateShoppingPageCommand()),

    /** The create country. */
    CREATE_COUNTRY(new CreateCountryCommand()),

    /** The create city. */
    CREATE_CITY(new CreateCityCommand()),

    /** The create city page. */
    CREATE_CITY_PAGE(new CreateCityPageCommand()),
    
    /** The vacation admin list. */
    VACATION_ADMIN_LIST(new VacationAdminListCommand()),
    
    /** The trip admin list. */
    TRIP_ADMIN_LIST(new TripAdminListCommand()),
    
    /** The shopping admin list. */
    SHOPPING_ADMIN_LIST(new ShoppingAdminListCommand()),

    /** The country admin list. */
    COUNTRY_ADMIN_LIST(new CountryAdminListCommand()),

    /** The city admin list. */
    CITY_ADMIN_LIST(new CityAdminListCommand()),
    
    /** The edit vacation page. */
    EDIT_VACATION_PAGE(new EditVacationPageCommand()),
    
    /** The edit vacation. */
    EDIT_VACATION(new EditVacationCommand()),
    
    /** The edit trip page. */
    EDIT_TRIP_PAGE(new EditTripPageCommand()),
    
    /** The edit trip. */
    EDIT_TRIP(new EditTripCommand()),
    
    /** The edit shopping page. */
    EDIT_SHOPPING_PAGE(new EditShoppingPageCommand()),
    
    /** The edit shopping. */
    EDIT_SHOPPING(new EditShoppingCommand()),

    /** The edit country. */
    EDIT_COUNTRY(new EditCountryCommand()),

    /** The edit country page. */
    EDIT_COUNTRY_PAGE(new EditCountryPageCommand()),

    /** The edit city. */
    EDIT_CITY(new EditCityCommand()),

    /** The edit city page. */
    EDIT_CITY_PAGE(new EditCityPageCommand()),
    
    /** The vacation delete admin list. */
    VACATION_DELETE_ADMIN_LIST(new VacationDeleteAdminListCommand()),
    
    /** The trip delete admin list. */
    TRIP_DELETE_ADMIN_LIST(new TripDeleteAdminListCommand()),
    
    /** The shopping delete admin list. */
    SHOPPING_DELETE_ADMIN_LIST(new ShoppingDeleteAdminListCommand()),

    /** The country delete admin list. */
    COUNTRY_DELETE_ADMIN_LIST(new CountryDeleteAdminListCommand()),

    /** The city delete admin list. */
    CITY_DELETE_ADMIN_LIST(new CityDeleteAdminListCommand()),

    /** The delete vacation. */
    DELETE_VACATION(new DeleteVacationCommand()),
    
    /** The delete trip. */
    DELETE_TRIP(new DeleteTripCommand()),
    
    /** The delete shopping. */
    DELETE_SHOPPING(new DeleteShoppingCommand()),

    /** The delete country. */
    DELETE_COUNTRY(new DeleteCountryCommand()),

    /** The delete city. */
    DELETE_CITY(new DeleteCityCommand()),
    
    /** The order vacation. */
    ORDER_VACATION(new OrderVacationCommand()),
    
    /** The order trip. */
    ORDER_TRIP(new OrderTripCommand()),
    
    /** The order shopping. */
    ORDER_SHOPPING(new OrderShoppingCommand()),
    
    /** The upcoming order list. */
    ORDER_LIST_UPCOMING(new OrderUpcomingListCommand()),

    /** The old order list. */
    ORDER_LIST_OLD(new OrderOldListCommand()),
    
    /** The delete order. */
    DELETE_ORDER(new DeleteOrderCommand()),
    
    /** The balance. */
    BALANCE(new BalancePageCommand()),
    
    /** The balance add. */
    BALANCE_ADD(new BalanceAddCommand()),
    
    /** The change password. */
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    
    /** The create user. */
    CREATE_USER(new CreateUserCommand()),
    
    /** The user admin list. */
    USER_ADMIN_LIST(new UserAdminListCommand()),
    
    /** The edit user page. */
    EDIT_USER_PAGE(new EditUserPageCommand()),
    
    /** The edit user. */
    EDIT_USER(new EditUserCommand()),
    
    /** The user delete admin list. */
    USER_DELETE_ADMIN_LIST(new UserDeleteAdminListCommand()),
    
    /** The delete user. */
    DELETE_USER(new DeleteUserCommand()),
    
    /** The search. */
    SEARCH(new SearchListCommand()),
    
    /** The vacation sort. */
    VACATION_SORT(new VacationSortCommand()),
    
    /** The trip sort. */
    TRIP_SORT(new TripSortCommand()),
    
    /** The shopping sort. */
    SHOPPING_SORT(new ShoppingSortCommand()),
    
    /** The forward. */
    FORWARD(new ForwardCommand());
    
    /** The command. */
    ActionCommand command;

    /**
     * СommandEnum constructor.
     *
     * @param command the current command
     */
    CommandEnum(ActionCommand command) {
        this.command = command;
    }

    /**
     * Gets the current command.
     *
     * @return the current command
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
