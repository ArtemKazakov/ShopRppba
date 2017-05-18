<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.birthday" var="birthday"/>
    <fmt:message bundle="${loc}" key="locale.gender" var="gender"/>
    <fmt:message bundle="${loc}" key="locale.address" var="address"/>
    <fmt:message bundle="${loc}" key="locale.orders" var="custorders"/>
    <fmt:message bundle="${loc}" key="locale.noorders" var="noorders"/>
    <fmt:message bundle="${loc}" key="locale.myorders" var="myorders"/>
    <fmt:message bundle="${loc}" key="locale.cancelledorders" var="cancelledorders"/>
    <fmt:message bundle="${loc}" key="locale.progressorders" var="progressorders"/>
    <fmt:message bundle="${loc}" key="locale.completedorders" var="completedorders"/>
    <fmt:message bundle="${loc}" key="locale.date" var="date"/>
    <fmt:message bundle="${loc}" key="locale.price" var="price"/>
    <fmt:message bundle="${loc}" key="locale.status" var="status"/>
    <fmt:message bundle="${loc}" key="locale.details" var="details"/>

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
            <c:if test="${sessionScope.user.role == 'user'}">
            <h2>${sessionScope.user.name}&nbsp;${sessionScope.user.surname}</h2>
            <h4>${sessionScope.user.email}</h4>
            <h4>${birthday}: ${sessionScope.user.birthday}</h4>
            <h4>${gender}: ${sessionScope.user.gender}</h4>
            <h4>${address}: ${sessionScope.user.address}</h4>
            <h4>${custorders}:</h4>
            <table width="100%">
                <c:if test="${orders_size == 0}">
                    <p>${noorders}</p>
                </c:if>

                <c:forEach items="${orders}" var="item" varStatus="loop">
                    <tr>
                        <td>${item.id}</td>
                        <td>${date}: ${item.orderDate}</td>
                        <td>${status}: ${item.status}</td>
                        <td>${price}: $${item.price}</td>
                        <td><button class="button" onclick="location.href='Controller?action=gotoorder&order_id=${item.id}'">${details}</button></td>
                    </tr>
                </c:forEach>
            </table>
            </c:if>
            <c:if test="${sessionScope.user.role == 'admin'}">
                <h2>${sessionScope.user.name}&nbsp;${sessionScope.user.surname}</h2>
                <h4>${sessionScope.user.email}</h4>
                <h4>${birthday}: ${sessionScope.user.birthday}</h4>
                <h4>${gender}: ${sessionScope.user.gender}</h4>
                <h4>${address}: ${sessionScope.user.address}</h4>
                <h4>${custorders}:</h4>
                <div class="row">
                    <a href="javascript:void(0)" class="tablink tablink-active" onclick="openOrders(event, 'my');">
                        <div class="grid col-4">${myorders}</div>
                    </a>
                    <a href="javascript:void(0)" class="tablink" onclick="openOrders(event, 'process');">
                        <div class="grid col-4">${progressorders}</div>
                    </a>
                    <a href="javascript:void(0)" class="tablink" onclick="openOrders(event, 'completed');">
                        <div class="grid col-4">${completedorders}</div>
                    </a>
                    <a href="javascript:void(0)" class="tablink" onclick="openOrders(event, 'cancelled');">
                        <div class="grid col-4">${cancelledorders}</div>
                    </a>
                </div>
                <div class="orders" id="my">
                    <table width="100%">
                    <c:forEach items="${orders}" var="item" varStatus="loop1">
                        <c:if test="${item.customer.id == sessionScope.user.id}">
                            <tr>
                                <td>${item.id}</td>
                                <td>${date}: ${item.orderDate}</td>
                                <td>${status}: ${item.status}</td>
                                <td>${price}: $${item.price}</td>
                                <td><button class="button" onclick="location.href='Controller?action=gotoorder&order_id=${item.id}'">${details}</button></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </table>
                </div>
                <div class="orders" id="cancelled" style="display: none;">
                    <table width="100%">
                        <c:forEach items="${orders}" var="item" varStatus="loop2">
                            <c:if test="${item.status == 'cancelled'}">
                                <tr>
                                    <td>#${item.id}</td>
                                    <td>${item.customer.name}&nbsp;${item.customer.surname}</td>
                                    <td>${item.customer.email}</td>
                                    <td>${date}: ${item.orderDate}</td>
                                    <td>${price}: $${item.price}</td>
                                    <td><button class="button" onclick="location.href='Controller?action=gotoorder&order_id=${item.id}'">${details}</button></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
                <div class="orders" id="completed" style="display: none;">
                    <table width="100%">
                        <c:forEach items="${orders}" var="item" varStatus="loop3">
                            <c:if test="${item.status == 'completed'}">
                                <tr>
                                    <td>#${item.id}</td>
                                    <td>${item.customer.name}&nbsp;${item.customer.surname}</td>
                                    <td>${item.customer.email}</td>
                                    <td>${date}: ${item.orderDate}</td>
                                    <td>${price}: $${item.price}</td>
                                    <td><button class="button" onclick="location.href='Controller?action=gotoorder&order_id=${item.id}'">${details}</button></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
                <div class="orders" id="process" style="display: none;">
                    <table width="100%">
                        <c:forEach items="${orders}" var="item" varStatus="loop4">
                            <c:if test="${item.status == 'in progress'}">
                                <tr>
                                    <td>#${item.id}</td>
                                    <td>${item.customer.name}&nbsp;${item.customer.surname}</td>
                                    <td>${item.customer.email}</td>
                                    <td>${date}: ${item.orderDate}</td>
                                    <td>${price}: $${item.price}</td>
                                    <td><button class="button" onclick="location.href='Controller?action=gotoorder&order_id=${item.id}'">${details}</button></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</main>

<script type="text/javascript">
    function openOrders(evt, category){
        var i, x, tablinks;
        x = document.getElementsByClassName("orders");

        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }

        tablinks = document.getElementsByClassName("tablink");
        for (i = 0; i < x.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" tablink-active", "");
        }

        document.getElementById(category).style.display = "block";
        evt.currentTarget.className += " tablink-active";
    }
</script>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
