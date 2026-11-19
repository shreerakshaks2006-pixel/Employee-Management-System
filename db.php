<?php
$host     = "127.0.0.1";
$user     = "root";
$password = "";
$database = "hrm_db";
$port     = 3307;

$conn = mysqli_connect($host, $user, $password, $database, $port);

if (!$conn) {
    die("<div style='color:red;padding:20px;font-family:sans-serif;'>
        <b>Database Connection Failed:</b> " . mysqli_connect_error() . "
        <br><br>Make sure MySQL is running in XAMPP Control Panel.
    </div>");
}
?>
