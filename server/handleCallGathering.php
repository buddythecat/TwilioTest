<?php 
header('Content-type: text/xml');
$callID = $_REQUEST["callID"];
echo "<Response>";
echo "<Gather action=\"/process_gather.php?callid=$callID\" timeout=\"100\" method=\"GET\">"; ?>
	<Say> Please hit star to end </Say>
</Gather>
</Response>