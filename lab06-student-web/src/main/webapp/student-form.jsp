<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm sinh viên</title>
    <style>
        body { font-family: Arial, sans-serif; max-width:500px; margin:40px auto; }
        input { width:100%; padding:6px; margin-top:4px; box-sizing:border-box; }
        button { margin-top:16px; padding:8px 20px; cursor:pointer; }
    </style>
</head>
<body>
<h2>Thêm sinh viên</h2>
<p style="color:red">${error}</p>
<form action="${pageContext.request.contextPath}/students" method="post">
    <label>Mã sinh viên:</label>
    <input type="text" name="id"><br><br>
    <label>Họ tên:</label>
    <input type="text" name="name"><br><br>
    <label>Lớp:</label>
    <input type="text" name="className"><br><br>
    <label>Email:</label>
    <input type="email" name="email"><br><br>
    <button type="submit">Lưu sinh viên</button>
</form>
<p><a href="${pageContext.request.contextPath}/students">Quay lại danh sách</a></p>
</body>
</html>
