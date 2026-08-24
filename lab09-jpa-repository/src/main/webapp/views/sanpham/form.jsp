<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab9.model.SanPham" %>
<%
    SanPham sp = (SanPham) request.getAttribute("sanPham");
    boolean isEdit = sp != null && sp.getId() != null;
    String activeMenu = "sanpham";
    String pageTitle = isEdit ? "Cập nhật sản phẩm" : "Thêm sản phẩm";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/san-pham">Sản phẩm</a> / <%= pageTitle %></div>
<span class="eyebrow">Kho dữ liệu khác · Bài 13</span>
<div class="page-header"><h1><%= pageTitle %></h1></div>

<div class="card form-narrow">
    <% if (request.getAttribute("loi") != null) { %>
        <div class="alert alert-error">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
            <%= request.getAttribute("loi") %>
        </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/san-pham">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= sp.getId() %>"/>
        <% } %>
        <label>Mã sản phẩm</label>
        <input type="text" name="maSanPham" required
               value="<%= sp != null && sp.getMaSanPham() != null ? sp.getMaSanPham() : "" %>"/>

        <label>Tên sản phẩm</label>
        <input type="text" name="tenSanPham" required
               value="<%= sp != null && sp.getTenSanPham() != null ? sp.getTenSanPham() : "" %>"/>

        <label>Giá (VNĐ)</label>
        <input type="number" step="1000" name="gia" min="0"
               value="<%= sp != null && sp.getGia() != null ? sp.getGia() : 0 %>"/>

        <label>Số lượng</label>
        <input type="number" name="soLuong" min="0"
               value="<%= sp != null && sp.getSoLuong() != null ? sp.getSoLuong() : 0 %>"/>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Thêm mới" %></button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/san-pham">Hủy</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
