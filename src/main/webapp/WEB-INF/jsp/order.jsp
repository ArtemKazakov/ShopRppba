<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.date" var="date"/>
    <fmt:message bundle="${loc}" key="locale.status" var="status"/>
    <fmt:message bundle="${loc}" key="locale.comment" var="comment"/>
    <fmt:message bundle="${loc}" key="locale.yourcart" var="yourcart"/>
    <fmt:message bundle="${loc}" key="locale.size" var="size"/>
    <fmt:message bundle="${loc}" key="locale.price" var="price"/>
    <fmt:message bundle="${loc}" key="locale.quantity" var="quantity"/>
    <fmt:message bundle="${loc}" key="locale.overallquantity" var="overallquantity"/>
    <fmt:message bundle="${loc}" key="locale.overallprice" var="overallprice"/>
    <fmt:message bundle="${loc}" key="locale.complete" var="complete"/>
    <fmt:message bundle="${loc}" key="locale.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="locale.youritems" var="youritems"/>

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
            <h2>${order.customer.name}&nbsp;${order.customer.surname}</h2>
            <h4>#${order.id}</h4>
            <h4>${date}: ${order.orderDate}</h4>
            <h4>${status}: ${order.status}</h4>
            <h4>${comment}:</h4>
            <p>${order.comment}</p>
            <h3>${youritems}:</h3>
            <table class="order-items" width="100%">
                <c:forEach items="${order.items}" var="item" varStatus="loop">
                    <tr>
                        <td><img src="${item.key.imageUrl}"></td>
                        <td>${item.key.name}</td>
                        <td>${size}: ${item.key.size}</td>
                        <td>${price}: $${item.key.price}</td>
                        <td>${quantity}: ${item.value}</td>
                    </tr>
                </c:forEach>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>${overallprice}: $${order.price}</td>
                        <td>${overallquantity}: ${order.size}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <c:if test="${(sessionScope.user.role == 'admin') && (order.status == 'in progress')}">
                                <button class="button register" onclick="location.href='Controller?action=changeorderstatus&status=completed&order_id=${order.id}'">${complete}</button>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${(order.status == 'in progress')}">
                                <button class="button register" onclick="location.href='Controller?action=changeorderstatus&status=cancelled&order_id=${order.id}'">${cancel}</button>
                            </c:if>
                        </td>
                    </tr>
            </table>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>

