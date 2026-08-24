<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab9.model.LopHoc" %>
<%
    LopHoc lop = (LopHoc) request.getAttribute("lopHoc");
    boolean isEdit = lop != null && lop.getId() != null;
    String activeMenu = "lophoc";
    String pageTitle = isEdit ? "Cập nhật lớp học" : "Thêm lớp học";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/lop-hoc">Lớp học</a> / <%= pageTitle %></div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header"><h1><%= pageTitle %></h1></div>

<div class="card form-narrow">
    <% if (request.getAttribute("loi") != null) { %>
        <div class="alert alert-error">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
            <%= request.getAttribute("loi") %>
        </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/lop-hoc">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= lop.getId() %>"/>
        <% } %>
        <label>Mã lớp</label>
        <input type="text" name="maLop" required
               value="<%= lop != null && lop.getMaLop() != null ? lop.getMaLop() : "" %>"/>

        <label>Tên lớp</label>
        <input type="text" name="tenLop" required
               value="<%= lop != null && lop.getTenLop() != null ? lop.getTenLop() : "" %>"/>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Thêm mới" %></button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/lop-hoc">Hủy</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
