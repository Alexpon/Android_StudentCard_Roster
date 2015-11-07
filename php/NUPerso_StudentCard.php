
<?php
	$con = mysqli_connect("address", "un", "pw", "db");
	$card_id = $_POST["card_id"];
	$student_id = $_POST["student_id"];
	$student_name = $_POST["student_name"];
	$department = $_POST["department"];
	
	$statement = mysqli_prepare($con, "SELECT * FROM user_table WHERE card_id = ?");
	mysqli_stmt_bind_param($statement, "s", $card_id);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	
	if(mysqli_stmt_fetch($statement)==null){
		$statement = mysqli_prepare($con, "INSERT INTO user_table (card_id, student_id, student_name, department) VALUES (?, ?, ?, ?)");
		mysqli_stmt_bind_param($statement, "ssss", $card_id, $student_id, $student_name, $department);
		mysqli_stmt_execute($statement);	
	}
	

	mysqli_stmt_close($statement);
	mysqli_close($con);
?>