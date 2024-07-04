package DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @NotNull(message = "Ngày thi không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayThi;

    private int soLuongThiSinh;

    private String DiaDiemThi;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quyDinh_id", referencedColumnName = "id")
    @ToString.Exclude
    private QuyDinh quyDinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noiDung_id", referencedColumnName = "id")
    @ToString.Exclude
    private NoiDung noiDung;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        CuocThi cuocThi = (CuocThi) o;
        return getId() != null && Objects.equals(getId(),
                cuocThi.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
