-- ================================================
-- Script tao CSDL cho Lab 9 - IT3242 Cong nghe Java
-- Chay script nay TRUOC khi khoi dong ung dung.
-- Hibernate (hibernate.hbm2ddl.auto=update) se tu tao bang
-- neu chua co, nhung nen tao san CSDL de chac chan dung ten.
-- ================================================

CREATE DATABASE IF NOT EXISTS lab09_jpa
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE lab09_jpa;

CREATE TABLE IF NOT EXISTS lop_hoc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_lop VARCHAR(20) NOT NULL UNIQUE,
    ten_lop VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS sinh_vien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sinh_vien VARCHAR(20) NOT NULL UNIQUE,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    ngay_sinh DATE,
    lop_hoc_id INT,
    CONSTRAINT fk_sinhvien_lophoc FOREIGN KEY (lop_hoc_id) REFERENCES lop_hoc(id)
);

-- Bai 7: Mon hoc va Diem
CREATE TABLE IF NOT EXISTS mon_hoc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_mon_hoc VARCHAR(20) NOT NULL UNIQUE,
    ten_mon_hoc VARCHAR(100) NOT NULL,
    so_tin_chi INT
);

CREATE TABLE IF NOT EXISTS diem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    diem_so DOUBLE,
    sinh_vien_id INT NOT NULL,
    mon_hoc_id INT NOT NULL,
    CONSTRAINT fk_diem_sinhvien FOREIGN KEY (sinh_vien_id) REFERENCES sinh_vien(id),
    CONSTRAINT fk_diem_monhoc FOREIGN KEY (mon_hoc_id) REFERENCES mon_hoc(id)
);

-- Bai 13: Sach va San pham
CREATE TABLE IF NOT EXISTS sach (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sach VARCHAR(20) NOT NULL UNIQUE,
    ten_sach VARCHAR(150) NOT NULL,
    tac_gia VARCHAR(100),
    so_luong INT
);

CREATE TABLE IF NOT EXISTS san_pham (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_san_pham VARCHAR(20) NOT NULL UNIQUE,
    ten_san_pham VARCHAR(150) NOT NULL,
    gia DOUBLE,
    so_luong INT
);

-- Du lieu mau (co the bo qua vi ung dung da co DataSeedListener tu dong seed)
-- INSERT INTO lop_hoc (ma_lop, ten_lop) VALUES ('IT01', 'Cong nghe phan mem K17');
-- INSERT INTO sinh_vien (ma_sinh_vien, ho_ten, email, lop_hoc_id) VALUES ('SV001', 'Nguyen Van A', 'a@example.com', 1);
