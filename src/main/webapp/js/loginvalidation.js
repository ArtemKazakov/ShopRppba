function validateLoginForm() {
    var form = document.forms["login-form"];

    var email = form["email"];
    var password = form["password"];

    var emailRegEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    var validationCheck = new Array();

    validationCheck.push(validateWithRegExp(email, emailRegEx))

    if(password.value.empty()){
        makeInvalid(password);
        validationCheck.push(false);
    } else {
        makeValid(password)
        validationCheck.push(true);
    }

    if(validationCheck.includes(false)){
        return false;
    } else {
        return true;
    }
}

function makeValid(element){
    if(element.className == "invalid") element.className = "";
    element.parentNode.getElementsByClassName("error-message")[0].style.display="none";
}

function makeInvalid(element){
    element.className = "invalid";
    element.parentNode.getElementsByClassName("error-message")[0].style.display="block";
}

function validateWithRegExp(element, regExp) {
    if (element.value.match(regExp) == null) {
        makeInvalid(element);
        return false;
    } else {
        makeValid(element);
        return true;
    }
}