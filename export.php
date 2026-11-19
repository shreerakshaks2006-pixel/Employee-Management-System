<?php
include 'db.php';
header('Content-Type: application/vnd.ms-excel');
header('Content-Disposition: attachment; filename="employees_' . date('Y-m-d') . '.xls"');
header('Cache-Control: max-age=0');

$result = mysqli_query($conn, "SELECT id, name, email, phone, department, role, salary, hire_date, status FROM employees ORDER BY id");
?>
<table border="1">
    <thead>
        <tr style="background:#2d6a4f;color:white;font-weight:bold;">
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Department</th>
            <th>Role</th>
            <th>Salary</th>
            <th>Hire Date</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
    <?php while ($row = mysqli_fetch_assoc($result)): ?>
        <tr>
            <td><?= $row['id'] ?></td>
            <td><?= htmlspecialchars($row['name']) ?></td>
            <td><?= htmlspecialchars($row['email']) ?></td>
            <td><?= htmlspecialchars($row['phone']) ?></td>
            <td><?= htmlspecialchars($row['department']) ?></td>
            <td><?= htmlspecialchars($row['role']) ?></td>
            <td><?= number_format($row['salary'], 2) ?></td>
            <td><?= $row['hire_date'] ?></td>
            <td><?= $row['status'] ?></td>
        </tr>
    <?php endwhile; ?>
    </tbody>
</table>
