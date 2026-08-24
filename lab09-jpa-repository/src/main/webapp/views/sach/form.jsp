<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab9.model.Sach" %>
<%
    Sach sach = (Sach) request.getAttribute("sach");
    boolean isEdit = sach != null && sach.getId() != null;
    String activeMenu = "sach";
    String pageTitle = isEdit ? "Cập nhật sách" : "Thêm sách";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/sach">Sách</a> / <%= pageTitle %></div>
<span class="eyebrow">Kho dữ liệu khác · Bài 13</span>
<div class="page-header"><h1><%= pageTitle %></h1></div>

<div class="card form-narrow">
    <% if (request.getAttribute("loi") != null) { %>
        <div class="alert alert-error">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
            <%= request.getAttribute("loi") %>
        </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/sach">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= sach.getId() %>"/>
        <% } %>
        <label>Mã sách</label>
        <input type="text" name="maSach" required
               value="<%= sach != null && sach.getMaSach() != null ? sach.getMaSach() : "" %>"/>

        <label>Tên sách</label>
        <input type="text" name="tenSach" required
               value="<%= sach != null && sach.getTenSach() != null ? sach.getTenSach() : "" %>"/>

        <label>Tác giả</label>
        <input type="text" name="tacGia"
               value="<%= sach != null && sach.getTacGia() != null ? sach.getTacGia() : "" %>"/>

        <label>Số lượng</label>
        <input type="number" name="soLuong" min="0"
               value="<%= sach != null && sach.getSoLuong() != null ? sach.getSoLuong() : 0 %>"/>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Thêm mới" %></button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/sach">Hủy</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
