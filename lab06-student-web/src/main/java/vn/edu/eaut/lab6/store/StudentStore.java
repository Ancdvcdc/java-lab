package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Lưu danh sách sinh viên trong bộ nhớ (in-memory store).
 * Dữ liệu mẫu được khởi tạo bởi DataInitListener (Bài 12) khi ứng dụng khởi động,
 * chứ không khởi tạo cứng tại đây, để đúng yêu cầu Bài 12.
 */
public class StudentStore {

    private static final List<Student> students = new ArrayList<>();

    public static List<Student> findAll() {
        return students;
    }

    public static void add(Student student) {
        students.add(student);
    }

    public static Student findById(String id) {
        for (Student sv : students) {
            if (sv.getId().equalsIgnoreCase(id)) {
                return sv;
            }
        }
        return null;
    }

    /** Bài 7: xóa sinh viên theo mã. Trả về true nếu xóa thành công. */
    public static boolean deleteById(String id) {
        return students.removeIf(sv -> sv.getId().equalsIgnoreCase(id));
    }

    /** Bài 8: cập nhật thông tin sinh viên (không đổi mã sinh viên). */
    public static boolean update(String id, String name, String className, String email) {
        Student sv = findById(id);
        if (sv == null) {
            return false;
        }
        sv.setName(name);
        sv.setClassName(className);
        sv.setEmail(email);
        return true;
    }

    /** Bài 6: tìm kiếm theo tên, không phân biệt chữ hoa/thường. */
    public static List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        for (Student sv : students) {
            if (sv.getName() != null && sv.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(sv);
            }
        }
        return result;
    }

    /** Bài 10: thống kê số lượng sinh viên theo từng lớp, dùng cho Dashboard. */
    public static Map<String, Integer> countByClass() {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Student sv : students) {
            String lop = sv.getClassName() == null ? "(Chưa xếp lớp)" : sv.getClassName();
            map.merge(lop, 1, Integer::sum);
        }
        return map;
    }
}
