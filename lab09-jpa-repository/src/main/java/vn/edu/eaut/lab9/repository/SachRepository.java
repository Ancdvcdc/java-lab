package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.Sach;

import java.util.List;

public class SachRepository extends BaseRepository<Sach, Integer> {

    public SachRepository() {
        super(Sach.class);
    }

    @Override
    public List<Sach> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Sach s ORDER BY s.tenSach", Sach.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /** Bai 13: tim kiem sach theo ten hoac tac gia bang JPQL. */
    public List<Sach> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT s FROM Sach s "
                    + "WHERE LOWER(s.tenSach) LIKE :kw OR LOWER(s.tacGia) LIKE :kw "
                    + "ORDER BY s.tenSach";
            return em.createQuery(jpql, Sach.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByMaSach(String maSach) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Sach s WHERE s.maSach = :ma", Long.class)
                    .setParameter("ma", maSach)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
