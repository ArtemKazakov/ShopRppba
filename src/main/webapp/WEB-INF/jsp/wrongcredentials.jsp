<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <fmt:message bundle="${loc}" key="locale.addnewitem" var="addnewitem"/>
    <fmt:message bundle="${loc}" key="locale.search" var="search"/>
    <fmt:message bundle="${loc}" key="locale.men" var="men"/>
    <fmt:message bundle="${loc}" key="locale.kids" var="kids"/>
    <fmt:message bundle="${loc}" key="locale.ladies" var="ladies"/>
    <fmt:message bundle="${loc}" key="locale.ladiesclothing" var="ladiesclothing"/>
    <fmt:message bundle="${loc}" key="locale.accessories" var="accessories"/>
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
    <fmt:message bundle="${loc}" key="locale.oops" var="oops"/>
    <fmt:message bundle="${loc}" key="locale.wronglog" var="wronglog"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <title>shop</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<main>
    <div class="layout">
        <div class="main-content">
            <h1 class="error-big">
                ${oops}
            </h1>
            <h3 class="error-description">
                ${wronglog}
            </h3>
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
            </form>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
