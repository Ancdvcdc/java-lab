package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.SinhVien;

import java.util.List;

public class SinhVienRepository extends BaseRepository<SinhVien, Integer> {

    public SinhVienRepository() {
        super(SinhVien.class);
    }

    /** Lay danh sach, sap xep moi nhat len dau. Dung JOIN FETCH de tai luon LopHoc,
     *  tranh loi LazyInitializationException khi JSP truy cap sv.getLopHoc() sau khi EntityManager da dong. */
    @Override
    public List<SinhVien> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s LEFT JOIN FETCH s.lopHoc ORDER BY s.id DESC";
            return em.createQuery(jpql, SinhVien.class).getResultList();
        } finally {
            em.close();
        }
    }

    /** Bai 4/9: tim theo ten hoac ma sinh vien, co phan trang.
     *  Dung JOIN FETCH de tai luon LopHoc, tranh LazyInitializationException khi JSP hien thi ten lop. */
    public List<SinhVien> search(String keyword, int page, int pageSize) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s LEFT JOIN FETCH s.lopHoc "
                    + "WHERE LOWER(s.hoTen) LIKE :kw OR LOWER(s.maSinhVien) LIKE :kw "
                    + "ORDER BY s.id DESC";
            return em.createQuery(jpql, SinhVien.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .setFirstResult((page - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long countSearch(String keyword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM SinhVien s "
                    + "WHERE LOWER(s.hoTen) LIKE :kw OR LOWER(s.maSinhVien) LIKE :kw";
            return em.createQuery(jpql, Long.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    /** Bai 6: liet ke sinh vien theo lop. */
    public List<SinhVien> findByLopHoc(Integer lopHocId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s WHERE s.lopHoc.id = :lopId ORDER BY s.hoTen";
            return em.createQuery(jpql, SinhVien.class)
                    .setParameter("lopId", lopHocId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /** Bai 10: kiem tra trung ma sinh vien truoc khi luu. */
    public boolean existsByMaSinhVien(String maSinhVien, Integer excludeId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM SinhVien s WHERE s.maSinhVien = :ma "
                    + (excludeId != null ? "AND s.id <> :id" : "");
            var query = em.createQuery(jpql, Long.class).setParameter("ma", maSinhVien);
            if (excludeId != null) {
                query.setParameter("id", excludeId);
            }
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
