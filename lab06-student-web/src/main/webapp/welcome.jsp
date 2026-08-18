<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị</title>
    <style>
        body { font-family: Arial, sans-serif; max-width:600px; margin:60px auto; }
        li { margin:8px 0; }
    </style>
</head>
<body>
<h2>Xin chào, ${sessionScope.username} (${sessionScope.role})</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/dashboard">Xem Dashboard</a></li>
    <li><a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a></li>
    <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
</ul>
</body>
</html>
