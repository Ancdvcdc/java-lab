# BÁO CÁO LAB 7 – CRUD BẰNG SERVLET + JSP, MVC ĐƠN GIẢN

## 1. Mục tiêu
Xây dựng ứng dụng web CRUD trên nền Jakarta EE (Servlet + JSP + JSTL), tổ chức mã nguồn
theo mô hình MVC đơn giản (Model – Repository – Controller – View), có quản lý phiên
đăng nhập (Session) và bảo vệ khu vực quản trị bằng Filter.

## 2. Cấu trúc project
```
lab07-crud-mvc/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab7/
    │   ├── model/        (SinhVien, Sach, SanPham, LopHoc, DiemSinhVien, GioHangItem)
    │   ├── repository/   (lưu dữ liệu bằng List trong bộ nhớ, có findAll/add/update/delete/search)
    │   ├── controller/   (Servlet: SinhVienController, SachController, SanPhamController,
    │   │                  LopHocController, DiemController, GioHangController, LoginController)
    │   ├── filter/        (LoginFilter bảo vệ /admin/*)
    │   └── listener/      (AppLifecycleListener ghi log start/stop app và session)
    └── webapp/
        ├── index.jsp, login.jsp
        ├── admin/index.jsp        (trang quản trị, chỉ vào được sau khi đăng nhập)
        └── views/{sinhvien,sach,sanpham,lophoc,diem,giohang}/*.jsp
```

## 3. Luồng xử lý MVC (Browser → Servlet → Repository → JSP)
1. **Browser** gửi request (GET/POST) tới một URL, ví dụ `/sinh-vien?action=edit&id=1`.
2. **Controller (Servlet)** nhận request, đọc tham số, gọi **Repository** tương ứng để
   lấy/ghi dữ liệu (List trong bộ nhớ đóng vai trò "CSDL giả lập").
3. Controller đặt dữ liệu vào `request` (`setAttribute`) rồi **forward** sang trang JSP
   để hiển thị (dùng `forward` khi cùng một request, không đổi URL trên trình duyệt),
   hoặc **sendRedirect** sang một URL khác sau khi ghi dữ liệu (POST xong) để tránh
   gửi lại form khi người dùng bấm F5 (mẫu Post/Redirect/Get).
4. **View (JSP + JSTL)** chỉ đọc dữ liệu từ `request`/`session` bằng EL và JSTL
   (`<c:forEach>`, `<c:if>`...) để hiển thị, không chứa logic nghiệp vụ.
5. Với module Giỏ hàng, dữ liệu được lưu vào **session** (không phải request) vì cần
   tồn tại xuyên suốt nhiều lượt request của cùng một người dùng.
6. `LoginFilter` chặn mọi request tới `/admin/*`: nếu session chưa có `username` thì
   redirect về `login.jsp`, ngược lại cho request đi tiếp (`chain.doFilter`).
7. `AppLifecycleListener` ghi log khi ứng dụng khởi động/dừng và khi session được
   tạo/hủy, giúp theo dõi vòng đời ứng dụng.

## 4. Kết quả đạt được
- Hoàn thành CRUD cho **6 module**: Sinh viên, Sách, Sản phẩm, Lớp học, Điểm sinh viên,
  Giỏ hàng (session).
- Sinh viên: có tìm kiếm theo tên/lớp và **phân trang 5 dòng/trang** (Bài 11).
- Sản phẩm: có **validate** giá > 0 và số lượng ≥ 0, báo lỗi ngay trên form (Bài 7).
- Điểm sinh viên: tự động tính **điểm tổng kết** (chuyên cần 10% + giữa kỳ 30% +
  cuối kỳ 60%) và **xếp loại A/B/C/D/F** (Bài 9).
- Giỏ hàng: thêm/cập nhật số lượng/xóa sản phẩm, tính tổng tiền, lưu bằng `HttpSession`
  (Bài 10).
- Đăng nhập với tài khoản mẫu `admin/123456`, lưu `username` vào session; `LoginFilter`
  bảo vệ toàn bộ khu vực `/admin/*` (Bài 5).
