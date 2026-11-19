<?php
include 'db.php';

$search  = isset($_GET['search'])  ? mysqli_real_escape_string($conn, trim($_GET['search'])) : '';
$perPage = isset($_GET['show'])    ? (int)$_GET['show'] : 10;
$page    = isset($_GET['page'])    ? (int)$_GET['page'] : 1;
$offset  = ($page - 1) * $perPage;

$where = $search ? "WHERE name LIKE '%$search%' OR role LIKE '%$search%' OR department LIKE '%$search%'" : '';

$totalResult = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as cnt FROM employees $where"));
$total       = $totalResult['cnt'];
$totalPages  = ceil($total / $perPage);

$employees = mysqli_query($conn, "SELECT * FROM employees $where ORDER BY id LIMIT $perPage OFFSET $offset");

$msg = '';
if (isset($_GET['msg'])) {
    $msgs = [
        'added'   => ['success', 'Employee added successfully!'],
        'updated' => ['success', 'Employee updated successfully!'],
        'deleted' => ['success', 'Employee deleted successfully!'],
        'toggled' => ['success', 'Employee status updated!'],
    ];
    if (isset($msgs[$_GET['msg']])) {
        [$type, $text] = $msgs[$_GET['msg']];
        $msg = "<div class='alert alert-$type'>$text</div>";
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HRM - Employee Management</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="top-header">
    <h1>HRM</h1>
    <div class="bookmark-icon"><i class="fas fa-bookmark"></i></div>
</div>

<div class="container">
    <?= $msg ?>
    <div class="card">
        <div class="card-header">
            <h2>Employee</h2>
            <a href="add.php" class="badge-add">+ Add new</a>
            <a href="export.php" class="btn btn-success btn-sm"><i class="fas fa-file-excel"></i> Excel</a>
        </div>
        <div class="card-body">
            <div class="table-controls">
                <div class="show-entries">
                    Show
                    <form method="GET" style="display:inline;">
                        <input type="hidden" name="search" value="<?= htmlspecialchars($search) ?>">
                        <select name="show" onchange="this.form.submit()">
                            <?php foreach ([5,10,25,50] as $n): ?>
                                <option value="<?=$n?>" <?= $perPage==$n?'selected':'' ?>><?=$n?></option>
                            <?php endforeach; ?>
                        </select>
                    </form>
                    entries
                </div>
                <div class="search-box">
                    Search:
                    <form method="GET">
                        <input type="hidden" name="show" value="<?=$perPage?>">
                        <input type="text" name="search" value="<?= htmlspecialchars($search) ?>"
                               placeholder="Name, role, department..." oninput="this.form.submit()">
                    </form>
                </div>
            </div>

            <table class="table">
                <thead>
                    <tr>
                        <th># <span class="sort-icon">⇅</span></th>
                        <th>Name <span class="sort-icon">⇅</span></th>
                        <th>Role <span class="sort-icon">⇅</span></th>
                        <th>Status <span class="sort-icon">⇅</span></th>
                        <th>Actions <span class="sort-icon">⇅</span></th>
                    </tr>
                </thead>
                <tbody>
                <?php
                $i = $offset + 1;
                if (mysqli_num_rows($employees) === 0): ?>
                    <tr><td colspan="5" style="text-align:center;padding:30px;color:#888;">No employees found.</td></tr>
                <?php else:
                    while ($emp = mysqli_fetch_assoc($employees)):
                        $statusClass = $emp['status'] === 'Active' ? 'status-active' : 'status-disabled';
                        $toggleLabel = $emp['status'] === 'Active' ? 'Disable' : 'Enable';
                        $toggleClass = $emp['status'] === 'Active' ? 'btn-warning' : 'btn-success';
                ?>
                    <tr>
                        <td><?= $i++ ?></td>
                        <td><?= htmlspecialchars($emp['name']) ?></td>
                        <td><?= htmlspecialchars($emp['role']) ?></td>
                        <td><span class="<?= $statusClass ?>"><?= $emp['status'] ?></span></td>
                        <td>
                            <div class="actions">
                                <a href="view.php?id=<?= $emp['id'] ?>" class="btn btn-info btn-sm">
                                    <i class="fas fa-eye"></i> View
                                </a>
                                <a href="toggle.php?id=<?= $emp['id'] ?>" class="btn <?= $toggleClass ?> btn-sm"
                                   onclick="return confirm('<?= $toggleLabel ?> this employee?')">
                                    <i class="fas fa-toggle-on"></i> <?= $toggleLabel ?>
                                </a>
                                <a href="delete.php?id=<?= $emp['id'] ?>" class="btn btn-danger btn-sm"
                                   onclick="return confirm('Are you sure you want to delete this employee?')">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                <?php endwhile; endif; ?>
                </tbody>
            </table>

            <div class="table-footer">
                <span>Showing <?= $offset+1 ?> to <?= min($offset+$perPage, $total) ?> of <?= $total ?> entries</span>
                <div style="display:flex;gap:5px;">
                    <?php if ($page > 1): ?>
                        <a href="?page=<?=$page-1?>&show=<?=$perPage?>&search=<?=urlencode($search)?>" class="btn btn-secondary btn-sm">Previous</a>
                    <?php endif; ?>
                    <?php for ($p = 1; $p <= $totalPages; $p++): ?>
                        <a href="?page=<?=$p?>&show=<?=$perPage?>&search=<?=urlencode($search)?>"
                           class="btn btn-sm <?= $p==$page ? 'btn-success' : 'btn-secondary' ?>"><?=$p?></a>
                    <?php endfor; ?>
                    <?php if ($page < $totalPages): ?>
                        <a href="?page=<?=$page+1?>&show=<?=$perPage?>&search=<?=urlencode($search)?>" class="btn btn-secondary btn-sm">Next</a>
                    <?php endif; ?>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
