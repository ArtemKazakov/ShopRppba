<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.men" var="men"/>
    <fmt:message bundle="${loc}" key="locale.kids" var="kids"/>
    <fmt:message bundle="${loc}" key="locale.ladies" var="ladies"/>
    <fmt:message bundle="${loc}" key="locale.accessories" var="accessories"/>
    <fmt:message bundle="${loc}" key="locale.ladiesclothing" var="ladiesclothing"/>
    <fmt:message bundle="${loc}" key="locale.menclothing" var="menclothing"/>
    <fmt:message bundle="${loc}" key="locale.kidsclothing" var="kidsclothing"/>
    <fmt:message bundle="${loc}" key="locale.eng" var="eng"/>
    <fmt:message bundle="${loc}" key="locale.rus" var="rus"/>
    <fmt:message bundle="${loc}" key="locale.myprofile" var="myprofile"/>
    <fmt:message bundle="${loc}" key="locale.shoppingbag" var="shoppingbag"/>
    <fmt:message bundle="${loc}" key="locale.signin" var="signin"/>
    <fmt:message bundle="${loc}" key="locale.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="locale.join" var="join"/>
    <fmt:message bundle="${loc}" key="locale.allrights" var="allrights"/>
    <fmt:message bundle="${loc}" key="locale.email" var="email"/>
    <fmt:message bundle="${loc}" key="locale.password" var="password"/>
    <fmt:message bundle="${loc}" key="locale.explore" var="explore"/>
    <fmt:message bundle="${loc}" key="locale.search" var="search"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <title>shop</title>
</head>
<body>
<header class="main-header">
    <div class="wrapper">
        <a href="Controller?action=gotomain" class="logo">clShop</a>
        <nav class="primary-menu">
            <ul>
                <li><a href="Controller?action=load&category=men">${men}</a></li>
                <li><a href="Controller?action=load&category=ladies">${ladies}</a></li>
                <li><a href="Controller?action=load&category=kids">${kids}</a></li>
                <li><a href="Controller?action=load&category=accessories">${accessories}</a></li>
            </ul>
        </nav>
        <nav class="locale-menu">
            <div class="locale eng" onclick="location.href='Controller?action=changelocale&lang=eng'">${eng}</div>
            <div class="locale ru" onclick="location.href='Controller?action=changelocale&lang=ru'">${rus}</div>
        </nav>
        <nav class="additional-menu">
            <div class="account">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <a onclick="loginForm()">${sessionScope.user.name}&nbsp;${sessionScope.user.surname}</a>
                        <div class="signin-rollover row" id="signin-form" style="display: none">
                            <div class="inner">
                                <form action="Controller">
                                    <input type="hidden" name="action" value="gotouserprofile">
                                    <div class="inputwrapper">
                                        <button type="submit" class="button signin">${myprofile}</button>
                                    </div>
                                </form>
                                <form action="Controller" method="POST">
                                    <input type="hidden" name="action" value="logout"/>
                                    <div class="inputwrapper">
                                        <button type="submit" class="button signin">${logout}</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a onclick="loginForm()">${signin} / ${join}</a>
                        <div class="signin-rollover row" id="signin-form" style="display: none">
                            <div class="inner">
                                <form action="Controller" method="POST">
                                    <input type="hidden" name="action" value="login"/>
                                    <h3>${signin}</h3>
                                    <div class="inputwrapper">
                                        <label>${email}:</label>
                                        <input type="email" name="email">
                                    </div>
                                    <div class="inputwrapper">
                                        <label>${password}:</label>
                                        <input type="password" name="password">
                                    </div>
                                    <div class="inputwrapper">
                                        <button type="submit" class="button signin">${signin}</button>
                                    </div>
                                    <div class="inputwrapper join">
                                        <button class="button join-btn" type="button" onclick="location.href='Controller?action=redirect&page=registration.jsp'">${join}</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <c:if test="${sessionScope.user != null}">
                <div class="shop-cart">
                    <a href="Controller?action=redirect&page=cart.jsp">${shoppingbag} (${sessionScope.cart.size})</a>
                </div>
            </c:if>
        </nav>
        <div class="search">
            <form method="get" action="Controller">
                <input type="hidden" name="action" value="search">
                <input type="text" name="querry" placeholder="${search}">
            </form>
            <span class="magnifier"></span>
        </div>
    </div>
</header>

<main>
    <div class="title-image men-image">
    </div>
    <div class="layout-main">
        <div class="category-title">${menclothing}</div>
        <button class="button" onclick="location.href='Controller?action=load&category=men'">${explore}</button>
    </div>
    <div class="title-image ladies-image">
    </div>
    <div class="layout-main">
        <div class="category-title">${ladiesclothing}</div>
        <button class="button" onclick="location.href='Controller?action=load&category=ladies'">${explore}</button>
    </div>
    <div class="title-image kids-image">
    </div>
    <div class="layout-main">
        <div class="category-title">${kidsclothing}</div>
        <button class="button" onclick="location.href='Controller?action=load&category=kids'">${explore}</button>
    </div>
    <div class="title-image accessories-image">
    </div>
    <div class="layout-main">
        <div class="category-title">${accessories}</div>
        <button class="button" onclick="location.href='Controller?action=load&category=accessories'">${explore}</button>
    </div>
</main>
<footer class="primary-footer">
    <div class="wrapper">
        <a href="index.html" class="footer-logo">clShop</a>
        <div class="copyright">
            ${allrights}
        </div>
    </div>
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
</body>
</html>
