<?php
header('Content-type: text/xml');
$callerId = "5168744159"
?>
<Response>
    <Dial callerId="<?php echo $callerId ?>"> 
        <Number><?php echo $_REQUEST["PhoneNumber"]; ?></Number>
    </Dial>
</Response>