<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<form action="/login" method="post">
    <label for="unitid">ID:
        <input id="unitid" type="number" name="id">
    </label><br>
    <label for="unitname">Unit name:
        <input id="unitname" type="text" name="name">
    </label><br>
    <label for="unitadmin">Managed by admin:
        <input id="unitadmin" type="checkbox" name="managedByAdmin">
    </label><br>
    <button type="submit">Send Unit</button>
</form>
</body>
</html>