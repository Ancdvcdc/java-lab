<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab9.model.MonHoc" %>
<%
    MonHoc mh = (MonHoc) request.getAttribute("monHoc");
    boolean isEdit = mh != null && mh.getId() != null;
    String activeMenu = "monhoc";
    String pageTitle = isEdit ? "Cập nhật môn học" : "Thêm môn học";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/mon-hoc">Môn học</a> / <%= pageTitle %></div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header"><h1><%= pageTitle %></h1></div>

<div class="card form-narrow">
    <% if (request.getAttribute("loi") != null) { %>
        <div class="alert alert-error">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
            <%= request.getAttribute("loi") %>
        </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/mon-hoc">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= mh.getId() %>"/>
        <% } %>
        <label>Mã môn học</label>
        <input type="text" name="maMonHoc" required
               value="<%= mh != null && mh.getMaMonHoc() != null ? mh.getMaMonHoc() : "" %>"/>

        <label>Tên môn học</label>
        <input type="text" name="tenMonHoc" required
               value="<%= mh != null && mh.getTenMonHoc() != null ? mh.getTenMonHoc() : "" %>"/>

        <label>Số tín chỉ</label>
        <input type="number" name="soTinChi" min="1"
               value="<%= mh != null && mh.getSoTinChi() != null ? mh.getSoTinChi() : "" %>"/>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Thêm mới" %></button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/mon-hoc">Hủy</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
