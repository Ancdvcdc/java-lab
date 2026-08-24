package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.Diem;

import java.util.List;

public class DiemRepository extends BaseRepository<Diem, Integer> {

    public DiemRepository() {
        super(Diem.class);
    }

    /** Dung JOIN FETCH de tai luon SinhVien + MonHoc, tranh LazyInitializationException tren JSP. */
    @Override
    public List<Diem> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d "
                    + "JOIN FETCH d.sinhVien JOIN FETCH d.monHoc ORDER BY d.id DESC";
            return em.createQuery(jpql, Diem.class).getResultList();
        } finally {
            em.close();
        }
    }

    /** Bai 7: danh sach diem cua 1 sinh vien (de tinh diem trung binh, xep loai). */
    public List<Diem> findBySinhVien(Integer sinhVienId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d JOIN FETCH d.monHoc "
                    + "WHERE d.sinhVien.id = :svId ORDER BY d.monHoc.tenMonHoc";
            return em.createQuery(jpql, Diem.class)
                    .setParameter("svId", sinhVienId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /** Bai 7: diem trung binh cua 1 sinh vien, dung JPQL AVG(). */
    public Double diemTrungBinh(Integer sinhVienId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT AVG(d.diemSo) FROM Diem d WHERE d.sinhVien.id = :svId";
            return em.createQuery(jpql, Double.class)
                    .setParameter("svId", sinhVienId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public boolean existsBySinhVienAndMonHoc(Integer sinhVienId, Integer monHocId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(d) FROM Diem d "
                    + "WHERE d.sinhVien.id = :svId AND d.monHoc.id = :mhId";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("svId", sinhVienId)
                    .setParameter("mhId", monHocId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    /** Dung de kiem tra truoc khi xoa MonHoc: neu da co diem thi khong nen xoa truc tiep. */
    public long countByMonHoc(Integer monHocId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(d) FROM Diem d WHERE d.monHoc.id = :mhId";
            return em.createQuery(jpql, Long.class)
                    .setParameter("mhId", monHocId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
