<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
<style>
h1{font-family:"Comic Sans MS";
width:200px;}
h2{font-family:"Comic Sans MS";
width:200px;}
h3{font-family:"Comic Sans MS";
width:200px;}
</style>
</head>
<body align="center" >

<br><br>

<center>
	<h1>
		Student Card Roster <br>
	</h1>

<?php
	$num = $_GET['course'];
	switch($num){
		case "10410011":
			$course_name = "智慧型運輸系統";
			break;
		case "10410021":
			$course_name = "計算機概論";
			break;	
	}
?>
	
	<h2>
		<?php echo $course_name;?> <br>
		<?php echo $_GET['year']."年".$_GET['month']."月".$_GET['day']."日";?>
	</h2>
</center>
	<table border="1" align="center" style="border: 5px  dashed rgb(109, 2, 107); background-color: rgb(255, 255, 255);">
		<tr>
		<td><h3>學號</h3></td><td><h3>姓名</h3></td><td><h3>系級</h3></td><td><h3>日期</h3></td><td><h3>時間</h3></td><td><h3>狀態</h3></td>
		</tr>
		
<?php	
		include("conf.php");
		$d = $_GET['year']."-".$_GET['month']."-".$_GET['day'];
		$course = $_GET['course'];
		$result = mysql_query("SELECT * FROM `nu_studentcard_roster` WHERE `date`= '$d' AND `class_name`='$course_name'");
		
		while($row = mysql_fetch_row($result)){
			print "<tr><td><h3>".$row[2]."</h3></td><td><h3>".$row[3].
			"</h3></td><td><h3>".$row[5]."</h3></td><td><h3>".$row[6].
			"</h3></td><td><h3>".$row[7]."</h3></td><td><h3>".$row[8];
		}
?>

  </body>

</html>