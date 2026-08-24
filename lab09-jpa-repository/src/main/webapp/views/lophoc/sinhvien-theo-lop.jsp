<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.SinhVien" %>
<%@ page import="vn.edu.eaut.lab9.model.LopHoc" %>
<%
    LopHoc lop = (LopHoc) request.getAttribute("lopHoc");
    String activeMenu = "lophoc";
    String pageTitle = "Sinh viên lớp " + (lop != null ? lop.getTenLop() : "");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/lop-hoc">Lớp học</a> / Sinh viên trong lớp</div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header">
    <h1>Sinh viên lớp: <%= lop != null ? lop.getTenLop() : "" %></h1>
</div>

<div class="card">
    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Mã SV</th><th>Họ tên</th><th>Email</th></tr></thead>
            <tbody>
            <%
                List<SinhVien> ds = (List<SinhVien>) request.getAttribute("dsSinhVien");
                if (ds == null || ds.isEmpty()) {
            %>
            <tr class="empty-row"><td colspan="3">Lớp chưa có sinh viên</td></tr>
            <% } else {
                for (SinhVien sv : ds) {
            %>
            <tr>
                <td><span class="code"><%= sv.getMaSinhVien() %></span></td>
                <td><%= sv.getHoTen() %></td>
                <td><%= sv.getEmail() == null ? "" : sv.getEmail() %></td>
            </tr>
            <% } } %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
