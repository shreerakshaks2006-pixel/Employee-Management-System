<?php
include 'db.php';
$id  = isset($_GET['id']) ? (int)$_GET['id'] : 0;
$emp = mysqli_fetch_assoc(mysqli_query($conn, "SELECT * FROM employees WHERE id=$id"));
if (!$emp) { header("Location: index.php"); exit; }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HRM - View Employee</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="top-header">
    <h1>HRM</h1>
    <div class="bookmark-icon"><i class="fas fa-bookmark"></i></div>
</div>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>Employee Details</h2>
            <a href="edit.php?id=<?= $emp['id'] ?>" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Edit</a>
            <a href="index.php" class="btn btn-secondary btn-sm"><i class="fas fa-arrow-left"></i> Back</a>
        </div>
        <div class="card-body">
            <div class="detail-box">
                <div class="detail-row">
                    <span class="detail-label">ID</span>
                    <span class="detail-value"><?= $emp['id'] ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Full Name</span>
                    <span class="detail-value"><?= htmlspecialchars($emp['name']) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email</span>
                    <span class="detail-value"><?= htmlspecialchars($emp['email']) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Phone</span>
                    <span class="detail-value"><?= htmlspecialchars($emp['phone']) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Department</span>
                    <span class="detail-value"><?= htmlspecialchars($emp['department']) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Role</span>
                    <span class="detail-value"><?= htmlspecialchars($emp['role']) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Salary</span>
                    <span class="detail-value">₹ <?= number_format($emp['salary'], 2) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Hire Date</span>
                    <span class="detail-value"><?= date('d M Y', strtotime($emp['hire_date'])) ?></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Status</span>
                    <span class="detail-value">
                        <span class="<?= $emp['status']==='Active'?'status-active':'status-disabled' ?>">
                            <?= $emp['status'] ?>
                        </span>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
