function auto() {
	document.form1.action="auto_refresh.php"
	document.form1.submit();
}

function history() {
/*
	var getForm = document.forms["form1"];
	var getName = getForm.elements.course.value;
	var getYear = getForm.elements.year.value;
	var getMonth = getForm.elements.month.value;
	var getDay = getForm.elements.day.value;
*/
	document.form1.action = "history.php";
	document.form1.submit();
}
