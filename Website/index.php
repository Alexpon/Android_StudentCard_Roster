<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<html>

	<head>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/change.js"></script>
		
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
		

	
		<form name="form1" action="">
			<h2>
				<input type="radio" name="course" value="10410011" checked="">智慧型運輸系統
				<input type="radio" name="course" value="10410021">計算機概論
				<input type="radio" name="course" value="10410031">智慧型運輸系統特論
				
			</h2>
			<br><br>
			<h1>
				<input type="number" name="year" min="2015" max="2100" step="1" value=<?php echo $sub[0] ?> size="20">年
				<input type="number" name="month" min="1" max="12" step="1" value=<?php echo $sub[1] ?> size="20">月
				<input type="number" name="day" min="1" max="31" step="1" value=<?php echo $sub[2] ?> size="20">日
				<br><br>
				<input type="button" class="btn btn-danger" value="今日即時" type="submit" onClick="auto()">
				<input type="button" class="btn btn-danger" value="歷史紀錄" type="submit" onClick="history()">
			</h1>
		</form>
		
		<br>
</p>
	</body>

</html>