- `AppLifecycleListener` ghi log vòng đời ứng dụng/session (Bài 12).

## 5. Câu hỏi củng cố

**1. Vì sao JSP không nên chứa logic xử lý nghiệp vụ?**
JSP thuộc tầng View, chỉ nên chịu trách nhiệm hiển thị dữ liệu. Nếu đặt logic nghiệp
vụ (tính toán, truy xuất/ghi dữ liệu, validate...) vào JSP, mã sẽ trộn lẫn HTML và
Java (scriptlet), khó đọc, khó bảo trì, khó tái sử dụng và khó kiểm thử. Tách logic
sang Controller/Repository giúp mỗi tầng chỉ có một trách nhiệm (Single Responsibility),
đúng tinh thần MVC.

**2. Khi nào dùng `forward` và khi nào dùng `sendRedirect`?**
- `forward`: dùng khi Controller muốn chuyển tiếp cùng một request (giữ nguyên dữ liệu
  đã set bằng `setAttribute`) sang một JSP để hiển thị kết quả — ví dụ hiển thị danh
  sách, hiển thị form có lỗi validate. URL trên trình duyệt không đổi, xử lý phía server.
- `sendRedirect`: dùng khi muốn trình duyệt gửi một **request mới** tới URL khác —
  thường dùng sau khi xử lý xong một thao tác ghi dữ liệu (thêm/sửa/xóa) theo mẫu
  Post/Redirect/Get, để tránh lặp lại thao tác ghi khi người dùng tải lại trang.

**3. Repository trong Lab 7 có vai trò gì khi chưa dùng CSDL?**
Repository đóng vai trò tầng truy xuất dữ liệu (Data Access Layer), che giấu chi tiết
lưu trữ khỏi Controller. Ở Lab 7 nó dùng `List` trong bộ nhớ để mô phỏng CSDL, cung cấp
các thao tác chuẩn `findAll/findById/add/update/delete/search`. Nhờ vậy, sau này khi
chuyển sang dùng CSDL thật (JDBC/JPA), Controller và View gần như không cần thay đổi,
chỉ cần viết lại phần bên trong Repository.

**4. Session khác request ở điểm nào?**
- `request` chỉ tồn tại trong một lượt gửi–nhận giữa client và server, kết thúc khi
  response được trả về.
- `session` tồn tại xuyên suốt nhiều request của cùng một người dùng (được nhận diện
  qua cookie `JSESSIONID`), cho đến khi hết hạn hoặc bị hủy (`invalidate()`).
Vì vậy dữ liệu cần "nhớ" giữa nhiều lần bấm (như trạng thái đăng nhập, giỏ hàng) phải
lưu ở session, còn dữ liệu chỉ dùng cho một lần hiển thị (như danh sách, kết quả tìm
kiếm) thì đặt ở request là đủ.

**5. Lab 8 có thể chuyển phần nào của Lab 7 sang JSF?**
Có thể chuyển một form CRUD đơn giản, ví dụ module **Sản phẩm** hoặc **Sinh viên**,
sang JSF: Model (JavaBean) giữ nguyên hoặc chuyển thành Managed Bean; phần Controller
(Servlet điều hướng) và View (JSP thuần) được thay bằng JSF Managed Bean + Facelets
(`.xhtml`) với các thẻ `<h:form>`, `<h:inputText>`, `<h:message>`... để tận dụng cơ chế
validate và message có sẵn của JSF thay vì tự viết bằng tay như ở Lab 7.

## 6. Ghi chú
- Dữ liệu lưu trong bộ nhớ (List tĩnh) nên sẽ mất khi restart server — đúng theo yêu
  cầu của bài lab (chưa dùng CSDL).
- Toàn bộ Servlet/Filter/Listener khai báo bằng annotation (`@WebServlet`, `@WebFilter`,
  `@WebListener`), `web.xml` chỉ cấu hình welcome-file và session-timeout.
