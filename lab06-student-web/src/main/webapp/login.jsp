<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <style>
        body { font-family: Arial, sans-serif; background:#f4f6f8; }
        .box { max-width:360px; margin:80px auto; background:#fff; padding:30px;
               border-radius:8px; box-shadow:0 2px 8px rgba(0,0,0,0.15); }
        input[type=text], input[type=password] { width:100%; padding:8px; margin-top:4px; box-sizing:border-box; }
        button { margin-top:16px; padding:8px 20px; cursor:pointer; }
        .hint { font-size:12px; color:#666; margin-top:16px; }
    </style>
</head>
<body>
<div class="box">
    <h2>Đăng nhập hệ thống</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Tên đăng nhập:</label>
        <input type="text" name="username"><br>
        <label>Mật khẩu:</label>
        <input type="password" name="password"><br>
        <button type="submit">Đăng nhập</button>
    </form>
    <p style="color:red">${error}</p>
   
</div>
</body>
</html>
