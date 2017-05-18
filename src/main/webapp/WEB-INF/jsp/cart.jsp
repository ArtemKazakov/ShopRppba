<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.yourcart" var="yourcart"/>
    <fmt:message bundle="${loc}" key="locale.emptycart" var="emptycart"/>
    <fmt:message bundle="${loc}" key="locale.size" var="size"/>
    <fmt:message bundle="${loc}" key="locale.price" var="price"/>
    <fmt:message bundle="${loc}" key="locale.quantity" var="quantity"/>
    <fmt:message bundle="${loc}" key="locale.removefromcart" var="removefromcart"/>
    <fmt:message bundle="${loc}" key="locale.overallprice" var="overallprice"/>
    <fmt:message bundle="${loc}" key="locale.overallquantity" var="overallquantity"/>
    <fmt:message bundle="${loc}" key="locale.submitorder" var="submitorder"/>

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
            <h3>${yourcart}:</h3>
            <table class="cart-items">
                <c:if test="${cart.size == 0}">
                    ${emptycart}
                </c:if>

                <c:forEach items="${sessionScope.cart.items}" var="item" varStatus="loop">
                    <tr>
                        <td><img src="${item.key.imageUrl}"></td>
                        <td>${item.key.name}</td>
                        <td>${size}: ${item.key.size}</td>
                        <td>${price}: $${item.key.price}</td>
                        <td>${quantity}: ${item.value}</td>
                        <td>
                            <button class="button" onclick="location.href='Controller?action=removefromcart&item_id=${item.key.id}&user_id=${sessionScope.user.id}&size=${item.key.size}'">
                                ${removefromcart}
                            </button>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${cart.size != 0}">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>${overallprice}: $${sessionScope.cart.cost}</td>
                    <td>${overallquantity}: ${sessionScope.cart.size}</td>
                    <td><button class="button register" onclick="location.href='Controller?action=redirect&page=ordersubmit.jsp'">${submitorder}</button></td>
                </tr>
                </c:if>
            </table>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>

