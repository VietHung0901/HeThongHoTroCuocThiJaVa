package DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "CuocThi")
public class CuocThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenCuocThi", length = 50, nullable = false)
    private String tenCuocThi;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayThi;

    private int soLuongThiSinh;

    private String diaDiemThi;

    private Long loaiTruongId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monThi_id", referencedColumnName = "id")
    @ToString.Exclude
    private MonThi monThi;

    @OneToMany(mappedBy = "cuocThi", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChiTietNoiDung> chiTietNoiDungs = new ArrayList<>();

    @OneToMany(mappedBy = "cuocThi", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChiTietQuyDinh> chiTietQuyDinhs = new ArrayList<>();

    @OneToMany(mappedBy = "cuocThi", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PhieuDangKy> phieuDangKIES = new ArrayList<>();
    private int trangThai;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) !=
//                Hibernate.getClass(o)) return false;
//        Truong truong = (Truong) o;
//        return getId() != null && Objects.equals(getId(),
//                truong.getId());
//    }
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false; // Sửa đổi ở đây
    CuocThi cuocThi = (CuocThi) o;
    return Objects.equals(id, cuocThi.id) &&
            Objects.equals(tenCuocThi, cuocThi.tenCuocThi) &&
            Objects.equals(ngayThi, cuocThi.ngayThi) &&
            Objects.equals(diaDiemThi, cuocThi.diaDiemThi);
}

    @Override
    public int hashCode() {
        return Objects.hash(id, tenCuocThi, ngayThi, diaDiemThi);
    }


}
