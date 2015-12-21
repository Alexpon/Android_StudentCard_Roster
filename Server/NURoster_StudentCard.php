
<?php
	$con = mysqli_connect("address", "username", "password", "table");
	
	$course = $_POST["course"];
	$index = $_POST["index"];
	
	$statement = mysqli_prepare($con, 'SELECT time FROM nu_studentcard_class WHERE class_name = ?');
	mysqli_stmt_bind_param($statement, "s", $course);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $course_time);
	mysqli_stmt_fetch($statement);
	
	for($x=0; $x<$index; $x++){
		$str1 = 'student';
		$str2 = 'year';
		$str3 = 'month';
		$str4 = 'date';
		$str5 = 'hour';
		$str6 = 'minute';
		$str7 = 'second';
		$num = $x;
		$student = $_POST[$str1.$num];
		$year = $_POST[$str2.$num];
		$month = $_POST[$str3.$num];
		$date = $_POST[$str4.$num];
		$hour = $_POST[$str5.$num];
		$minute = $_POST[$str6.$num];
		$second = $_POST[$str7.$num];
		
		
		
		$statement1 = mysqli_prepare($con, 'SELECT * FROM nu_studentcard_user WHERE card_id = ?');
		mysqli_stmt_bind_param($statement1, "s", $student);
		mysqli_stmt_execute($statement1);

		mysqli_stmt_store_result($statement1);
		mysqli_stmt_bind_result($statement1, $id, $card_id, $student_id, $student_name, $department);

		if(mysqli_stmt_fetch($statement1)){
						
			$day=$year.'-'.$month.'-'.$date;
			$time=$hour.':'.$minute.':'.$second;
			$subCourse = mb_substr( $course,0,-3,"utf-8");
			
			if($time > $course_time){
				$statement2 = mysqli_prepare($con, "INSERT INTO nu_studentcard_roster (card_id, student_id, student_name, class_name, department, date, time, status) 
					VALUES ('$student', '$student_id', '$student_name', '$subCourse', '$department', '$day', '$time', 'late')");
			}
			else{
				$statement2 = mysqli_prepare($con, "INSERT INTO nu_studentcard_roster (card_id, student_id, student_name, class_name, department, date, time, status) 
					VALUES ('$student', '$student_id', '$student_name', '$subCourse', '$department', '$day', '$time', 'on time')");
			}
			mysqli_stmt_execute($statement2);
			mysqli_stmt_close($statement2);
		}
}

	mysqli_stmt_close($statement1);	


	mysqli_close($con);
?>