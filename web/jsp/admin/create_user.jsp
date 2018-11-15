
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.admin.create-user" bundle="${ rb }" /></title>
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
        <span style="text-align: center;"><h1><fmt:message key="title.admin.create-user" bundle="${ rb }" /></h1></span>
        <ctg:before-grid />
        <form id="create-user-form" method="post" action="travel" accept-charset="utf-8">
            <input type="hidden" name="command" value="create_user" />

            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label class="control-label" for="login"><fmt:message key="label.form.register.login" bundle="${ rb }" /></label>
                    <input type="text" pattern="([A-ZА-Яa-zа-я0-9_-]){3,16}" data-validation-pattern-message="<fmt:message key="label.valid.login" bundle="${ rb }" />"  class="form-control" id="login" name="login" required>
                </div>
                <div class="form-group col-sm-3">
                    <label class="control-label" for="password"><fmt:message key="label.form.register.password" bundle="${ rb }" /></label>
                    <input type="password" pattern="([A-ZА-Яa-zа-я0-9_-]){8,20}" data-validation-pattern-message="<fmt:message key="label.valid.password" bundle="${ rb }" />" class="form-control" id="password" name="password" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label class="control-label" for="email"><fmt:message key="label.form.register.email" bundle="${ rb }" /></label>
                    <input type="email" data-validation-email-message="<fmt:message key="label.valid.email" bundle="${ rb }" />" class="form-control" id="email" name="email" required>
                </div>
                <div class="form-group col-sm-3">
                    <label for="role"><fmt:message key="label.form.register.role" bundle="${ rb }" /></label>
                    <select class="form-control" id="role" name="role">
                        <option value="0"><fmt:message key="label.form.register.user" bundle="${ rb }" /></option>
                        <option value="1"><fmt:message key="label.form.register.admin" bundle="${ rb }" /></option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label class="control-label" for="name"><fmt:message key="label.form.register.name" bundle="${ rb }" /></label>
                    <input type="text" pattern="([A-ZА-Яa-zа-я]){2,25}" data-validation-pattern-message="<fmt:message key="label.valid.name" bundle="${ rb }" />" class="form-control" id="name" name="name" required>
                </div>
                <div class="form-group col-sm-3">
                    <label class="control-label" for="surname"><fmt:message key="label.form.register.surname" bundle="${ rb }" /></label>
                    <input type="text" pattern="([A-ZА-Яa-zа-я]){2,25}" data-validation-pattern-message="<fmt:message key="label.valid.surname" bundle="${ rb }" />" class="form-control" id="surname" name="surname" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label class="control-label" for="discount"><fmt:message key="label.form.register.discount" bundle="${ rb }" /></label>
                    <input type="number" min="0" max="1" step="0.1" value="0" class="form-control" id="discount" name="discount" required>
                </div>
                <div class="form-group col-sm-3">
                    <label class="control-label" for="money"><fmt:message key="label.form.register.money" bundle="${ rb }" /></label>
                    <input type="number" min="0" max="100000" value="0" class="form-control" id="money" name="money" required>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2 col-sm-offset-5">
                    <c:choose>
                        <c:when test="${ not empty errorCreateUserMessage }">
                            <br/>
                            <span style="color: #ff0000;">${errorCreateUserMessage}</span>
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
                        <button type="submit" class="btn btn-success form-button"><fmt:message key="action.create-user" bundle="${ rb }" /></button>
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
<script src="/js/jqBootstrapValidation.js"></script>
<script>
    $(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); } );
</script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

