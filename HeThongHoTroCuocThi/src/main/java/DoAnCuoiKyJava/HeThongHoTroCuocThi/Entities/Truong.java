package DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Truong")
public class Truong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String tenTruong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaiTruong_id", referencedColumnName = "id")
    @ToString.Exclude
    private LoaiTruong loaiTruong;

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
