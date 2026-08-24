<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.MonHoc" %>
<% String activeMenu = "monhoc"; String pageTitle = "Môn học"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> / Môn học</div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header">
    <h1>Danh sách môn học</h1>
</div>

<% if (request.getParameter("thanhCong") != null) { %>
    <div class="alert alert-success">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.4" stroke-linecap="round"><polyline points="4 13 9 18 20 6"/></svg>
        <%= request.getParameter("thanhCong").replace('+', ' ') %>
    </div>
<% } %>
<% if (request.getParameter("loi") != null) { %>
    <div class="alert alert-error">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
        <%= request.getParameter("loi").replace('+', ' ') %>
    </div>
<% } %>

<div class="card">
    <div class="toolbar">
        <div></div>
        <a class="btn btn-gold" href="${pageContext.request.contextPath}/mon-hoc?action=form">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.3" stroke-linecap="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Thêm môn học
        </a>
    </div>

    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Mã môn</th><th>Tên môn</th><th>Số tín chỉ</th><th>Hành động</th></tr></thead>
            <tbody>
            <%
                List<MonHoc> ds = (List<MonHoc>) request.getAttribute("dsMonHoc");
                if (ds == null || ds.isEmpty()) {
            %>
            <tr class="empty-row"><td colspan="4">Chưa có dữ liệu môn học</td></tr>
            <% } else {
                for (MonHoc mh : ds) {
            %>
            <tr>
                <td><span class="code"><%= mh.getMaMonHoc() %></span></td>
                <td><%= mh.getTenMonHoc() %></td>
                <td><span class="badge badge-info"><%= mh.getSoTinChi() == null ? "-" : mh.getSoTinChi() %> TC</span></td>
                <td>
                    <div class="actions">
                        <a class="btn btn-ghost btn-sm" href="${pageContext.request.contextPath}/mon-hoc?action=form&id=<%= mh.getId() %>">Sửa</a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/mon-hoc?action=delete&id=<%= mh.getId() %>"
                           onclick="return confirm('Xóa môn học này?');">Xóa</a>
                    </div>
                </td>
            </tr>
            <% } } %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
