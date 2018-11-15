
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.register" bundle="${ rb }" /></title>
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
    <!--<script src="/js/epamtravel.js"></script>-->
    <![endif]-->
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="/jsp/header.jsp"%>
<section>
    <div class="homepage-banner"></div>
    <span style="text-align: center;"><h1><fmt:message key="action.registration" bundle="${ rb }" /></h1></span>
    <hr>
    <form id="registerform" style="width: 300px; margin: 0 auto" method="post" action="travel">
        <input type="hidden" name="command" value="register" />
        <div class="form-group">
            <label class="control-label" for="login"><fmt:message key="label.form.register.login" bundle="${ rb }" /></label>
            <div class="controls">
            <input type="text" pattern="([A-ZА-Яa-zа-я0-9_-]){3,16}" data-validation-pattern-message="<fmt:message key="label.valid.login" bundle="${ rb }" />"  class="form-control" id="login" name="login" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label" for="password"><fmt:message key="label.form.register.password" bundle="${ rb }" /></label>
            <div class="controls">
            <input type="password" pattern="([A-ZА-Яa-zа-я0-9_-]){8,20}" data-validation-pattern-message="<fmt:message key="label.valid.password" bundle="${ rb }" />" class="form-control" id="password" name="password" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label" for="email"><fmt:message key="label.form.register.email" bundle="${ rb }" /></label>
            <div class="controls">
                <input type="email" data-validation-email-message="<fmt:message key="label.valid.email" bundle="${ rb }" />" class="form-control" id="email" name="email" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label" for="name"><fmt:message key="label.form.register.name" bundle="${ rb }" /></label>
            <div class="controls">
            <input type="text" pattern="([A-ZА-Яa-zа-я]){2,25}" data-validation-pattern-message="<fmt:message key="label.valid.name" bundle="${ rb }" />" class="form-control" id="name" name="name" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label" for="surname"><fmt:message key="label.form.register.surname" bundle="${ rb }" /></label>
            <div class="controls">
            <input type="text" pattern="([A-ZА-Яa-zа-я]){2,25}" data-validation-pattern-message="<fmt:message key="label.valid.surname" bundle="${ rb }" />" class="form-control" id="surname" name="surname" required>
            </div>
        </div>
        <c:choose>
            <c:when test="${ not empty errorRegisterPassMessage }">
                <br/>
                <span style="color: #ff0000;">${errorRegisterPassMessage}</span>
                <br/>
                <br/>
            </c:when>
            <c:otherwise>
                <br/>
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
        <button id="sub" type="submit" class="btn btn-success"><fmt:message key="action.createacc" bundle="${ rb }" /></button>
    </form>
    <hr>
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
