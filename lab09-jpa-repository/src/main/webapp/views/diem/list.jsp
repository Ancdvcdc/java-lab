<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.Diem" %>
<% String activeMenu = "diem"; String pageTitle = "Điểm"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> / Điểm</div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header">
    <h1>Danh sách điểm</h1>
</div>

<% if (request.getParameter("thanhCong") != null) { %>
    <div class="alert alert-success">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.4" stroke-linecap="round"><polyline points="4 13 9 18 20 6"/></svg>
        <%= request.getParameter("thanhCong").replace('+', ' ') %>
    </div>
<% } %>

<div class="card">
    <div class="toolbar">
        <div></div>
        <a class="btn btn-gold" href="${pageContext.request.contextPath}/diem?action=form">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.3" stroke-linecap="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Nhập điểm
        </a>
    </div>

    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Sinh viên</th><th>Môn học</th><th>Điểm</th><th>Hành động</th></tr></thead>
            <tbody>
            <%
                List<Diem> ds = (List<Diem>) request.getAttribute("dsDiem");
                if (ds == null || ds.isEmpty()) {
            %>
            <tr class="empty-row"><td colspan="4">Chưa có dữ liệu điểm</td></tr>
            <% } else {
                for (Diem d : ds) {
                    double diemSo = d.getDiemSo() == null ? 0 : d.getDiemSo();
                    String badgeClass = diemSo >= 8 ? "badge-success" : diemSo >= 5 ? "badge-info" : "badge-danger";
            %>
            <tr>
                <td><%= d.getSinhVien().getHoTen() %> <span class="code"><%= d.getSinhVien().getMaSinhVien() %></span></td>
                <td><%= d.getMonHoc().getTenMonHoc() %></td>
                <td><span class="badge <%= badgeClass %>"><%= d.getDiemSo() %></span></td>
                <td>
                    <div class="actions">
                        <a class="btn btn-ghost btn-sm" href="${pageContext.request.contextPath}/diem?action=bangDiem&svId=<%= d.getSinhVien().getId() %>">Bảng điểm</a>
                        <a class="btn btn-ghost btn-sm" href="${pageContext.request.contextPath}/diem?action=form&id=<%= d.getId() %>">Sửa</a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/diem?action=delete&id=<%= d.getId() %>"
                           onclick="return confirm('Xóa điểm này?');">Xóa</a>
                    </div>
                </td>
            </tr>
            <% } } %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
