<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.price" var="price"/>
    <fmt:message bundle="${loc}" key="locale.numberofitems" var="numberofitems"/>
    <fmt:message bundle="${loc}" key="locale.fillform" var="fillform"/>
    <fmt:message bundle="${loc}" key="locale.delivery" var="delivery"/>
    <fmt:message bundle="${loc}" key="locale.pickup" var="pickup"/>
    <fmt:message bundle="${loc}" key="locale.courier" var="courier"/>
    <fmt:message bundle="${loc}" key="locale.address" var="address"/>
    <fmt:message bundle="${loc}" key="locale.addresserr" var="addresserr"/>
    <fmt:message bundle="${loc}" key="locale.comment" var="comment"/>
    <fmt:message bundle="${loc}" key="locale.submitorder" var="submitorder"/>
    <fmt:message bundle="${loc}" key="locale.addresserr" var="addresserr"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <title>shop</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<main>
    <div class="layout">
        <div class="row cart-detail">
            <h2>${sessionScope.user.name}&nbsp;${sessionScope.user.surname}</h2>
            <h4>${price}: $${cart.cost}</h4>
            <h4>${numberofitems}: ${cart.size}</h4>
            <h3>${fillform}</h3>
            <form name="ordersub-form" method="post" action="Controller" onsubmit="return submitValidation()">
                <input type="hidden" name="action" value="submitorder">
                <input type="hidden" name="user_id" value="${sessionScope.user.id}">
                <div class="inputwrapper">
                    <label>${delivery}</label>
                    <select name="delivery">
                        <option value="0">
                            ${pickup}
                        </option>
                        <option value="1">
                            ${courier}
                        </option>
                    </select>
                </div>
                <div class="inputwrapper">
                    <label>address:</label>
                    <div class="error-message">${addresserr}</div>
                    <input type="text" name="address" value="${sessionScope.user.address}">
                </div>
                <div class="inputwrapper">
                    <label>${comment}</label>
                    <textarea name="comment" id="" cols="60" rows="10"></textarea>
                </div>
                <div class="inputwrapper">
                    <button type="submit" class="button register">${submitorder}</button>
                </div>
            </form>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>

