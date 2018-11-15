

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.vacation" bundle="${ rb }" /></title>
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
    <span style="text-align: center;"><h1><fmt:message key="label.vacation.list" bundle="${rb}" /></h1></span>
    <div class="before-grid">
        <div class="sort-box">
            <label for="cd-dropdown"><fmt:message key="label.sort" bundle="${rb}" />: </label>
            <select id="cd-dropdown" class="cd-select form-control" onchange="top.location=this.value">
                <c:choose>
                    <c:when test="${ empty sortCriterion }">
                        <option selected value="travel?command=vacation_list"><fmt:message key="label.sort.last" bundle="${rb}" /></option>
                    </c:when>
                    <c:otherwise>
                        <option value="travel?command=vacation_list"><fmt:message key="label.sort.last" bundle="${rb}" /></option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${ 'name' == sortCriterion }">
                        <c:choose>
                            <c:when test="${ 'true' == sortOrder }">
                                <option selected value="travel?command=vacation_sort&criterion=name&order=true" class="icon-1"><fmt:message key="label.sort.name" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option value="travel?command=vacation_sort&criterion=name&order=false" class="icon-2"><fmt:message key="label.sort.name" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:when>
                            <c:otherwise>
                                <option value="travel?command=vacation_sort&criterion=name&order=true" class="icon-1"><fmt:message key="label.sort.name" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option selected value="travel?command=vacation_sort&criterion=name&order=false" class="icon-2"><fmt:message key="label.sort.name" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <option value="travel?command=vacation_sort&criterion=name&order=true" class="icon-1"><fmt:message key="label.sort.name" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                        <option value="travel?command=vacation_sort&criterion=name&order=false" class="icon-2"><fmt:message key="label.sort.name" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${ 'departure_date' == sortCriterion }">
                        <c:choose>
                            <c:when test="${ 'true' == sortOrder }">
                                <option selected value="travel?command=vacation_sort&criterion=departure_date&order=true" class="icon-3"><fmt:message key="label.sort.departure-date" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option value="travel?command=vacation_sort&criterion=departure_date&order=false" class="icon-4"><fmt:message key="label.sort.departure-date" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:when>
                            <c:otherwise>
                                <option value="travel?command=vacation_sort&criterion=departure_date&order=true" class="icon-3"><fmt:message key="label.sort.departure-date" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option selected value="travel?command=vacation_sort&criterion=departure_date&order=false" class="icon-4"><fmt:message key="label.sort.departure-date" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <option value="travel?command=vacation_sort&criterion=departure_date&order=true" class="icon-3"><fmt:message key="label.sort.departure-date" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                        <option value="travel?command=vacation_sort&criterion=departure_date&order=false" class="icon-4"><fmt:message key="label.sort.departure-date" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${ 'arrival_date' == sortCriterion }">
                        <c:choose>
                            <c:when test="${ 'true' == sortOrder }">
                                <option selected value="travel?command=vacation_sort&criterion=arrival_date&order=true" class="icon-5"><fmt:message key="label.sort.arrival-date" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option value="travel?command=vacation_sort&criterion=arrival_date&order=false" class="icon-6"><fmt:message key="label.sort.arrival-date" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:when>
                            <c:otherwise>
                                <option value="travel?command=vacation_sort&criterion=arrival_date&order=true" class="icon-5"><fmt:message key="label.sort.arrival-date" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option selected value="travel?command=vacation_sort&criterion=arrival_date&order=false" class="icon-6"><fmt:message key="label.sort.arrival-date" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <option value="travel?command=vacation_sort&criterion=arrival_date&order=true" class="icon-5"><fmt:message key="label.sort.arrival-date" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                        <option value="travel?command=vacation_sort&criterion=arrival_date&order=false" class="icon-6"><fmt:message key="label.sort.arrival-date" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${ 'price' == sortCriterion }">
                        <c:choose>
                            <c:when test="${ 'true' == sortOrder }">
                                <option selected value="travel?command=vacation_sort&criterion=price&order=true" class="icon-7"><fmt:message key="label.sort.price" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option value="travel?command=vacation_sort&criterion=price&order=false" class="icon-8"><fmt:message key="label.sort.price" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:when>
                            <c:otherwise>
                                <option value="travel?command=vacation_sort&criterion=price&order=true" class="icon-7"><fmt:message key="label.sort.price" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option selected value="travel?command=vacation_sort&criterion=price&order=false" class="icon-8"><fmt:message key="label.sort.price" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <option value="travel?command=vacation_sort&criterion=price&order=true" class="icon-7"><fmt:message key="label.sort.price" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                        <option value="travel?command=vacation_sort&criterion=price&order=false" class="icon-8"><fmt:message key="label.sort.price" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${ 'transport' == sortCriterion }">
                        <c:choose>
                            <c:when test="${ 'true' == sortOrder }">
                                <option selected value="travel?command=vacation_sort&criterion=transport&order=true" class="icon-9"><fmt:message key="label.sort.transport" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option value="travel?command=vacation_sort&criterion=transport&order=false" class="icon-10"><fmt:message key="label.sort.transport" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:when>
                            <c:otherwise>
                                <option value="travel?command=vacation_sort&criterion=transport&order=true" class="icon-9"><fmt:message key="label.sort.transport" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                                <option selected value="travel?command=vacation_sort&criterion=transport&order=false" class="icon-10"><fmt:message key="label.sort.transport" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <option value="travel?command=vacation_sort&criterion=transport&order=true" class="icon-9"><fmt:message key="label.sort.transport" bundle="${rb}" /> (<fmt:message key="label.sort.ascending" bundle="${rb}" />)</option>
                        <option value="travel?command=vacation_sort&criterion=transport&order=false" class="icon-10"><fmt:message key="label.sort.transport" bundle="${rb}" /> (<fmt:message key="label.sort.descending" bundle="${rb}" />)</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <hr>
    </div>
    <c:choose>
        <c:when test="${ not empty vacations }">
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
        </c:when>
        <c:otherwise>
            <div class="empty-list">
                <span><fmt:message key="label.empty-search" bundle="${rb}" /></span>
            </div>
        </c:otherwise>
    </c:choose>
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

