
<?php
	$con = mysqli_connect("address", "username", "password", "table");
	
	$statement = mysqli_prepare($con, 'SELECT * FROM nu_studentcard_class');
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $class_id, $class_name, $time);
	
	$class = array();
	
	while (mysqli_stmt_fetch($statement)) {
		$class[] = array("class_id" => $class_id, "class_name" => $class_name);
	}
	
	echo json_encode($class);

	mysqli_stmt_close($statement);
	
	mysqli_close($con);
	
?>