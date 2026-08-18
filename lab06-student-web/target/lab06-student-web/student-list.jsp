<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sinh viên</title>
    <style>
        body { font-family: Arial, sans-serif; max-width:900px; margin:30px auto; }
        table { border-collapse: collapse; width:100%; margin-top:16px; }
        th, td { border:1px solid #ccc; padding:8px; text-align:left; }
        th { background:#f0f0f0; }
        .toolbar { display:flex; justify-content:space-between; align-items:center; }
        a.action { margin-right:8px; }
        a.danger { color:#c0392b; }
        .empty { color:#888; font-style:italic; }
    </style>
</head>
<body>

<div class="toolbar">
    <h2>Danh sách sinh viên</h2>
    <div>
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a> |
        <a href="${pageContext.request.contextPath}/welcome.jsp">Trang chủ</a> |
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </div>
</div>

<!-- Bài 6: Tìm kiếm sinh viên theo họ tên -->
<form action="${pageContext.request.contextPath}/students" method="get">
    <label>Tìm theo họ tên:</label>
    <input type="text" name="keyword" value="${keyword}">
    <button type="submit">Tìm kiếm</button>
    <c:if test="${not empty keyword}">
        <a href="${pageContext.request.contextPath}/students">Xóa bộ lọc</a>
    </c:if>
</form>

<!-- Bài 9: chỉ ADMIN mới thấy nút Thêm sinh viên -->
<c:if test="${sessionScope.role == 'ADMIN'}">
    <p><a href="${pageContext.request.contextPath}/student-form.jsp">+ Thêm sinh viên</a></p>
</c:if>

<p style="color:red">${error}</p>

<c:choose>
    <c:when test="${empty students}">
        <p class="empty">
            <c:choose>
                <c:when test="${not empty keyword}">Không tìm thấy sinh viên nào có tên chứa "${keyword}".</c:when>
                <c:otherwise>Chưa có sinh viên nào trong danh sách.</c:otherwise>
            </c:choose>
        </p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Mã SV</th>
                <th>Họ tên</th>
                <th>Lớp</th>
                <th>Email</th>
                <c:if test="${sessionScope.role == 'ADMIN'}"><th>Thao tác</th></c:if>
            </tr>
            <c:forEach var="sv" items="${students}">
                <tr>
                    <td>${sv.id}</td>
                    <td>${sv.name}</td>
                    <td>${sv.className}</td>
                    <td>${sv.email}</td>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <td>
                            <a class="action" href="${pageContext.request.contextPath}/student-edit?id=${sv.id}">Sửa</a>
                            <a class="action danger"
                               href="${pageContext.request.contextPath}/student-delete?id=${sv.id}"
                               onclick="return confirm('Xóa sinh viên ${sv.id} - ${sv.name}?');">Xóa</a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
