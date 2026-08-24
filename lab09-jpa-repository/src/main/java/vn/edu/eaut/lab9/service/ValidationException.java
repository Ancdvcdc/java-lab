package vn.edu.eaut.lab9.service;

/** Ngoai le nghiep vu dung de bao loi validate cho nguoi dung (khong phai loi he thong). */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
