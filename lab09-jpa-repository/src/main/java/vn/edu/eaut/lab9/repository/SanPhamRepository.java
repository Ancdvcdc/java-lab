package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.SanPham;

import java.util.List;

public class SanPhamRepository extends BaseRepository<SanPham, Integer> {

    public SanPhamRepository() {
        super(SanPham.class);
    }

    @Override
    public List<SanPham> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SanPham s ORDER BY s.tenSanPham", SanPham.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /** Bai 13: tim kiem san pham theo ten bang JPQL. */
    public List<SanPham> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT s FROM SanPham s WHERE LOWER(s.tenSanPham) LIKE :kw ORDER BY s.tenSanPham";
            return em.createQuery(jpql, SanPham.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByMaSanPham(String maSanPham) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM SanPham s WHERE s.maSanPham = :ma", Long.class)
                    .setParameter("ma", maSanPham)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
