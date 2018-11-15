
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.login" bundle="${ rb }" /></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>

    <link rel="icon" href="favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />

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
    <span style="text-align: center;"><h1><fmt:message key="action.login" bundle="${ rb }" /></h1></span>
    <hr>
    <form id="loginform" style="width: 300px; margin: 0 auto" method="post" action="travel">
        <input type="hidden" name="command" value="login" />
        <div class="form-group">
            <label for="login"><fmt:message key="label.login" bundle="${ rb }" /></label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" class="form-control" id="login" name="login">
            </div>
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="label.password" bundle="${ rb }" /></label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" class="form-control" id="password" name="password">
            </div>
        </div>
        <c:choose>
            <c:when test="${ not empty errorLoginPassMessage }">
                <br/>
                <span style="color: #ff0000;">${errorLoginPassMessage}</span>
                <br/>
                <br/>
            </c:when>
            <c:otherwise>
                <br/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${ not empty errorNotAuthorizedMessage }">
                <br/>
                <span style="color: #ff0000;">${errorNotAuthorizedMessage}</span>
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
        <button type="submit" class="btn btn-default"><fmt:message key="action.login" bundle="${ rb }" /></button>
    </form>
    <hr>
</section>



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>

<%@ include file="/jsp/footer.jsp"%>


</body></html>
