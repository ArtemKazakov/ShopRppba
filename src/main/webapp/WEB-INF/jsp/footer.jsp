<%--
  Created by IntelliJ IDEA.
  User: aliubimov
  Date: 1/9/17
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.allrights" var="allrights"/>

<footer class="primary-footer">
    <div class="wrapper">
        <a href="index.html" class="footer-logo">clShop</a>
        <div class="copyright">
            ${allrights}
        </div>
    </div>

    <script src="../../js/validation.js"></script>
</footer>

<script type="text/javascript">
    function loginForm(){
        var form = document.getElementById("signin-form");
        if(form.style.display == "none"){
            form.style.display = "block";
        } else{
            form.style.display = "none";
        }
    }
</script>
