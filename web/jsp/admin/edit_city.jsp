
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.admin.edit-city" bundle="${ rb }" /></title>
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
    <![endif]-->
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="/jsp/header.jsp"%>
<section>
    <div class="container-fluid">
        <span style="text-align: center;"><h1><fmt:message key="title.admin.edit-city" bundle="${ rb }" /></h1></span>
        <ctg:before-grid />
        <form id="edit-user-form" method="post" action="travel" accept-charset="utf-8">
            <input type="hidden" name="command" value="edit_city" />
            <input type="hidden" name="id" value="<c:out value="${ city.id }" />" />

            <div class="row">
                <div class="form-group col-sm-6 col-sm-offset-3">
                    <label for="name"><fmt:message key="label.admin.create-tour.tour.name" bundle="${ rb }" /></label>
                    <input type="text" class="form-control" id="name" name="name" max="45" value="<c:out value="${ city.name }" />" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-6 col-sm-offset-3">
                    <label for="country"><fmt:message key="label.admin.cities.country" bundle="${ rb }" /></label>
                    <select class="form-control" id="country" name="country">
                        <c:forEach var="country" items="${countries}">
                            <c:choose>
                                <c:when test="${ country.id == (city.country.id) }"><option selected value=<c:out value="${ country.id }" />><c:out value="${ country.nameCountry }" /></option></c:when>
                                <c:otherwise><option value=<c:out value="${ country.id }" />><c:out value="${ country.nameCountry }" /></option></c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2 col-sm-offset-5">
                    <c:choose>
                        <c:when test="${ not empty errorEditCityMessage }">
                            <br/>
                            <span style="color: #ff0000;">${errorEditCityMessage}</span>
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
                    <div class="control-buttons">
                        <button type="submit" class="btn btn-success form-button"><fmt:message key="action.edit" bundle="${ rb }" /></button>
                        <button type="button" class="btn btn-default form-button" onclick="history.back()"><fmt:message key="action.cancel" bundle="${ rb }" /></button>
                    </div>
                </div>
            </div>
        </form>
        <div class="after-grid">
            <hr>
        </div>
    </div>
</section>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

