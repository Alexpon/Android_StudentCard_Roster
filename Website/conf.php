<?php

$host="address";
$user="username";
$upwd="password";
$db="table";

$link=mysql_connect($host,$user,$upwd) or die ("Unable to connect!");
mysql_select_db($db, $link) or die ("Unable to select database!");

?>