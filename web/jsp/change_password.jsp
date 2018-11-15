
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.change-password" bundle="${rb}" /></title>
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
    <span style="text-align: center;"><h1><fmt:message key="title.change-password" bundle="${rb}" /></h1></span>

    <c:choose>
        <c:when test="${ not empty errorChangePasswordMessage }">
            <br/>
            <div style="text-align: center; width: 100%;">
                <span style="color: #ff0000;"><b>${errorChangePasswordMessage}</b></span>
            </div>
            <br/>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ not empty changePasswordSuccessMessage }">
            <br/>
            <div style="text-align: center; width: 100%;">
                <span style="color: #4cae4c;"><b>${changePasswordSuccessMessage}</b></span>
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

    <div class="container-fluid change-password-page">
        <br>
        <form id="change_password_form" style="width: 300px; margin: 0 auto; text-align: center;" method="post" action="travel">
            <input type="hidden" name="command" value="change_password" />
            <div class="form-group">
                <label class="control-label" for="password_old"><fmt:message key="label.change.password.old" bundle="${ rb }" /></label>
                <div class="controls">
                    <input type="password" class="form-control" id="password_old" name="password_old" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label" for="password_new"><fmt:message key="label.change.password.new" bundle="${ rb }" /></label>
                <div class="controls">
                    <input type="password" pattern="([A-ZА-Яa-zа-я0-9_-]){8,20}" data-validation-pattern-message="<fmt:message key="label.valid.password" bundle="${ rb }" />" class="form-control" id="password_new" name="password_new" required>
                </div>
            </div>
            <button id="sub" type="submit" class="btn btn-success"><fmt:message key="label.change.password.change" bundle="${ rb }" /></button>
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
<script src="/js/jqBootstrapValidation.js"></script>
<script>
    $(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); } );
</script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

