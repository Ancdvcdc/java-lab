# Lab 6 - Student Web (Servlet + JSP + JSTL + Filter + Listener)

Maven Web Project đầy đủ **12 bài** của Lab 6 (Chương 3 - Công nghệ Java), tổ chức theo mô hình MVC
cơ bản: Model - View - Controller - Store.

## 1. Yêu cầu môi trường

- JDK 17 hoặc 21
- Apache Maven
- Apache Tomcat **10.x** (bắt buộc, vì dùng `jakarta.*` namespace, không phải `javax.*`)
- NetBeans có tích hợp sẵn Tomcat, hoặc bạn tự cài Tomcat 10 rồi khai báo Server trong NetBeans

## 2. Mở project bằng NetBeans

1. **File → Open Project** → chọn thư mục `lab06-student-web` (có sẵn `pom.xml`).
2. Chuột phải project → **Properties → Run** → kiểm tra mục **Server** đã trỏ tới Tomcat 10.x
   (nếu chưa có server nào, vào **Tools → Servers** để thêm Tomcat 10 trước).
3. Chuột phải project → **Run** (hoặc Shift+F6). NetBeans sẽ build WAR và deploy lên Tomcat.
4. Trình duyệt tự mở `http://localhost:8080/lab06-student-web/` (hoặc bạn tự gõ).

## 3. Tài khoản đăng nhập mẫu

| Tài khoản | Mật khẩu | Quyền |
|---|---|---|
| admin | 123456 | ADMIN (xem/thêm/sửa/xóa) |
| user | 123456 | USER (chỉ xem) |

## 4. Danh sách 12 bài và cách kiểm thử

### Phần A - Có gợi ý code

| Bài | Nội dung | Cách kiểm thử |
|---|---|---|
| 1 | Servlet Hello | `/hello` — hiển thị "Hello, Servlet" |
| 2 | Form thêm sinh viên | Đăng nhập bằng `admin`, vào `/students` → "+ Thêm sinh viên" |
| 3 | StudentStore + JSP/JSTL | `/students` — danh sách sinh viên hiển thị bằng `<c:forEach>` |
| 4 | Đăng nhập + Session | `/login.jsp` → đăng nhập → `welcome.jsp` |
| 5 | Filter + Listener | Xem **Output/console** của Tomcat khi khởi động (`Ung dung Lab 6 da khoi dong`), khi đăng nhập (`Session moi duoc tao`), và khi truy cập `/students` mà chưa đăng nhập (bị đá về `login.jsp`) |

### Phần B - Tự làm (đã hoàn thành đủ 7/7, yêu cầu tối thiểu 5/7)

| Bài | Nội dung | Cách kiểm thử |
|---|---|---|
| 6 | Tìm kiếm sinh viên | Ở `/students`, gõ vào ô "Tìm theo họ tên" (không phân biệt hoa/thường) |
| 7 | Xóa sinh viên | Đăng nhập `admin`, bấm "Xóa" ở 1 dòng trong bảng |
| 8 | Sửa sinh viên | Đăng nhập `admin`, bấm "Sửa" → form hiện dữ liệu cũ, mã SV không cho sửa |
| 9 | Phân quyền Admin/User | Đăng nhập bằng `user`, vào `/students` sẽ **không thấy** nút Thêm/Sửa/Xóa; nếu cố truy cập trực tiếp URL `/student-form.jsp` sẽ bị chuyển tới `/403.jsp` |
| 10 | Dashboard | Sau khi đăng nhập, vào `welcome.jsp` → "Xem Dashboard" (`/dashboard`) — hiện tổng số SV, số SV theo lớp, giờ đăng nhập |
| 11 | Log truy cập (Filter) | Xem console Tomcat, mỗi request đều có dòng `[LOG] ... | GET /students | user=admin` |
| 12 | Khởi tạo dữ liệu mẫu (Listener) | Khi Tomcat khởi động, console in `DataInitListener: da khoi tao 5 sinh vien mau`; `/students` hiển thị sẵn 5 sinh viên mẫu (SV001 → SV005) |

## 5. Cấu trúc project

```
lab06-student-web/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab6/
    │   ├── controller/
    │   │   ├── HelloServlet.java          Bài 1
    │   │   ├── StudentServlet.java        Bài 2, 3, 6  (/students - GET danh sách, POST thêm)
    │   │   ├── StudentEditServlet.java    Bài 8        (/student-edit)
    │   │   ├── StudentDeleteServlet.java  Bài 7        (/student-delete)
    │   │   ├── LoginServlet.java          Bài 4, 9, 10 (/login)
    │   │   ├── LogoutServlet.java         Bài 4        (/logout)
    │   │   └── DashboardServlet.java      Bài 10       (/dashboard)
    │   ├── filter/
    │   │   ├── AuthFilter.java            Bài 5  - bắt buộc đăng nhập
    │   │   ├── AdminFilter.java           Bài 9  - bắt buộc quyền ADMIN cho thao tác ghi
    │   │   └── LogFilter.java             Bài 11 - ghi log mọi request
    │   ├── listener/
    │   │   ├── AppContextListener.java    Bài 5  - log start/stop ứng dụng
    │   │   ├── SessionLogListener.java    Bài 5  - log tạo/hủy session
    │   │   └── DataInitListener.java      Bài 12 - khởi tạo 5 sinh viên mẫu
    │   ├── model/
    │   │   ├── Student.java
    │   │   └── User.java                  phục vụ Bài 9
    │   └── store/
    │       ├── StudentStore.java          CRUD + tìm kiếm + thống kê trong bộ nhớ
    │       └── UserStore.java             tài khoản mẫu ADMIN/USER
    └── webapp/
        ├── index.jsp                      điều hướng theo trạng thái đăng nhập
        ├── login.jsp                      Bài 4
        ├── welcome.jsp                    Bài 4, 10
        ├── student-form.jsp               Bài 2
        ├── student-edit.jsp               Bài 8
        ├── student-list.jsp               Bài 3, 6, 7, 8, 9
        ├── dashboard.jsp                  Bài 10
        ├── 403.jsp                        Bài 9
        └── WEB-INF/web.xml
```

## 6. Một số điểm cần chú ý khi viết báo cáo

- **Luồng Request → Servlet → JSP → Response**: ví dụ `/students` (GET) → `StudentServlet.doGet()` →
  `request.setAttribute("students", ...)` → `RequestDispatcher.forward()` → `student-list.jsp` render
  bằng JSTL `<c:forEach>`.
- **forward vs sendRedirect**: các thao tác hiển thị dữ liệu (xem danh sách, xem dashboard) dùng
  `forward` (giữ nguyên request, 1 lần round-trip); các thao tác thay đổi dữ liệu (thêm, sửa, xóa) dùng
  `sendRedirect` sau khi xử lý xong để tránh submit lại form khi người dùng bấm F5 (Post/Redirect/Get pattern).
- **2 tầng Filter**: `AuthFilter` kiểm tra đăng nhập (chạy trước), `AdminFilter` kiểm tra quyền ADMIN cho
  riêng các thao tác ghi dữ liệu (chạy sau, chỉ chặn thêm khi cần).
- **Dữ liệu đang lưu ở đâu?** Toàn bộ `StudentStore`/`UserStore` là `static List` trong bộ nhớ (RAM) của
  ứng dụng — mất hết khi restart Tomcat. Nhược điểm: không lưu trữ lâu dài, không dùng được khi có nhiều
  instance server (không chia sẻ được dữ liệu). Đây là điểm Lab 7, Lab 8 sẽ khắc phục bằng cách kết nối
  CSDL thật (JDBC/JPA) thay cho `store` in-memory.
