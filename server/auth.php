<?php
// This php is to be put on your server, and change the URLs in BasicPhone.m
// to point to this file's public location. 
require "Services/Twilio/Capability.php";

$accountSid = "AC56281de41698b835e7d1eb90b50726eb";
$authToken = "72171f5acb13554d1b0c3e3e8d232cac";
 
// The app outbound connections will use: 
$appSid = "AP28606685a40a4f52a3f963902914bad0";

// The client name for inbound connections: requested from App
$clientName = twilioTest;

$capability = new Services_Twilio_Capability($accountSid, $authToken);

// This would allow inbound connections as $clientName:
$capability->allowClientIncoming($clientName);
 
// This allows outgoing connections to $appSid with the "From" parameter being $clientName 
$capability->allowClientOutgoing($appSid, array(), $clientName);

// This would return a token to use with Twilio based on 
// the account and capabilities defined above 
$token = $capability->generateToken();

echo $token; 
?>