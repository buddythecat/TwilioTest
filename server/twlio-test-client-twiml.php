<?php
header('Content-type: text/xml');
$callerId = "5168744159";
$callID = $_REQUEST["CallID"];
?>
<Response>
    <Dial callerId="<?php echo $callerId ?>" hangupOnStar="true"> 
        <Number><?php echo $_REQUEST["PhoneNumber"]; ?></Number>
        <Say>Disconnected</Say>
    </Dial>
</Response>