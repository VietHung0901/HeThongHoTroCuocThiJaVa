package DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

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

    @Column(name = "name", length = 50, nullable = false)
    private String tenCuocThi;

    private Date ngayThi;

    private int soLuongThiSinh;

    private String DiaDiemThi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monThi_id", referencedColumnName = "id")
    @ToString.Exclude
    private MonThi monThi;

    @OneToMany(mappedBy = "chiTietNoiDung", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChiTietNoiDung> chiTietNoiDungs = new ArrayList<>();

    @OneToMany(mappedBy = "chiTietQuyDinh", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChiTietQuyDinh> chiTietQuyDinhs = new ArrayList<>();

    private int trangThai;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        Truong truong = (Truong) o;
        return getId() != null && Objects.equals(getId(),
                truong.getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
