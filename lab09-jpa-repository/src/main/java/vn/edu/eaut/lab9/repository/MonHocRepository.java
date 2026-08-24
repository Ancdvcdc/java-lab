package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.MonHoc;

import java.util.List;

public class MonHocRepository extends BaseRepository<MonHoc, Integer> {

    public MonHocRepository() {
        super(MonHoc.class);
    }

    @Override
    public List<MonHoc> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MonHoc m ORDER BY m.tenMonHoc", MonHoc.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByMaMonHoc(String maMonHoc) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(m) FROM MonHoc m WHERE m.maMonHoc = :ma", Long.class)
                    .setParameter("ma", maMonHoc)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
