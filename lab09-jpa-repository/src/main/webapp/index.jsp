<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String activeMenu = "home"; String pageTitle = "Trang chủ"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<span class="eyebrow">Hệ thống quản lý học vụ</span>
<div class="page-header">
    <div>
        <h1>Chào mừng trở lại</h1>
        <p class="desc">Dữ liệu Sinh viên, Lớp học, Điểm và các module khác được lưu trữ thật trong MySQL thông qua JPA/Hibernate.</p>
    </div>
</div>

<div class="stat-grid">
    <a class="stat-card" href="${pageContext.request.contextPath}/sinh-vien">
        <div class="icon">
            <svg width="19" height="19" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="8" r="3.6"/><path d="M4.5 20c0-4.1 3.4-6.6 7.5-6.6s7.5 2.5 7.5 6.6"/></svg>
        </div>
        <div class="title">Sinh viên</div>
        <div class="desc">Thêm, sửa, xóa, tìm kiếm và phân trang danh sách sinh viên theo lớp.</div>
        <div class="go">Mở module &rarr;</div>
    </a>

    <a class="stat-card" href="${pageContext.request.contextPath}/lop-hoc">
        <div class="icon">
            <svg width="19" height="19" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><rect x="3.3" y="3.3" width="7.2" height="7.2" rx="1.4"/><rect x="13.5" y="3.3" width="7.2" height="7.2" rx="1.4"/><rect x="3.3" y="13.5" width="7.2" height="7.2" rx="1.4"/><rect x="13.5" y="13.5" width="7.2" height="7.2" rx="1.4"/></svg>
        </div>
        <div class="title">Lớp học</div>
        <div class="desc">Quản lý danh sách lớp và xem sinh viên trực thuộc từng lớp.</div>
        <div class="go">Mở module &rarr;</div>
    </a>

    <a class="stat-card" href="${pageContext.request.contextPath}/mon-hoc">
        <div class="icon">
            <svg width="19" height="19" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><path d="M12 6.2c-2-1.4-4.8-1.9-7.7-1V18c2.9-0.9 5.7-0.4 7.7 1 2-1.4 4.8-1.9 7.7-1V5.2c-2.9-0.9-5.7-0.4-7.7 1z"/><line x1="12" y1="6.2" x2="12" y2="19"/></svg>
        </div>
        <div class="title">Môn học</div>
        <div class="desc">Danh mục môn học và số tín chỉ tương ứng.</div>
        <div class="go">Mở module &rarr;</div>
    </a>

    <a class="stat-card" href="${pageContext.request.contextPath}/diem">
        <div class="icon">
            <svg width="19" height="19" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><rect x="4" y="12.5" width="3.4" height="7.5" rx="0.6"/><rect x="10.3" y="6.5" width="3.4" height="13.5" rx="0.6"/><rect x="16.6" y="15.5" width="3.4" height="4.5" rx="0.6"/></svg>
        </div>
        <div class="title">Điểm</div>
        <div class="desc">Nhập điểm theo môn học, xem bảng điểm và xếp loại từng sinh viên.</div>
        <div class="go">Mở module &rarr;</div>
    </a>

    <a class="stat-card" href="${pageContext.request.contextPath}/sach">
        <div class="icon">
            <svg width="19" height="19" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><path d="M5.5 4.3h11.4c0.9 0 1.6 0.7 1.6 1.6v12.8c0 0.5-0.4 0.9-0.9 0.9H7.1c-0.9 0-1.6-0.7-1.6-1.6V4.3z"/><line x1="5.5" y1="4.3" x2="5.5" y2="17.7"/></svg>
        </div>
        <div class="title">Sách</div>
        <div class="desc">Quản lý kho sách: mã sách, tác giả, số lượng tồn.</div>
        <div class="go">Mở module &rarr;</div>
    </a>

    <a class="stat-card" href="${pageContext.request.contextPath}/san-pham">
        <div class="icon">
            <svg width="19" height="19" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><path d="M3.3 7.5L12 3l8.7 4.5L12 12z"/><path d="M3.3 7.5v9L12 21l8.7-4.5v-9"/><line x1="12" y1="12" x2="12" y2="21"/></svg>
        </div>
        <div class="title">Sản phẩm</div>
        <div class="desc">Quản lý sản phẩm: giá bán, số lượng tồn kho.</div>
        <div class="go">Mở module &rarr;</div>
    </a>
</div>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
