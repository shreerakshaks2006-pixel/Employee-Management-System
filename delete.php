<?php
include 'db.php';
$id = isset($_GET['id']) ? (int)$_GET['id'] : 0;
if ($id > 0) {
    mysqli_query($conn, "DELETE FROM employees WHERE id=$id");
}
header("Location: index.php?msg=deleted");
exit;
