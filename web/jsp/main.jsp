
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.main" bundle="${ rb }" /></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script src="/js/epamtravel.js"></script>
    <![endif]-->
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="/jsp/header.jsp"%>
<section>
    <c:if test="${ (empty vacations) && (empty trips) && (empty shoppings) }">
        <script language="JavaScript" type="text/javascript">
            location="travel?command=tour_list"
        </script>
    </c:if>
    <span style="text-align: center;"><h1><a href="travel?command=vacation_list" class="tour-list-link"><fmt:message key="label.vacation.list" bundle="${rb}" /></a></h1></span>
    <ctg:before-grid />
    <div class="grid">
        <c:forEach var="vacation" items="${vacations}" varStatus="status">
                <div class="effect-tour">
                    <c:if test="${ vacation.lastMinute }">
                        <img class="hot-tour-img" src="/images/hot_tour.png" />
                    </c:if>
                    <figure class="effect-milo">
                        <img src="${vacation.pathImage}" alt="img"/>
                        <figcaption>
                            <h2><c:out value="${ vacation.cities[0].name }" />, <span><c:out value="${ vacation.cities[0].country.nameCountry }" /></span></h2>
                            <h3><c:out value="${ vacation.price }" />$</h3>
                            <p><c:out value="${ vacation.summary }" /></p>
                            <a href="travel?command=vacation_full&id=<c:out value="${ vacation.id }" />"></a>
                        </figcaption>
                    </figure>
                </div>
        </c:forEach>
    </div>
    <div class="after-grid">
        <hr>
    </div>
    <span style="text-align: center;"><h1><a href="travel?command=trip_list" class="tour-list-link"><fmt:message key="label.trip.list" bundle="${rb}" /></a></h1></span>
    <div class="before-grid">
        <hr>
    </div>
    <div class="grid">
        <c:forEach var="trip" items="${trips}" varStatus="status">
            <div class="effect-tour">
                <c:if test="${ trip.lastMinute }">
                    <img class="hot-tour-img" src="/images/hot_tour.png" />
                </c:if>
                <figure class="effect-milo">
                    <img src="${trip.pathImage}" alt="img"/>
                    <figcaption>
                        <h2><c:out value="${ trip.cities[0].name }" />, <span><c:out value="${ trip.cities[0].country.nameCountry }" /></span></h2>
                        <h3><c:out value="${ trip.price }" />$</h3>
                        <p><c:out value="${ trip.summary }" /></p>
                        <a href="travel?command=trip_full&id=<c:out value="${ trip.id }" />"></a>
                    </figcaption>
                </figure>
            </div>
        </c:forEach>
    </div>
    <div class="after-grid">
        <hr>
    </div>
    <span style="text-align: center;"><h1><a href="travel?command=shopping_list" class="tour-list-link"><fmt:message key="label.shopping.list" bundle="${rb}" /></a></h1></span>
    <div class="before-grid">
        <hr>
    </div>
    <div class="grid">
        <c:forEach var="shopping" items="${shoppings}" varStatus="status">
            <div class="effect-tour">
                <c:if test="${ shopping.lastMinute }">
                    <img class="hot-tour-img" src="/images/hot_tour.png" />
                </c:if>
                <figure class="effect-milo">
                    <img src="${shopping.pathImage}" alt="img"/>
                    <figcaption>
                        <h2><c:out value="${ shopping.cities[0].name }" />, <span><c:out value="${ shopping.cities[0].country.nameCountry }" /></span></h2>
                        <h3><c:out value="${ shopping.price }" />$</h3>
                        <p><c:out value="${ shopping.summary }" /></p>
                        <a href="travel?command=shopping_full&id=<c:out value="${ shopping.id }" />"></a>
                    </figcaption>
                </figure>
            </div>
        </c:forEach>
    </div>
    <div class="after-grid">
        <hr>
    </div>
</section>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

