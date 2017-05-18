<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.description" var="description"/>
    <fmt:message bundle="${loc}" key="locale.size" var="size"/>
    <fmt:message bundle="${loc}" key="locale.addtocart" var="addtocart"/>
    <fmt:message bundle="${loc}" key="locale.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="locale.itemreviews" var="itemreviews"/>
    <fmt:message bundle="${loc}" key="locale.leavereview" var="leavereview"/>
    <fmt:message bundle="${loc}" key="locale.submitreview" var="submitreview"/>
    <fmt:message bundle="${loc}" key="locale.close" var="close"/>
    <fmt:message bundle="${loc}" key="locale.comment" var="comment"/>
    <fmt:message bundle="${loc}" key="locale.rate" var="rate"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <title>shop</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<main>
    <div class="layout">
        <div class="row item-detail">
            <div class="grid item-detail-image">
                <img src="${item.imageUrl}" alt="${item.imageUrl}">
            </div>
            <div class="grid item-detail-info">
                <h1 class="item-name">${item.name}&nbsp;&nbsp;<span class="mark-${item.rating}"></span></h1>
                <div class="item-price">
                    $${item.price}
                </div>
                <div class="item-description">
                    <h2>${description}</h2>
                    <div>
                        ${item.description}
                    </div>
                </div>
                <c:if test="${sessionScope.user != null}">
                <form action="Controller" method="POST">
                <c:if test="${item.category != 'accessories'}">
                <div class="inputwrapper">
                    <label>${size}:</label>
                    <select name="size">
                        <option>xs</option>
                        <option>s</option>
                        <option>m</option>
                        <option>l</option>
                        <option>xl</option>
                    </select>
                </div>
                </c:if>

                <input type="hidden" name="action" value="addtocart">
                <input type="hidden" name="item_id" value="${item.id}">
                <input type="hidden" name="user_id" value="${sessionScope.user.id}">
                <div class="item-buttons">
                    <button class="button" type="submit">${addtocart}</button>
                    <c:if test="${sessionScope.user.role == 'admin'}">
                        <button type="button" class="button" onclick="location.href='Controller?action=gotoedititem&item_id=${item.id}'">${edit}</button>
                    </c:if>
                </div>
                </form>
                </c:if>
                <h3 class="review-title">${itemreviews}</h3>
                <c:if test="${sessionScope.user != null}">
                    <div class="leave-review" id="open-btn" onclick="openReviewBox()">${leavereview}</div>
                    <div class="leave-review" id="close-btn" onclick="closeReviewBox()" style="display: none;">${close}</div>
                </c:if>
                <div class="review-box" id="rbox" style="display: none;">
                    <form action="Controller" method="post">
                        <input type="hidden" name="action" value="addreview">
                        <input type="hidden" name="item_id" value="${item.id}">
                        <div class="inputwrapper">
                            <label>${rate}</label>
                            <select name="rating">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                            </select>
                        </div>
                        <div class="inputwrapper">
                            <label>${comment}:</label>
                            <textarea name="comment" rows="8" cols="33"></textarea>
                        </div>
                        <div class="item-buttons">
                            <button class="button" type="submit">${submitreview}</button>
                        </div>
                    </form>
                </div>
                <div class="reviews" id="rvws">
                    <c:forEach items="${reviews}" var="review">
                        <div class="review">
                            <span class="review-author">${review.user.name}&nbsp;${review.user.surname}</span>
                            <span class="mark-${review.rating}"></span>
                            <c:if test="${(sessionScope.user.id == review.user.id) || (sessionScope.user.role == 'admin')}">
                                <span class="remove-review" onclick="location.href='Controller?action=deletereview&item_id=${review.itemId}&user_id=${review.user.id}'">✖</span>
                            </c:if>
                            <div class="review-date">${review.reviewDate}</div>
                            <p>${review.comment}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</main>


<script type="text/javascript">
    function openReviewBox(){
        var form = document.getElementById("rbox");
        var reviews = document.getElementById("rvws");
        var openBtn = document.getElementById("open-btn");
        var closeBtn = document.getElementById("close-btn");

        form.style.display = "block";
        closeBtn.style.display = "block";
        reviews.style.display = "none";
        openBtn.style.display = "none";
    }

    function closeReviewBox(){
        var form = document.getElementById("rbox");
        var reviews = document.getElementById("rvws");
        var openBtn = document.getElementById("open-btn");
        var closeBtn = document.getElementById("close-btn");

        form.style.display = "none";
        closeBtn.style.display = "none";
        reviews.style.display = "block";
        openBtn.style.display = "block";
    }
</script>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
