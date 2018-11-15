
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.orders" bundle="${rb}" /></title>
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
    <span style="text-align: center;"><h1><fmt:message key="title.orders" bundle="${rb}" /></h1></span>

    <c:choose>
        <c:when test="${ not empty errorOrderDeleteMessage }">
            <br/>
            <div style="text-align: center; width: 100%;">
                <span style="color: #ff0000;"><b>${errorOrderDeleteMessage}</b></span>
            </div>
            <br/>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>

    <ctg:before-grid />

    <div class="container-fluid">
<c:choose>
    <c:when test="${ not empty orders }">
        <c:forEach var="order" items="${orders}" varStatus="status">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2  order-row">
                    <div class="col-sm-5 order-img"><img src="<c:out value="${ order.pathImage }" />" width="276px" height="207px"></div>
                    <div class="col-sm-3 order-info-text">
                        <div class="row"><b><fmt:message key="label.order.name" bundle="${ rb }" />:</b> <c:out value="${ order.name }" /></div>
                        <div class="row"><b><fmt:message key="label.order.tour-type" bundle="${ rb }" />:</b>
                            <c:if test="${ 'VACATIONS' == (order.tourType) }"><fmt:message key="title.vacation" bundle="${ rb }" /></c:if>
                            <c:if test="${ 'TRIPS' == (order.tourType) }"><fmt:message key="title.trip" bundle="${ rb }" /></c:if>
                            <c:if test="${ 'SHOPPINGS' == (order.tourType) }"><fmt:message key="title.shopping" bundle="${ rb }" /></c:if>
                        </div>
                        <div class="row"><b><fmt:message key="label.order.departure-date" bundle="${ rb }" />:</b> <c:out value="${ order.departureDate }" /></div>
                        <div class="row"><b><fmt:message key="label.order.arrival-date" bundle="${ rb }" />:</b> <c:out value="${ order.arrivalDate }" /></div>
                        <div class="row"><b><fmt:message key="label.order.quantity" bundle="${ rb }" />:</b> <c:out value="${ order.quantity }" /></div>
                        <div class="row"><b><fmt:message key="label.order.total-price" bundle="${ rb }" />:</b> <c:out value="${ order.totalPrice }" />$</div>
                    </div>
                    <div class="col-sm-4 order-buttons-block">
                        <div class="row">
                            <c:if test="${ 'VACATIONS' == (order.tourType) }"><a href="travel?command=vacation_full&id=<c:out value="${ order.tourId }" />"><button type="button" class="btn btn-info order-buttons order-button-first" data-toggle="modal" data-target="#order-modal">
                                <fmt:message key="label.order.tour-info" bundle="${ rb }" /></button></a></c:if>
                            <c:if test="${ 'TRIPS' == (order.tourType) }"><a href="travel?command=trip_full&id=<c:out value="${ order.tourId }" />"><button type="button" class="btn btn-info order-buttons order-button-first" data-toggle="modal" data-target="#order-modal">
                                <fmt:message key="label.order.tour-info" bundle="${ rb }" /></button></a></c:if>
                            <c:if test="${ 'SHOPPINGS' == (order.tourType) }"><a href="travel?command=shopping_full&id=<c:out value="${ order.tourId }" />"><button type="button" class="btn btn-info order-buttons order-button-first" data-toggle="modal" data-target="#order-modal">
                                <fmt:message key="label.order.tour-info" bundle="${ rb }" /></button></a></c:if>
                        </div>
                        <div class="row">
                            <c:if test="${ 'upcoming' == (time) }"><button type="button" class="btn btn-danger order-buttons" data-toggle="modal" data-target="#order-delete-modal-<c:out value="${ order.orderId }" />">
                                <fmt:message key="label.order.cancel-tour" bundle="${ rb }" /></button></c:if>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="order-delete-modal-<c:out value="${ order.orderId }" />" tabindex="-1" role="dialog" aria-labelledby="order-delete-modal-label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="order-delete-modal-label"><fmt:message key="label.order.delete.modal.title" bundle="${ rb }" /></h4>
                        </div>
                        <div class="modal-body">
                            <fmt:message key="label.order.delete.modal.question-text" bundle="${ rb }" /> "<c:out value="${ order.name }" />" (<fmt:message key="label.order.delete.modal.price" bundle="${ rb }" /> : <c:out value="${ order.totalPrice }" />)? <fmt:message key="label.order.delete.modal.text-info" bundle="${ rb }" />
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.admin.modal-cancel" bundle="${ rb }" /></button>
                            <a href="travel?command=delete_order&id=<c:out value="${ order.orderId }" />"><button type="button" class="btn btn-danger"><fmt:message key="label.order.delete.modal.cancel-button" bundle="${ rb }" /></button></a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="empty-orders">
            <span><fmt:message key="label.order.empty-orders" bundle="${rb}" /></span>
        </div>
    </c:otherwise>
</c:choose>
    </div>

    <div class="after-grid">
        <hr>
        <div class="tour-controls">
            <button type="button" class="btn btn-default" onclick="history.back()"><fmt:message key="label.vacation.button.back" bundle="${rb}" /></button>
        </div>
    </div>
</section>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

