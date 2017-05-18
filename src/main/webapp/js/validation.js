function validateForm(){
	var form = document.forms["registration-form"];

	var name = form["customer_name"];
	var surname = form["customer_surname"];
	var email = form["email"];
	var password = form["password"];
	var passwordConfirm = form["password-confirm"];
	var address = form["customer_address"];
	var birthday = form["birthday"];
	var agree = form["agree"];

	var nameRegEx = /^[a-zA-Z]+([-\'][a-zA-Z]+)*$/;
	var surnameRegEx = /^[a-zA-Z]+([-\'\s][a-zA-Z]+)*$/;
	var emailRegEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var passwordRegEx = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,25}$/;
	var addressRegEx = /^(?=.*\d)[a-zA-Z]+([0-9a-zA-Z-_\\.,;:\s]){14,}$/;

	var validationCheck = new Array();

	validationCheck.push(validateUsername(name, nameRegEx));
	validationCheck.push(validateUsername(surname, surnameRegEx));
	validationCheck.push(validateWithRegExp(email, emailRegEx));
	validationCheck.push(validateWithRegExp(password, passwordRegEx));
	validationCheck.push(validatePasswordConfirmation(passwordConfirm, password));
	validationCheck.push(validateWithRegExp(address, addressRegEx));
	validationCheck.push(validateBirthdayDate(birthday));
	validationCheck.push(validateAgreement(agree));

	//console.log(validationCheck);
	if(validationCheck.includes(false)){
		return false;
	} else {
		return true;
	}
}

function validateItem() {
	var form = document.forms["item-form"];

	var name = form["item_name"];
	var description = form["description"];
	var price = form["price"];

	var nameRegExp = /^[a-zA-Z]+([-\'\s\:][a-zA-Z]+)*$/;
	var descrRegExp = /^[0-9a-zA-Z,=\.\-\\:\s\"]+$/;
	var priceRegExp = /^(\d+\.\d{1,2})$/;

	var validationCheck = new Array();

	validationCheck.push(validateWithRegExp(name, nameRegExp));
	validationCheck.push(validateUsername(description, descrRegExp));
	validationCheck.push(validateWithRegExp(price, priceRegExp));

	if(validationCheck.includes(false)){
		return false;
	} else {
		return true;
	}
}

function submitValidation() {
	var form = document.forms["ordersub-form"];

	var address = form["address"];

	var addressRegEx = /^(?=.*\d)[a-zA-Z]+([0-9a-zA-Z-_\\.,;:\s]){14,}$/;

	var check = validateWithRegExp(address, addressRegEx);

	return check;
}

function makeValid(element){
	if(element.className == "invalid") element.className = "";
	element.parentNode.getElementsByClassName("error-message")[0].style.display="none";
}

function makeInvalid(element){
	element.className = "invalid";
	element.parentNode.getElementsByClassName("error-message")[0].style.display="block";
}

function validateUsername(username, usernameRegEx){
	if((username.value.match(usernameRegEx) == null) || (username.value.length < 5) || (username.value.length > 30)){
		makeInvalid(username)
		return false;
	} else {
		makeValid(username);
		return true;
	}
}

function validateWithRegExp(element, regExp){
	if(element.value.match(regExp) == null){
		makeInvalid(element);
		return false;
	} else{
		makeValid(element);
		return true;
	}
}

function validateBirthdayDate(birthday){
	if(birthday.value != ""){
		var now = new Date();
		var birthdayDate = new Date(birthday.value);
		var age = Math.abs(now.getFullYear() - birthdayDate.getFullYear());

		if((age < 7)||(age > 120)){
			makeInvalid(birthday);
			return false;
		} else {
			makeValid(birthday);
			return true;
		}

	} else {
		makeInvalid(birthday);
		return false;
	}
}

function validateAgreement(checkbox){
	if(!checkbox.checked){
		checkbox.parentNode.style.color = "red";
		return false;
	} else {
		checkbox.parentNode.style.color = "black"
		return true;
	}
}

function validatePasswordConfirmation(passwordConfirm, password){
	if(passwordConfirm.value != password.value){
		makeInvalid(passwordConfirm);
		return false;
	} else {
		makeValid(passwordConfirm);
		return true;
	}
}