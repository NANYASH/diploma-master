<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<footer>
    <div class="footer-content">
        <div class="copyright">&copy; <fmt:message key="footer.copyright" bundle="${ rb }" /></div>
    </div>
</footer>

<script src="/js/slidebox.js"></script>
<script>
    $(document).ready(function(){

        $("#search-box").slideBox({width: "100%", height: "126px", position: "top"});
    });
</script>
<script>
    $(function(){
        var d = new Date();
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        $("#tour_field").change(function () {
            switch ($("#tour_field :selected").val()){
                case "name":
                    $("#search_text").css("display", "");
                    $("#search_text_select").css("display", "none");
                    $("#search_text").attr("type", "text");
                    break;
                case "departure_date":
                    $("#search_text").css("display", "");
                    $("#search_text_select").css("display", "none");
                    $("#search_text").attr("type", "date");
                    $("#search_text").attr("min", year + "-" + month + "-" + day);
                    break;
                case "arrival_date":
                    $("#search_text").css("display", "");
                    $("#search_text_select").css("display", "none");
                    $("#search_text").attr("type", "date");
                    $("#search_text").attr("min", year + "-" + month + "-" + day);
                    break;
                case "price":
                    $("#search_text").css("display", "");
                    $("#search_text_select").css("display", "none");
                    $("#search_text").attr("type", "number");
                    break;
                case "transport":
                    $("#search_text").css("display", "none");
                    $("#search_text_select").css("display", "");
                    break;
                default:
                    $("#search_text").css("display", "");
                    $("#search_text_select").css("display", "none");
                    $("#search_text").attr("type", "text");
                    break;
            }
        })
    });
</script>