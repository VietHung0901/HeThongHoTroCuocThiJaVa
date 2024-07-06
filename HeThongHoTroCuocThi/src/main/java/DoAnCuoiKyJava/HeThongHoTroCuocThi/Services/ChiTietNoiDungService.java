package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.ChiTietNoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IChiTietNoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiTietNoiDungService {
    private final IChiTietNoiDungRepository CTNDRepository;
    public ChiTietNoiDung addChiTietNoiDung(ChiTietNoiDung CTND) {
        return CTNDRepository.save(CTND);
    }

    //moi vua them vao
    public List<ChiTietNoiDung> getChiTietNoiDungsByCuocThiId(Long cuocThiId) {
        return CTNDRepository.findByCuocThiId(cuocThiId);
    }
}
