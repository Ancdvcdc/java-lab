package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Lưu danh sách tài khoản trong bộ nhớ, phục vụ Bài 9 - phân quyền ADMIN / USER.
 * Tài khoản mẫu:
 *   admin / 123456  -> quyền ADMIN (thêm, sửa, xóa, xem)
 *   user  / 123456  -> quyền USER  (chỉ xem)
 */
public class UserStore {

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "123456", "ADMIN"));
        users.add(new User("user", "123456", "USER"));
    }

    public static User findByUsernameAndPassword(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
