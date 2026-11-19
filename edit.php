<?php
include 'db.php';
$id    = isset($_GET['id']) ? (int)$_GET['id'] : 0;
$emp   = mysqli_fetch_assoc(mysqli_query($conn, "SELECT * FROM employees WHERE id=$id"));
$error = '';
if (!$emp) { header("Location: index.php"); exit; }

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $name       = mysqli_real_escape_string($conn, trim($_POST['name']));
    $email      = mysqli_real_escape_string($conn, trim($_POST['email']));
    $phone      = mysqli_real_escape_string($conn, trim($_POST['phone']));
    $department = mysqli_real_escape_string($conn, trim($_POST['department']));
    $role       = mysqli_real_escape_string($conn, trim($_POST['role']));
    $salary     = (float)$_POST['salary'];
    $hire_date  = mysqli_real_escape_string($conn, $_POST['hire_date']);

    if (!$name || !$email || !$phone || !$department || !$role || !$hire_date) {
        $error = "All fields are required.";
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $error = "Invalid email address.";
    } else {
        $result = mysqli_query($conn,
            "UPDATE employees SET name='$name', email='$email', phone='$phone',
             department='$department', role='$role', salary=$salary, hire_date='$hire_date'
             WHERE id=$id");
        if ($result) {
            header("Location: index.php?msg=updated");
            exit;
        } else {
            $error = "Update failed. Email may already be in use.";
        }
    }
    // Reload updated data into form
    $emp = array_merge($emp, $_POST);
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HRM - Edit Employee</title>
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
            <h2>Edit Employee — <?= htmlspecialchars($emp['name']) ?></h2>
            <a href="index.php" class="btn btn-secondary btn-sm"><i class="fas fa-arrow-left"></i> Back</a>
        </div>
        <div class="card-body">
            <?php if ($error): ?>
                <div class="alert alert-danger"><?= $error ?></div>
            <?php endif; ?>
            <form method="POST">
                <div class="form-row">
                    <div class="form-group">
                        <label>Full Name *</label>
                        <input type="text" name="name" class="form-control"
                               value="<?= htmlspecialchars($emp['name']) ?>" required>
                    </div>
                    <div class="form-group">
                        <label>Email Address *</label>
                        <input type="email" name="email" class="form-control"
                               value="<?= htmlspecialchars($emp['email']) ?>" required>
                    </div>
                    <div class="form-group">
                        <label>Phone *</label>
                        <input type="text" name="phone" class="form-control"
                               value="<?= htmlspecialchars($emp['phone']) ?>" required>
                    </div>
                    <div class="form-group">
                        <label>Department *</label>
                        <select name="department" class="form-control" required>
                            <?php foreach (['Management','Sales','Finance','Engineering','HR','Operations'] as $d): ?>
                                <option value="<?=$d?>" <?= $emp['department']===$d?'selected':'' ?>><?=$d?></option>
                            <?php endforeach; ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Role / Designation *</label>
                        <select name="role" class="form-control" required>
                            <?php foreach (['Business Owner','Sales Manager','Sales Person','Cashier','HR Manager','Software Engineer','Financial Analyst'] as $r): ?>
                                <option value="<?=$r?>" <?= $emp['role']===$r?'selected':'' ?>><?=$r?></option>
                            <?php endforeach; ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Salary</label>
                        <input type="number" name="salary" class="form-control" step="0.01" min="0"
                               value="<?= htmlspecialchars($emp['salary']) ?>">
                    </div>
                    <div class="form-group">
                        <label>Hire Date *</label>
                        <input type="date" name="hire_date" class="form-control"
                               value="<?= htmlspecialchars($emp['hire_date']) ?>" required>
                    </div>
                </div>
                <div style="margin-top:10px;">
                    <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Update Employee</button>
                    <a href="index.php" class="btn btn-secondary"><i class="fas fa-times"></i> Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
