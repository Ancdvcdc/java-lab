<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa thông tin sinh viên</title>
    <style>
        body { font-family: Arial, sans-serif; max-width:500px; margin:40px auto; }
        input { width:100%; padding:6px; margin-top:4px; box-sizing:border-box; }
        input[readonly] { background:#eee; }
        button { margin-top:16px; padding:8px 20px; cursor:pointer; }
    </style>
</head>
<body>
<h2>Sửa thông tin sinh viên</h2>
<p style="color:red">${error}</p>
<form action="${pageContext.request.contextPath}/student-edit" method="post">
    <label>Mã sinh viên:</label>
    <input type="text" name="id" value="${student.id}" readonly><br><br>
    <label>Họ tên:</label>
    <input type="text" name="name" value="${student.name}"><br><br>
    <label>Lớp:</label>
    <input type="text" name="className" value="${student.className}"><br><br>
    <label>Email:</label>
    <input type="email" name="email" value="${student.email}"><br><br>
    <button type="submit">Lưu thay đổi</button>
</form>
<p><a href="${pageContext.request.contextPath}/students">Quay lại danh sách</a></p>
</body>
</html>
