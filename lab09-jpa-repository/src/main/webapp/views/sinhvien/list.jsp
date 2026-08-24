<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.SinhVien" %>
<% String activeMenu = "sinhvien"; String pageTitle = "Sinh viên"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> / Sinh viên</div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header">
    <h1>Danh sách sinh viên</h1>
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
        <form method="get" action="${pageContext.request.contextPath}/sinh-vien" class="search-box">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="7"/><line x1="21" y1="21" x2="16.6" y2="16.6"/></svg>
            <input type="text" name="keyword" placeholder="Tìm theo tên hoặc mã SV..."
                   value="<%= request.getAttribute("keyword") == null ? "" : request.getAttribute("keyword") %>"/>
        </form>
        <a class="btn btn-gold" href="${pageContext.request.contextPath}/sinh-vien?action=form">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.3" stroke-linecap="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Thêm sinh viên
        </a>
    </div>

    <div class="table-wrap">
        <table class="data-table">
            <thead>
            <tr>
                <th>Mã SV</th>
                <th>Họ tên</th>
                <th>Email</th>
                <th>Ngày sinh</th>
                <th>Lớp</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<SinhVien> ds = (List<SinhVien>) request.getAttribute("dsSinhVien");
                if (ds == null || ds.isEmpty()) {
            %>
            <tr class="empty-row"><td colspan="6">Chưa có dữ liệu sinh viên</td></tr>
            <%
                } else {
                    for (SinhVien sv : ds) {
            %>
            <tr>
                <td><span class="code"><%= sv.getMaSinhVien() %></span></td>
                <td><%= sv.getHoTen() %></td>
                <td><%= sv.getEmail() == null ? "" : sv.getEmail() %></td>
                <td><%= sv.getNgaySinh() == null ? "" : sv.getNgaySinh() %></td>
                <td><%= sv.getLopHoc() == null
                        ? "<span class=\"badge badge-neutral\">Chưa có lớp</span>"
                        : "<span class=\"badge badge-info\">" + sv.getLopHoc().getTenLop() + "</span>" %></td>
                <td>
                    <div class="actions">
                        <a class="btn btn-ghost btn-sm" href="${pageContext.request.contextPath}/sinh-vien?action=form&id=<%= sv.getId() %>">Sửa</a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=<%= sv.getId() %>"
                           onclick="return confirm('Xóa sinh viên này?');">Xóa</a>
                    </div>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <%
            int trangHienTai = (int) request.getAttribute("page");
            int tongSoTrang = (int) request.getAttribute("tongSoTrang");
            String kw = (String) request.getAttribute("keyword");
            String kwParam = (kw == null || kw.isBlank()) ? "" : "&keyword=" + kw;
            for (int i = 1; i <= tongSoTrang; i++) {
        %>
        <a class="<%= i == trangHienTai ? "active" : "" %>"
           href="${pageContext.request.contextPath}/sinh-vien?page=<%= i %><%= kwParam %>"><%= i %></a>
        <% } %>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
