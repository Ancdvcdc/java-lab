package vn.edu.eaut.lab9.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.Diem;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Lop Service dung de kiem tra du lieu (validate) truoc khi goi Repository.
 * Bai 10: xu ly loi trung ma, sai dinh dang email, thieu truong bat buoc.
 */
public class SinhVienService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final SinhVienRepository repository = new SinhVienRepository();

    public void themMoi(SinhVien sv) {
        validate(sv, null);
        repository.save(sv);
    }

    public void capNhat(SinhVien sv) {
        validate(sv, sv.getId());
        repository.update(sv);
    }

    /**
     * Xoa sinh vien. Vi bang diem co khoa ngoai tro toi sinh_vien (fk_diem_sinhvien),
     * neu xoa truc tiep sinh vien dang co diem, MySQL se tu choi (ConstraintViolationException).
     * Vi vay can xoa het cac ban ghi Diem lien quan TRUOC, roi moi xoa SinhVien,
     * trong CUNG MOT transaction - neu buoc nao loi thi rollback toan bo (khong mat du lieu nua vong).
     */
    public void xoa(Integer id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Thao tac 1: xoa toan bo diem cua sinh vien nay (neu co)
            em.createQuery("DELETE FROM Diem d WHERE d.sinhVien.id = :svId")
                    .setParameter("svId", id)
                    .executeUpdate();

            // Thao tac 2: xoa sinh vien
            SinhVien sv = em.find(SinhVien.class, id);
            if (sv != null) {
                em.remove(sv);
            }

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    /**
     * Bai 11: Transaction gom nhieu thao tac - them 1 sinh vien MOI va tao diem mac dinh (0)
     * cho TAT CA mon hoc hien co, trong CUNG MOT transaction (dung chung 1 EntityManager).
     * Neu bat ky thao tac nao loi (vi du trung ma sinh vien, hoac loi khi tao diem),
     * toan bo se duoc rollback - khong co sinh vien nao duoc them, khong co diem nao duoc tao.
     */
    public void themMoiVaKhoiTaoDiem(SinhVien sv, List<MonHoc> danhSachMonHoc) {
        validate(sv, null); // kiem tra du lieu truoc, ngoai transaction (loi validate khong can rollback DB)

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Thao tac 1: them sinh vien
            em.persist(sv);

            // Thao tac 2: tao diem mac dinh = 0 cho tung mon hoc
            for (MonHoc mh : danhSachMonHoc) {
                MonHoc mhRef = em.getReference(MonHoc.class, mh.getId());
                Diem diem = new Diem(sv, mhRef, 0.0);
                em.persist(diem);
            }

            tx.commit(); // Chi khi CA HAI thao tac deu thanh cong moi commit
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback(); // Loi o buoc nao cung rollback toan bo, khong luu du lieu nua vong
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    private void validate(SinhVien sv, Integer currentId) {
        if (sv.getMaSinhVien() == null || sv.getMaSinhVien().isBlank()) {
            throw new ValidationException("Ma sinh vien khong duoc de trong.");
        }
        if (sv.getHoTen() == null || sv.getHoTen().isBlank()) {
            throw new ValidationException("Ho ten khong duoc de trong.");
        }
        if (sv.getEmail() != null && !sv.getEmail().isBlank()
                && !EMAIL_PATTERN.matcher(sv.getEmail()).matches()) {
            throw new ValidationException("Email khong dung dinh dang.");
        }
        if (repository.existsByMaSinhVien(sv.getMaSinhVien(), currentId)) {
            throw new ValidationException(
                    "Ma sinh vien '" + sv.getMaSinhVien() + "' da ton tai, vui long chon ma khac.");
        }
    }
}
