<?php
include 'db.php';
$id  = isset($_GET['id']) ? (int)$_GET['id'] : 0;
$emp = mysqli_fetch_assoc(mysqli_query($conn, "SELECT status FROM employees WHERE id=$id"));
if ($emp) {
    $newStatus = $emp['status'] === 'Active' ? 'Disabled' : 'Active';
    mysqli_query($conn, "UPDATE employees SET status='$newStatus' WHERE id=$id");
}
header("Location: index.php?msg=toggled");
exit;
