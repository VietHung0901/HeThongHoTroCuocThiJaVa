package DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.ChiTietNoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChiTietNoiDungRepository extends JpaRepository<ChiTietNoiDung, Long>{
    // moi vua them vao
    List<ChiTietNoiDung> findByCuocThiId(Long cuocThiId);
}
