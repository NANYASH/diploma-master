<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />

<header>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="header-content">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only"><fmt:message key="label.toggle-nav" bundle="${ rb }" /></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <c:choose>
                        <c:when test="${ not empty user }">
                            <div class="header-logo"><a href="travel?command=tour_list" class="logo navbar-brand"><img src="/images/logo_gray-blue_epamtravel_80px.svg" width="170px" height="48px"></a></div>
                        </c:when>
                        <c:otherwise>
                            <div class="header-logo"><a href="travel?command=forward&page=login" class="logo navbar-brand"><img src="/images/logo_gray-blue_epamtravel_80px.svg" width="170px" height="48px"></a></div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <div class="header-menu">
                        <div class="nav-menu">
                            <ul class="title-menu">
                                <li class="menu-item"><a href="travel?command=tour_list" class="menu-link"><fmt:message key="menu.item1" bundle="${ rb }" /></a></li>
                                <li class="menu-item"><a href="travel?command=vacation_list" class="menu-link"><fmt:message key="menu.item2" bundle="${ rb }" /></a></li>
                                <li class="menu-item"><a href="travel?command=trip_list" class="menu-link"><fmt:message key="menu.item3" bundle="${ rb }" /></a></li>
                                <li class="menu-item"><a href="travel?command=shopping_list" class="menu-link"><fmt:message key="menu.item4" bundle="${ rb }" /></a></li>
                                <%--<li class="menu-item"><a href="travel?command=forward&page=create_user" class="menu-link"><fmt:message key="test" bundle="${ rb }" /></a></li>--%>
                            </ul>
                        </div>
                        <div class="header-utility">
                            <div class="localization">
                                <a data-toggle="dropdown" href="#"><img class="localization-icon" src="images/globe.png" alt="Localization"></a>
                                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                    <li class="localization-item"><a href="travel?command=english_language" class="localization-link">English</a></li>
                                    <li class="localization-item"><a href="travel?command=russian_language" class="localization-link">Русский</a></li>
                                </ul>
                            </div>
                            <div class="search"><img class="search-icon slide-button" src="images/search.png" alt="Search"></div>
                        </div>
                        <div class="user">
                            <c:choose>
                                <c:when test="${ not empty user }">
                                    <a data-toggle="dropdown" href="#" class="user-link"><div class="username">${user}</div></a>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                        <li class="usermenu-item"><a class="usermenu-link" style="background: #fff;">
                                            <c:choose>
                                            <c:when test="${ role==1 }"><fmt:message key="label.user.menu.admin" bundle="${ rb }" /></c:when>
                                            <c:otherwise><fmt:message key="label.user.menu.user" bundle="${ rb }" /></c:otherwise>
                                            </c:choose>
                                        </a></li>
                                        <li class="usermenu-item divider"></li>
                                        <c:if test="${ role==1 }"><li class="usermenu-item"><a href="travel?command=forward&page=admin_panel" class="usermenu-link"><fmt:message key="label.user.menu.administration" bundle="${ rb }" /></a></li></c:if>
                                        <li class="usermenu-item"><a href="travel?command=order_list_upcoming" class="usermenu-link"><fmt:message key="label.user.menu.orders-upcoming" bundle="${ rb }" /></a></li>
                                        <li class="usermenu-item"><a href="travel?command=order_list_old" class="usermenu-link"><fmt:message key="label.user.menu.orders-old" bundle="${ rb }" /></a></li>
                                        <li class="usermenu-item"><a href="travel?command=balance" class="usermenu-link"><fmt:message key="label.user.menu.balance" bundle="${ rb }" /></a></li>
                                        <li class="usermenu-item divider"></li>
                                        <li class="usermenu-item"><a href="travel?command=forward&page=change_password" class="usermenu-link"><fmt:message key="label.user.menu.change-pass" bundle="${ rb }" /></a></li>
                                    </ul>
                                    <div class="log-action"><a class="menu-link" href="travel?command=logout"><fmt:message key="action.logout" bundle="${ rb }" /></a></div>
                                </c:when>
                                <c:otherwise>
                                    <div class="register-action"><a class="menu-link" href="travel?command=forward&page=register"><fmt:message key="action.registration" bundle="${ rb }" /></a></div>
                                    <div class="log-action"><a class="menu-link" href="travel?command=forward&page=login"><fmt:message key="action.login" bundle="${ rb }" /></a></div>

                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>

<div id="search-box">
    <div class="container-fluid">
        <form class="search-form" role="form" id="search" method="post" action="travel">
            <input type="hidden" name="command" value="search" />
            <div class="row">
                <div class="form-group col-sm-2 col-sm-offset-2">
                    <label for="tour_type"><fmt:message key="label.tour-type" bundle="${ rb }" /></label>
                    <div class="input-group">
                        <select class="form-control" id="tour_type" name="tour_type">
                            <option value="vacation"><fmt:message key="label.vacation" bundle="${ rb }" /></option>
                            <option value="trip"><fmt:message key="label.trip" bundle="${ rb }" /></option>
                            <option value="shopping"><fmt:message key="label.shopping" bundle="${ rb }" /></option>
                        </select>
                    </div>
                </div>
                <div class="form-group col-sm-2">
                    <label for="tour_field"><fmt:message key="label.search-term" bundle="${ rb }" /></label>
                    <div class="input-group">
                        <select class="form-control" id="tour_field" name="tour_field">
                            <option value="name"><fmt:message key="label.name" bundle="${ rb }" /></option>
                            <option value="departure_date"><fmt:message key="label.vacation.departureDate" bundle="${ rb }" /></option>
                            <option value="arrival_date"><fmt:message key="label.vacation.arrivalDate" bundle="${ rb }" /></option>
                            <option value="price"><fmt:message key="label.vacation.price" bundle="${ rb }" /></option>
                            <option value="transport"><fmt:message key="label.vacation.transport" bundle="${ rb }" /></option>
                        </select>
                    </div>
                </div>
                <div class="form-group col-sm-3">
                    <label for="search_text"><fmt:message key="label.search-conditions" bundle="${ rb }" /></label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="search_text" name="search_text">
                        <select style="display: none;" class="form-control" id="search_text_select" name="search_text_select">
                            <option value="PLANE"><fmt:message key="label.transport.plane" bundle="${ rb }" /></option>
                            <option value="BOAT"><fmt:message key="label.transport.boat" bundle="${ rb }" /></option>
                            <option value="TRAIN"><fmt:message key="label.transport.train" bundle="${ rb }" /></option>
                            <option value="BUS"><fmt:message key="label.transport.bus" bundle="${ rb }" /></option>
                        </select>
                    </div>
                </div>
                <button type="submit" class="btn btn-success col-sm-1"><fmt:message key="action.search" bundle="${ rb }" /></button>
            </div>
        </form>
    </div>
</div>