<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.SinhVien" %>
<%@ page import="vn.edu.eaut.lab9.model.LopHoc" %>
<%@ page import="vn.edu.eaut.lab9.model.MonHoc" %>
<%
    SinhVien sv = (SinhVien) request.getAttribute("sinhVien");
    boolean isEdit = sv != null && sv.getId() != null;
    String activeMenu = "sinhvien";
    String pageTitle = isEdit ? "Cập nhật sinh viên" : "Thêm sinh viên";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/sinh-vien">Sinh viên</a> / <%= pageTitle %></div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header">
    <h1><%= pageTitle %></h1>
</div>

<div class="card form-narrow">
    <% if (request.getAttribute("loi") != null) { %>
        <div class="alert alert-error">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round"><circle cx="12" cy="12" r="9"/><line x1="12" y1="8" x2="12" y2="13"/><line x1="12" y1="16.5" x2="12" y2="16.5"/></svg>
            <%= request.getAttribute("loi") %>
        </div>
    <% } %>

    <%
        List<LopHoc> dsLop = (List<LopHoc>) request.getAttribute("danhSachLop");
    %>
    <form method="post" action="${pageContext.request.contextPath}/sinh-vien">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= sv.getId() %>"/>
        <% } %>

        <label>Mã sinh viên</label>
        <input type="text" name="maSinhVien" required
               value="<%= sv != null && sv.getMaSinhVien() != null ? sv.getMaSinhVien() : "" %>"/>

        <label>Họ tên</label>
        <input type="text" name="hoTen" required
               value="<%= sv != null && sv.getHoTen() != null ? sv.getHoTen() : "" %>"/>

        <label>Email</label>
        <input type="email" name="email"
               value="<%= sv != null && sv.getEmail() != null ? sv.getEmail() : "" %>"/>

        <label>Ngày sinh</label>
        <input type="date" name="ngaySinh"
               value="<%= sv != null && sv.getNgaySinh() != null ? sv.getNgaySinh() : "" %>"/>

        <label>Lớp</label>
        <select name="lopHocId">
            <option value="">-- Chọn lớp --</option>
            <% if (dsLop != null) {
                for (LopHoc lop : dsLop) {
                    boolean selected = sv != null && sv.getLopHoc() != null
                            && sv.getLopHoc().getId().equals(lop.getId());
            %>
            <option value="<%= lop.getId() %>" <%= selected ? "selected" : "" %>><%= lop.getTenLop() %></option>
            <% } } %>
        </select>

        <% if (!isEdit) {
            List<MonHoc> dsMon = (List<MonHoc>) request.getAttribute("danhSachMon");
        %>
        <div class="checkbox-row">
            <input type="checkbox" name="khoiTaoDiem" id="khoiTaoDiem"/>
            <label for="khoiTaoDiem">
                Khởi tạo điểm mặc định (0) cho tất cả <%= dsMon == null ? 0 : dsMon.size() %> môn học hiện có
                <span class="hint">Bài 11 · Transaction</span>
            </label>
        </div>
        <% } %>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary"><%= isEdit ? "Cập nhật" : "Thêm mới" %></button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/sinh-vien">Hủy</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
