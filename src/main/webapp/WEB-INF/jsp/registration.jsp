<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.registernew" var="registernew"/>
    <fmt:message bundle="${loc}" key="locale.name" var="name"/>
    <fmt:message bundle="${loc}" key="locale.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="locale.gender" var="gender"/>
    <fmt:message bundle="${loc}" key="locale.birthday" var="birthday"/>
    <fmt:message bundle="${loc}" key="locale.email" var="email"/>
    <fmt:message bundle="${loc}" key="locale.password" var="password"/>
    <fmt:message bundle="${loc}" key="locale.passconfirm" var="passconfirm"/>
    <fmt:message bundle="${loc}" key="locale.male" var="male"/>
    <fmt:message bundle="${loc}" key="locale.female" var="female"/>
    <fmt:message bundle="${loc}" key="locale.agree" var="agree"/>
    <fmt:message bundle="${loc}" key="locale.address" var="address"/>
    <fmt:message bundle="${loc}" key="locale.join" var="join"/>
    <fmt:message bundle="${loc}" key="locale.passerr" var="passerr"/>
    <fmt:message bundle="${loc}" key="locale.passconfirmerr" var="passconfirmerr"/>
    <fmt:message bundle="${loc}" key="locale.dateerr" var="dateerr"/>
    <fmt:message bundle="${loc}" key="locale.nameerr" var="nameerr"/>
    <fmt:message bundle="${loc}" key="locale.surnameerr" var="surnameerr"/>
    <fmt:message bundle="${loc}" key="locale.emailerr" var="emailerr"/>
    <fmt:message bundle="${loc}" key="locale.addresserr" var="addresserr"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <title>shop</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>

<main>
    <div class="layout-registration">
        <div class="main-content">
            <div class="title">
                <h3>${registernew}</h3>
            </div>
            <div>
                <form name="registration-form" onsubmit="return validateForm()" method="post" action="Controller">
                    <input type="hidden" name="action" value="register">
                    <div class="inputwrapper">
                        <label>${name}:</label>
                        <div class="error-message">${nameerr}</div>
                        <input type="text" name="customer_name">
                    </div>
                    <div class="inputwrapper">
                        <label>${surname}:</label>
                        <div class="error-message">${surnameerr}</div>
                        <input type="text" name="customer_surname">
                    </div>
                    <div class="inputwrapper">
                        <label>${gender}:</label>
                        <select name="gender">
                            <option value="male">${male}</option>
                            <option value="female">${female}</option>
                        </select>
                    </div>
                    <div id="email-cont">
                        <div class="inputwrapper">
                            <label id="main-email">${email}:</label>
                            <div class="error-message">${emailerr}</div>
                            <input type="text" name="email">
                        </div>
                    </div>
                    <div class="inputwrapper">
                        <label>${password}:</label>
                        <div class="error-message">${passerr}</div>
                        <input type="password" name="password">
                    </div>
                    <div class="inputwrapper">
                        <label>${passconfirm}:</label>
                        <div class="error-message">${passconfirmerr}</div>
                        <input type="password" name="password-confirm">
                    </div>
                    <div class="inputwrapper">
                        <label>${address}:</label>
                        <div class="error-message">${addresserr}</div>
                        <input type="text" name="customer_address">
                    </div>
                    <div class="inputwrapper">
                        <label>${birthday}:</label>
                        <div class="error-message">${dateerr}</div>
                        <input type="date" name="birthday">
                    </div>
                    <div class="inputwrapper checkbox-wrapper">
                        <label>
                            <input type="checkbox" name="agree">
                            ${agree}
                        </label>
                    </div>
                    <div class="inputwrapper">
                        <button type="submit" class="button register">${join}</button type="submit">
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>



<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
