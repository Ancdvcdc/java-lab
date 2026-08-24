<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.eaut.lab9.model.Diem" %>
<%@ page import="vn.edu.eaut.lab9.model.SinhVien" %>
<%
    SinhVien sv = (SinhVien) request.getAttribute("sinhVien");
    String activeMenu = "diem";
    String pageTitle = "Bảng điểm";
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<div class="breadcrumb"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a> /
    <a href="${pageContext.request.contextPath}/diem">Điểm</a> / Bảng điểm</div>
<span class="eyebrow">Đào tạo</span>
<div class="page-header">
    <h1>Bảng điểm: <%= sv != null ? sv.getHoTen() : "" %></h1>
</div>

<%
    List<Diem> ds = (List<Diem>) request.getAttribute("dsDiem");
    Double dtb = (Double) request.getAttribute("diemTrungBinh");
    String xepLoai = (String) request.getAttribute("xepLoai");
%>

<div class="card">
    <div class="table-wrap">
        <table class="data-table">
            <thead><tr><th>Môn học</th><th>Điểm</th></tr></thead>
            <tbody>
            <% if (ds == null || ds.isEmpty()) { %>
            <tr class="empty-row"><td colspan="2">Chưa có điểm</td></tr>
            <% } else { for (Diem d : ds) { %>
            <tr>
                <td><%= d.getMonHoc().getTenMonHoc() %></td>
                <td><%= d.getDiemSo() %></td>
            </tr>
            <% } } %>
            </tbody>
        </table>
    </div>

    <div class="summary-box">
        <div class="item">
            <div class="label">Mã sinh viên</div>
            <div class="value"><span class="code" style="background:transparent;color:#E7DAB2;padding:0;font-size:20px;"><%= sv != null ? sv.getMaSinhVien() : "" %></span></div>
        </div>
        <div class="item">
            <div class="label">Điểm trung bình</div>
            <div class="value gold"><%= dtb == null ? "—" : String.format("%.2f", dtb) %></div>
        </div>
        <div class="item">
            <div class="label">Xếp loại</div>
            <div class="value"><%= xepLoai %></div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
