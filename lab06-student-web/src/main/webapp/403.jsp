<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>403 - Không có quyền truy cập</title>
    <style>
        body { font-family: Arial, sans-serif; max-width:500px; margin:80px auto; text-align:center; }
        h1 { color:#c0392b; }
    </style>
</head>
<body>
<h1>403 - Không có quyền truy cập</h1>
<p>Tài khoản của bạn (<b>${sessionScope.role}</b>) không có quyền thực hiện chức năng này.</p>
<p><a href="${pageContext.request.contextPath}/students">Quay lại danh sách sinh viên</a></p>
</body>
</html>
