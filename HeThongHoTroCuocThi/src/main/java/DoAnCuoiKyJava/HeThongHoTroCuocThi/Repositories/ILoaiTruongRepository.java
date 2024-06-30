package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoaiTruongRepository extends JpaRepository<LoaiTruong, Long> {
    Optional<LoaiTruong> findByTenLoaiTruong(String tenLoaiTruong);
}
