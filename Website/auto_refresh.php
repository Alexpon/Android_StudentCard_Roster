<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<html>

	<head>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		
		<Style>
			html, body{
				background:url('res/auto.jpg');
				align:center;
				background-size: 100% 100%;
				width: 100%;
				height: 100%;
				margin: 0;
				padding: 0;
				font-family:Verdana, Geneva, sans-serif;
			}
		</Style>
	</head>
	<body>
<?php
	$num = $_GET['course'];
	switch($num){
		case "10410011":
			$course_name = "智慧型運輸系統";
			break;
		case "10410021":
			$course_name = "計算機概論";
			break;
		case "10410031":
			$course_name = "智慧型運輸系統特論";
			break;
	}
	$dd = date("Y-m-d");
?>
		<div class="container">
			<h1>今日即時更新</h1>
			<h3><?php print $dd ?> 
			<h2><?php print $course_name ?></h2>
			           
			<table class="table table-condensed">
				<thead>
				  <tr>
					<th>學號</th>
					<th>姓名</th>
					<th>系級</th>
					<th>日期</th>	
					<th>時間</th>	
					<th>狀態</th>
				  </tr>
				</thead>
				<tbody>
<?php	
		include("conf.php");
		$course = "nu_studentcard_course_".$_GET['course'];
		$result = mysql_query("SELECT * FROM `$course`");
		while($row = mysql_fetch_row($result)){
			if(strtotime($dd) != strtotime($row[4])){
				$row[4] = "";
				$row[5] = "";
				$row[6] = "";
			}
			print "<tr>
					<td>".$row[1]."</td>
					<td>".$row[2]."</td>
					<td>".$row[3]."</td>
					<td>".$row[4]."</td>
					<td>".$row[5]."</td>
					<td>".$row[6]."</td>
				  </tr>";
		}
?> 
				</tbody>
			</table>
		</div>
	</body>
  <meta content='5'; url=http://140.116.97.92/StudentCardRoster/auto_refresh.php' http-equiv='refresh'>
</html>