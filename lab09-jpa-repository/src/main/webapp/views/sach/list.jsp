<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.Sach" %>
<% String activeMenu = "sach"; String pageTitle = "Sách"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> / Sách</div>
<span class="eyebrow">Kho dữ liệu khác · Bài 13</span>
<div class="page-header">
    <h1>Danh sách sách</h1>
</div>

<% if (request.getParameter("thanhCong") != null) { %>
    <div class="alert alert-success">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.4" stroke-linecap="round"><polyline points="4 13 9 18 20 6"/></svg>
        <%= request.getParameter("thanhCong").replace('+', ' ') %>
    </div>
<% } %>

<div class="card">
    <div class="toolbar">
        <form method="get" action="${pageContext.request.contextPath}/sach" class="search-box">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="7"/><line x1="21" y1="21" x2="16.6" y2="16.6"/></svg>
            <input type="text" name="keyword" placeholder="Tìm theo tên sách hoặc tác giả..."
                   value="<%= request.getAttribute("keyword") %>"/>
        </form>
        <a class="btn btn-gold" href="${pageContext.request.contextPath}/sach?action=form">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.3" stroke-linecap="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Thêm sách
        </a>
    </div>

    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Mã sách</th><th>Tên sách</th><th>Tác giả</th><th>Số lượng</th><th>Hành động</th></tr></thead>
            <tbody>
            <%
                List<Sach> ds = (List<Sach>) request.getAttribute("dsSach");
                if (ds == null || ds.isEmpty()) {
            %>
            <tr class="empty-row"><td colspan="5">Chưa có dữ liệu sách</td></tr>
            <% } else {
                for (Sach s : ds) {
            %>
            <tr>
                <td><span class="code"><%= s.getMaSach() %></span></td>
                <td><%= s.getTenSach() %></td>
                <td><%= s.getTacGia() == null ? "" : s.getTacGia() %></td>
                <td><span class="badge <%= (s.getSoLuong() == null || s.getSoLuong() == 0) ? "badge-danger" : "badge-success" %>"><%= s.getSoLuong() == null ? 0 : s.getSoLuong() %></span></td>
                <td>
                    <div class="actions">
                        <a class="btn btn-ghost btn-sm" href="${pageContext.request.contextPath}/sach?action=form&id=<%= s.getId() %>">Sửa</a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/sach?action=delete&id=<%= s.getId() %>"
                           onclick="return confirm('Xóa sách này?');">Xóa</a>
                    </div>
                </td>
            </tr>
            <% } } %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
