<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; max-width:700px; margin:30px auto; }
        .card { background:#f7f9fb; border:1px solid #ddd; border-radius:8px; padding:20px; margin-bottom:16px; }
        table { border-collapse: collapse; width:100%; }
        th, td { border:1px solid #ccc; padding:8px; text-align:left; }
        th { background:#eef2f7; }
        .top { display:flex; justify-content:space-between; align-items:center; }
    </style>
</head>
<body>

<div class="top">
    <h2>Dashboard</h2>
    <div>
        <a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a> |
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </div>
</div>

<div class="card">
    <p><b>Người dùng:</b> ${sessionScope.username} (${sessionScope.role})</p>
    <p><b>Thời gian đăng nhập:</b> ${sessionScope.loginTime}</p>
    <p><b>Tổng số sinh viên:</b> ${totalStudents}</p>
</div>

<div class="card">
    <h3>Số sinh viên theo từng lớp</h3>
    <c:choose>
        <c:when test="${empty classCounts}">
            <p><i>Chưa có dữ liệu.</i></p>
        </c:when>
        <c:otherwise>
            <table>
                <tr><th>Lớp</th><th>Số lượng</th></tr>
                <c:forEach var="entry" items="${classCounts}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
