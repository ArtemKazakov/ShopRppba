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
    <fmt:message bundle="${loc}" key="locale.itemname" var="itemname"/>
    <fmt:message bundle="${loc}" key="locale.price" var="price"/>
    <fmt:message bundle="${loc}" key="locale.category" var="category"/>
    <fmt:message bundle="${loc}" key="locale.description" var="description"/>
    <fmt:message bundle="${loc}" key="locale.image" var="image"/>
    <fmt:message bundle="${loc}" key="locale.save" var="save"/>
    <fmt:message bundle="${loc}" key="locale.delete" var="delete"/>
    <fmt:message bundle="${loc}" key="locale.itemnameerr" var="itemnameerr"/>
    <fmt:message bundle="${loc}" key="locale.descrerr" var="descrerr"/>
    <fmt:message bundle="${loc}" key="locale.priceerr" var="priceerr"/>

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
                <img src="${item.imageUrl}" alt="${item.imageUrl}" id="image">
            </div>
            <div class="grid item-detail-info">
                <form name="item-form" onsubmit="return validateItem()" action="Controller" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="edititem">
                    <input type="hidden" name="currentImg" value="${item.imageUrl}">
                    <input type="hidden" name="item_id" value="${item.id}">
                <div class="inputwrapper">
                    <label>${itemname}:</label>
                    <div class="error-message">${itemnameerr}</div>
                    <input type="text" name="item_name" value="${item.name}">
                </div>
                <div class="inputwrapper">
                    <label>${price}:</label>
                    <div class="error-message">${priceerr}</div>
                    <input type="text" name="price" value="${item.price}">
                </div>
                <div class="inputwrapper">
                    <label>${category}:</label>
                    <select name="category">
                        <option value="men">${men}</option>
                        <option value="ladies">${ladies}</option>
                        <option value="kids">${kids}</option>
                        <option value="accessories">${accessories}</option>
                    </select>
                </div>
                <div class="inputwrapper">
                    <label>${description}:</label>
                    <div class="error-message">${descrerr}</div>
                    <textarea name="description" rows="8" cols="33">${item.description}</textarea>
                </div>
                <div class="inputwrapper">
                    <label>${image}:</label>
                    <input onChange="imagePreview(this)" type="file" name="image">
                </div>
                <div class="item-buttons">
                    <button class="button" type="submit">${save}</button>
                    <button class="button" type="button" onclick="location.href='Controller?action=deleteitem&item_id=${item.id}'">${delete}</button>
                </div>
                </form>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"></jsp:include>

<script type="text/javascript">
    function imagePreview(input) {
        var file = input.files[0];
        console.log(file);
        var reader = new FileReader();
        var image = document.getElementById('image');

        reader.readAsDataURL(file);
        reader.onload = function(e){
            image.src = e.target.result;
        }
    }
</script>
</body>
</html>
