<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.Diem" %>
<%@ page import="vn.edu.eaut.lab9.model.SinhVien" %>
<%@ page import="vn.edu.eaut.lab9.model.MonHoc" %>
<%
    Diem diem = (Diem) request.getAttribute("diem");
    boolean isEdit = diem != null && diem.getId() != null;
    String activeMenu = "diem";
    String pageTitle = isEdit ? "Cập nhật điểm" : "Nhập điểm";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/diem">Điểm</a> / <%= pageTitle %></div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header"><h1><%= pageTitle %></h1></div>

<div class="card form-narrow">
    <% if (request.getAttribute("loi") != null) { %>
        <div class="alert alert-error">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
            <%= request.getAttribute("loi") %>
        </div>
    <% } %>

    <%
        List<SinhVien> dsSV = (List<SinhVien>) request.getAttribute("danhSachSV");
        List<MonHoc> dsMon = (List<MonHoc>) request.getAttribute("danhSachMon");
    %>
    <form method="post" action="${pageContext.request.contextPath}/diem">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= diem.getId() %>"/>
        <% } %>

        <label>Sinh viên</label>
        <select name="sinhVienId" required>
            <option value="">-- Chọn sinh viên --</option>
            <% if (dsSV != null) { for (SinhVien sv : dsSV) {
                boolean selected = diem != null && diem.getSinhVien() != null
                        && diem.getSinhVien().getId().equals(sv.getId());
            %>
            <option value="<%= sv.getId() %>" <%= selected ? "selected" : "" %>>
                <%= sv.getHoTen() %> (<%= sv.getMaSinhVien() %>)
            </option>
            <% } } %>
        </select>

        <label>Môn học</label>
        <select name="monHocId" required>
            <option value="">-- Chọn môn học --</option>
            <% if (dsMon != null) { for (MonHoc mh : dsMon) {
                boolean selected = diem != null && diem.getMonHoc() != null
                        && diem.getMonHoc().getId().equals(mh.getId());
            %>
            <option value="<%= mh.getId() %>" <%= selected ? "selected" : "" %>><%= mh.getTenMonHoc() %></option>
            <% } } %>
        </select>

        <label>Điểm số (0 - 10)</label>
        <input type="number" step="0.1" min="0" max="10" name="diemSo" required
               value="<%= diem != null && diem.getDiemSo() != null ? diem.getDiemSo() : "" %>"/>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Lưu điểm" %></button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/diem">Hủy</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
