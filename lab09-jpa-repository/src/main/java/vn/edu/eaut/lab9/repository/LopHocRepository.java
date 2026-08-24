package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.LopHoc;

import java.util.List;

public class LopHocRepository extends BaseRepository<LopHoc, Integer> {

    public LopHocRepository() {
        super(LopHoc.class);
    }

    /** Dung JOIN FETCH + DISTINCT de tai luon danh sach sinh vien cua tung lop,
     *  tranh LazyInitializationException khi JSP goi lop.getDanhSachSinhVien().size(). */
    @Override
    public List<LopHoc> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT DISTINCT l FROM LopHoc l "
                    + "LEFT JOIN FETCH l.danhSachSinhVien ORDER BY l.tenLop";
            return em.createQuery(jpql, LopHoc.class).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByMaLop(String maLop) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(l) FROM LopHoc l WHERE l.maLop = :ma", Long.class)
                    .setParameter("ma", maLop)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
