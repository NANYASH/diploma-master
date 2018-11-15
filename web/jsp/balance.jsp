
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.balance" bundle="${rb}" /></title>
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
    <span style="text-align: center;"><h1><fmt:message key="title.balance" bundle="${rb}" /></h1></span>

    <c:choose>
        <c:when test="${ not empty errorBalanceAddMessage }">
            <br/>
            <div style="text-align: center; width: 100%;">
                <span style="color: #ff0000;"><b>${errorBalanceAddMessage}</b></span>
            </div>
            <br/>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>

    <ctg:before-grid />

    <div class="container-fluid balance-page">
        <div class="row balance-text-center"><span><fmt:message key="label.balance.user-text" bundle="${rb}" /> <b><c:out value="${ userProfile.name }" /> <c:out value="${ userProfile.surname }" /></b>,</span></div>
        <div class="row balance-text-center"><span><fmt:message key="label.balance.balance-text" bundle="${rb}" />: <b><c:out value="${ userProfile.money }" />$</b></span></div>
        <div class="row balance-text-center"><span><fmt:message key="label.balance.discount-text" bundle="${rb}" />: <b><c:out value="${ userProfile.discount * 100 }" />%</b></span></div>
        <br>
        <div class="row balance-text-center"><h3><fmt:message key="label.balance.recharging" bundle="${rb}" /></h3></div>
        <form id="balanceform" style="width: 300px; margin: 0 auto; text-align: center;" method="post" action="travel">
            <input type="hidden" name="command" value="balance_add" />
            <div class="form-group">
                <label class="control-label" for="money"><fmt:message key="label.balance.money" bundle="${ rb }" /></label>
                <div class="controls">
                    <input type="number" min="1" max="10000" data-validation-pattern-message="<fmt:message key="label.valid.balance-recharge" bundle="${ rb }" />" class="form-control" id="money" name="money" required>
                </div>
            </div>
            <button id="sub" type="submit" class="btn btn-success"><fmt:message key="label.balance.recharge" bundle="${ rb }" /></button>
        </form>
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

