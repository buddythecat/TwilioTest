<?php
header('Content-type: text/xml');
	$input = $_REQUEST['Digits'];
	$callId = $_REQUEST['callid'];
	$file = "log.txt";
	
	echo " <Say>We didn't receive any input. Goodbye!</Say> ";
	
	$Saved_File = fopen($file, 'w');
	fwrite($Saved_File, $callId);
	fclose($Saved_File);
?>