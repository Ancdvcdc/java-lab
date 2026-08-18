# Lab 5 - MiniShop (Java Swing + JDBC)

Project Maven đầy đủ cho **Bài 1 → Bài 5** (Phần A) của Lab 5:
1. Kết nối CSDL bằng JDBC (`DBHelper`)
2. Quản lý sản phẩm - CRUD + tìm kiếm (`SanPhamPanel`)
3. Quản lý khách hàng - CRUD + validate SĐT (`KhachHangPanel`)
4. Lập hóa đơn + chi tiết hóa đơn, có transaction + trừ kho (`HoaDonPanel`)
5. Tìm kiếm & thống kê bằng SwingWorker (`ThongKePanel`)

Giao diện `MainFrame` dùng `JTabbedPane` với đúng 4 tab: **Sản Phẩm | Khách Hàng | Hóa Đơn | Thống Kê**.

## 1. Chuẩn bị CSDL

Bạn đã có sẵn MySQL và dữ liệu, chỉ cần đảm bảo:
- Database tên **`minishop_db`**, đủ 4 bảng: `san_pham`, `khach_hang`, `hoa_don`, `chi_tiet_hoa_don`
  (đúng cấu trúc cột như trong `database/minishop_db.sql`).
- Nếu database/bảng đã tồn tại, **không cần chạy lại file SQL** (script dùng `IF NOT EXISTS` nên chạy lại cũng an toàn, sẽ không tạo trùng, chỉ có phần `INSERT` dữ liệu mẫu là bạn tự cân nhắc bỏ qua nếu đã có dữ liệu).

## 2. Cấu hình kết nối

Mở file:
```
src/main/java/vn/edu/eaut/lab5/config/DBHelper.java
```
Sửa lại `USER` và `PASSWORD` cho đúng với MySQL trên máy bạn (mặc định đang để `root` / mật khẩu rỗng):
```java
private static final String USER = "root";
private static final String PASSWORD = ""; // đổi lại mật khẩu MySQL của bạn
```

## 3. Mở project bằng NetBeans

1. Mở NetBeans → **File → Open Project...**
2. Chọn thư mục `lab05-minishop-swing-jdbc` (thư mục có sẵn file `pom.xml`) → NetBeans tự nhận đây là Maven project.
3. Chờ NetBeans tải dependency `mysql-connector-j` (cần mạng để Maven tải lần đầu vào kho `~/.m2`).
4. Trong Project → chuột phải vào project → **Run** (hoặc F6). NetBeans sẽ tự build và chạy `App.java` (đã set sẵn `mainClass` trong `pom.xml`).

> Nếu NetBeans báo không tìm thấy Main Class: chuột phải project → Properties → Run → Main Class → chọn `vn.edu.eaut.lab5.App`.

## 4. Chạy bằng dòng lệnh (nếu có Maven)

```bash
mvn clean compile exec:java
```

hoặc đóng gói kèm thư viện rồi chạy jar:
```bash
mvn clean package
java -jar target/lab05-minishop-swing-jdbc.jar
```

## 5. Cấu trúc project

```
lab05-minishop-swing-jdbc/
├── pom.xml
├── database/minishop_db.sql
└── src/main/java/vn/edu/eaut/lab5/
    ├── App.java                 -> điểm khởi chạy, test kết nối CSDL, mở MainFrame
    ├── config/DBHelper.java     -> Bài 1: kết nối JDBC
    ├── model/                   -> SanPham, KhachHang, HoaDon, ChiTietHoaDon
    ├── dal/                     -> SanPhamDAL, KhachHangDAL, HoaDonDAL, ThongKeDAL
    ├── bus/                     -> SanPhamBUS, KhachHangBUS, HoaDonBUS, ThongKeBUS
    ├── ui/
    │   ├── MainFrame.java       -> JTabbedPane 4 tab: Sản Phẩm / Khách Hàng / Hóa Đơn / Thống Kê
    │   ├── SanPhamPanel.java    -> Bài 2: CRUD + tìm kiếm sản phẩm
    │   ├── KhachHangPanel.java  -> Bài 3: CRUD + validate SĐT (DocumentFilter)
    │   ├── HoaDonPanel.java     -> Bài 4: lập hóa đơn, chi tiết hóa đơn, transaction, trừ kho
    │   └── ThongKePanel.java    -> Bài 5: tìm kiếm & thống kê dùng SwingWorker
    └── util/
        ├── MessageUtil.java
        └── PhoneDocumentFilter.java
```

## 6. Một số điểm đáng chú ý khi báo cáo

- **Mô hình 3 lớp**: GUI (`ui`) không viết SQL trực tiếp; GUI chỉ gọi `bus` (validate + xử lý nghiệp vụ), `bus` gọi `dal` (JDBC/PreparedStatement).
- **Transaction**: `HoaDonDAL.insertHoaDon()` dùng `conn.setAutoCommit(false)` + `commit()/rollback()` để đảm bảo lưu `hoa_don` + `chi_tiet_hoa_don` + trừ tồn kho là 1 khối thống nhất.
- **SwingWorker**: tất cả 4 thao tác trong tab Thống Kê (tìm hóa đơn theo ngày, doanh thu theo khoảng ngày, hóa đơn cao nhất, sản phẩm bán chạy nhất) đều chạy `doInBackground()` trên luồng nền, chỉ cập nhật GUI ở `done()` (chạy lại trên EDT) — không treo giao diện.
- **Validate SĐT**: validate 2 lớp — chặn nhập sai ngay trên `JTextField` bằng `PhoneDocumentFilter` (DocumentFilter), và validate lại lần nữa ở `KhachHangBUS` trước khi lưu CSDL (phòng trường hợp dữ liệu đưa vào không qua GUI).
- **Liên kết giữa các tab**: sau khi lập hóa đơn thành công ở tab Hóa Đơn, `MainFrame` sẽ tự gọi `sanPhamPanel.loadData()` để tab Sản Phẩm cập nhật lại số lượng tồn kho mới nhất.
