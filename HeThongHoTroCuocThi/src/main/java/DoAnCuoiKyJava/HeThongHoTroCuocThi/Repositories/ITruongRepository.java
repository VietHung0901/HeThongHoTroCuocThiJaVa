package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITruongRepository extends JpaRepository<Truong, Long> {
    Optional<Truong> findByTenTruong(String tenTruong);
}