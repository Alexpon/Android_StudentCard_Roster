<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<html>

	<head>
		<style type="text/css">
			div.transbox{
				margin:30px; 
				background-color: #ffffff;
				border: 1px solid black;
				/* for IE */
				filter:alpha(opacity=75);
				/* CSS3 standard */
				opacity:0.75;
			}

			div.transbox p
			{
			  margin: 30px 40px;
			}
		
			html, body{
				background:url('res/background.jpg');
				align:center;
				background-size: 100% 100%;
				width: 100%;
				height: 100%;
				margin: 0;
				padding: 0;
				font-family:Verdana, Geneva, sans-serif;
			}
		</style>
	</head>
	<body>
	
		
		<div class="transbox" align="center">

		<br><br><br><br><br>
		<br><br><br>
	<p>
		<h1>
			Student Card Roster
		</h1>
		
		<br><br><br>
		
		<?php 
			$date = date("Y-m-d");
			$sub = explode('-', $date);
		?>
		
		<form method="GET" action="Student_Roster.php">
		<h2>
			<input type="radio" name="course" value="智慧型運輸系統" checked="">智慧型運輸系統
			<input type="radio" name="course" value="計算機概論">計算機概論
		</h2>
		<br><br>
		<h1>
			<input type="number" name="year" min="2015" max="2100" step="1" value=<?php echo $sub[0] ?> size="20">年
			<input type="number" name="month" min="1" max="12" step="1" value=<?php echo $sub[1] ?> size="20">月
			<input type="number" name="day" min="1" max="31" step="1" value=<?php echo $sub[2] ?> size="20">日
			<br><br>
			<input type="submit" value="送出">
		</h1>
		<br>
</p>
	</body>

</html>