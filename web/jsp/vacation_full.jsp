
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title>${ vacation.name }</title>
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
<body onload="calculateTotalPrice()">
<%@ include file="/jsp/header.jsp"%>
<section>
    <span style="text-align: center;"><h1><c:out value="${ vacation.name }" /></h1></span>
        <c:choose>
            <c:when test="${ not empty errorOrderPassMessage }">
                <br/>
                <div style="text-align: center; width: 100%;">
                <span style="color: #ff0000;"><b>${errorOrderPassMessage}</b></span>
                </div>
                <br/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${ not empty errorOrderNotEnoughMoneyMessage }">
                <br/>
                <div style="text-align: center; width: 100%;">
                <span style="color: #ff0000;"><b>${errorOrderNotEnoughMoneyMessage}</b></span>
                    </div>
                <br/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${ not empty orderSuccessMessage }">
                <br/>
                    <div style="text-align: center; width: 100%;">
                <span style="color: #4cae4c;"><b>${orderSuccessMessage}</b></span>
                        </div>
                <br/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${ not empty wrongAction }">
                <br/>
                ${wrongAction}
                <br/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${ not empty nullPage }">
                <br/>
                ${nullPage}
                <br/>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    <ctg:before-grid />
    <div class="tour-header">
        <div class="tour-photo">
            <img src="${vacation.pathImage}" alt="img" class="img-rounded">
        </div>

        <div class="short-description">
            <div class="first-column">
                <p><b><fmt:message key="label.vacation.departureDate" bundle="${rb}" />:</b> <c:out value="${ vacation.departureDate }" /></p>
                <p><b><fmt:message key="label.vacation.destinationCity" bundle="${rb}" />:</b> <c:out value="${ vacation.cities[0].name }" /></p>
                <p><b><fmt:message key="label.vacation.hotel" bundle="${rb}" />:</b> <c:out value="${ vacation.hotel }" /></p>
            </div>
            <div class="second-column">
                <p><b><fmt:message key="label.vacation.arrivalDate" bundle="${rb}" />:</b> <c:out value="${ vacation.arrivalDate }" /></p>
                <p><b><fmt:message key="label.vacation.destinationCounty" bundle="${rb}" />:</b> <c:out value="${ vacation.cities[0].country.nameCountry }" /></p>
                <p><b><fmt:message key="label.vacation.transport" bundle="${rb}" />:</b> <c:out value="${ vacation.transport.name}" /></p>
            </div>
            <div class="bottom-column">
                <p><b><fmt:message key="label.vacation.price" bundle="${rb}" />:</b> <c:out value="${ vacation.price }" />$</p>
            </div>
        </div>
    </div>
    <hr>
    <div class="full-description">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#description" data-toggle="tab"><fmt:message key="label.vacation.description" bundle="${rb}" /></a></li>
            <li><a href="#services" data-toggle="tab"><fmt:message key="label.vacation.services" bundle="${rb}" /></a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="description"><pre><c:out value="${ vacation.description}" /></pre></div>
            <div class="tab-pane" id="services"><pre><c:out value="${ vacation.services}" /></pre></div>
        </div>
    </div>

    <div class="modal fade" id="order-modal" tabindex="-1" role="dialog" aria-labelledby="order-modal-label" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="order-modal-label"><fmt:message key="label.modal.order.tour" bundle="${ rb }" /></h4>
                </div>
                <form class="form-horizontal" id="orderform" method="post" action="travel">
                    <div class="modal-body">
                        <input type="hidden" name="command" value="order_vacation" />
                        <input type="hidden" name="iduser" value="<c:out value="${ userProfile.id }" />" />
                        <input type="hidden" name="idtour" value="<c:out value="${ vacation.id }" />" />
                        <input type="hidden" name="price" value="<c:out value="${ vacation.price }" />" />
                        <input type="hidden" name="discount" value="<c:out value="${ userProfile.discount }" />" />
                        <input type="hidden" name="departure_date" value="<c:out value="${ vacation.departureDate }" />" />
                        <input type="hidden" name="arrival_date" value="<c:out value="${ vacation.arrivalDate }" />" />
                        <input type="hidden" name="tour_type" value="vacations" />
                        <div class="row order-information-title">
                            <h4><fmt:message key="label.modal.order.information" bundle="${ rb }" /></h4>
                        </div>
                        <div class="order-info">
                            <div class="user-info col-sm-6">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.user-name" bundle="${ rb }" /></label>
                                    <div class="col-sm-8">
                                        <p class="form-control-static paragraph-static"><c:out value="${ userProfile.name }" /></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.surname" bundle="${ rb }" /></label>
                                    <div class="col-sm-8">
                                        <p class="form-control-static paragraph-static"><c:out value="${ userProfile.surname }" /></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.money" bundle="${ rb }" /></label>
                                    <div class="col-sm-8">
                                        <p class="form-control-static paragraph-static"><c:out value="${ userProfile.money }" />$</p>
                                    </div>
                                </div>
                            </div>
                            <div class="tour-info col-sm-6">
                                <div class="form-group">
                                    <label class="col-sm-5 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.tour-name" bundle="${ rb }" /></label>
                                    <div class="col-sm-7">
                                        <p class="form-control-static paragraph-static"><c:out value="${ vacation.name }" /></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-5 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.departure-date" bundle="${ rb }" /></label>
                                    <div class="col-sm-7">
                                        <p class="form-control-static paragraph-static"><c:out value="${ vacation.departureDate }" /></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-5 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.arrival-date" bundle="${ rb }" /></label>
                                    <div class="col-sm-7">
                                        <p class="form-control-static paragraph-static"><c:out value="${ vacation.arrivalDate }" /></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="calculate-price">
                            <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-4 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.price" bundle="${ rb }" /></label>
                                <div class="col-sm-6 controls">
                                    <p id="tourPrice" class="form-control-static paragraph-static"><c:out value="${ vacation.price }" />$</p>
                                </div>
                            </div>
                            </div>
                            <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-4 control-label form-horizontal-control-label" for="quantity"><fmt:message key="label.modal.order.quantity" bundle="${ rb }" /></label>
                                <div class="col-sm-2 controls">
                                    <input onchange="calculateTotalPrice()" type="number" class="form-control" id="quantity" name="quantity" min="1" max="100" value="1" required>
                                </div>
                            </div>
                        </div>
                            <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-offset-4 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.discount" bundle="${ rb }" /></label>
                                <div class="col-sm-6 controls">
                                    <p id="discount" class="form-control-static paragraph-static"><c:out value="${ userProfile.discount * 100 }" />%</p>
                                </div>
                            </div>
                    </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-offset-4 control-label form-horizontal-control-label"><fmt:message key="label.modal.order.total" bundle="${ rb }" /></label>
                            <div class="col-sm-4 controls">
                                <p id="totalPrice" class="form-control-static paragraph-static"></p>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.admin.modal-cancel" bundle="${ rb }" /></button>
                        <button type="submit" class="btn btn-success"><fmt:message key="label.vacation.button.buy" bundle="${ rb }" /></button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="guest-modal" tabindex="-1" role="dialog" aria-labelledby="guest-modal-label" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="guest-modal-label"><fmt:message key="label.modal.guest.title" bundle="${ rb }" /></h4>
                </div>
                <div class="modal-body">
                    <fmt:message key="label.modal.guest.first-text" bundle="${ rb }" /> <a href="travel?command=forward&page=login"><fmt:message key="label.modal.guest.log-in" bundle="${ rb }" /></a> <fmt:message key="label.modal.guest.second-text" bundle="${ rb }" /> <a href="travel?command=forward&page=register"><fmt:message key="label.modal.guest.register" bundle="${ rb }" /></a><fmt:message key="label.modal.guest.third-text" bundle="${ rb }" />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.admin.modal-cancel" bundle="${ rb }" /></button>
                </div>
            </div>
        </div>
    </div>

    <div class="after-grid">
        <hr>
        <div class="tour-controls">
            <button type="button" class="btn btn-default" onclick="history.back()"><fmt:message key="label.vacation.button.back" bundle="${rb}" /></button>
            <c:choose>
                <c:when test="${ not empty user }">
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#order-modal"><fmt:message key="label.vacation.button.buy" bundle="${rb}" /></button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#guest-modal"><fmt:message key="label.vacation.button.buy" bundle="${rb}" /></button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>

<script>
    function calculateTotalPrice() {
        var price = <c:out value="${ vacation.price }" />;
        var quantity = $( "#quantity" ).val()
        var discount = 1-<c:out value="${ userProfile.discount }" />;

        totalPrice.innerHTML = Math.ceil(price * quantity * discount) + "$";
    }
</script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

