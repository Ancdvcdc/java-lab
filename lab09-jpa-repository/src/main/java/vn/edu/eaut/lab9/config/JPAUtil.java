package vn.edu.eaut.lab9.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Lop dung chung de lay EntityManagerFactory cho toan bo ung dung.
 * EntityManagerFactory chi nen tao 1 lan duy nhat (tao rat "nang").
 */
public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("lab09PU");

    private JPAUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
