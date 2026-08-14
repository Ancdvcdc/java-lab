package vn.edu.eaut.lab3;

public class Student {

    private String maSV;
    private String hoTen;
    private double diemTB;

    public Student(String maSV, String hoTen, double diemTB) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diemTB = diemTB;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(double diemTB) {
        this.diemTB = diemTB;
    }

    public String getXepLoai() {
        if (diemTB >= 8.5) {
            return "Giỏi";
        } else if (diemTB >= 7) {
            return "Khá";
        } else if (diemTB >= 5) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}
