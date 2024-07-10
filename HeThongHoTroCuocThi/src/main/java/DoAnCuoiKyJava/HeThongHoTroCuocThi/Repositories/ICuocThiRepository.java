package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ICuocThiRepository extends JpaRepository<CuocThi, Long> {
    //Xuất các loại trường đang không bị ẩn
    List<CuocThi> findByTrangThai(int trangThai);
    List<CuocThi> findByNgayThiBetweenOrderByNgayThi(LocalDateTime startDate, LocalDateTime endDate);
    List<CuocThi> findByDiaDiemThiContainingIgnoreCase(String diaDiemThi);

    // Tìm kiếm cuộc thi theo khoảng thời gian và địa điểm
    List<CuocThi> findByNgayThiBetweenAndDiaDiemThiContainingIgnoreCaseOrderByNgayThi(LocalDateTime startDate, LocalDateTime endDate, String diaDiemThi);
    List<CuocThi> findByMonThiId(Long monThiId);
}


