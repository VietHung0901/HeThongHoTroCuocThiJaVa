package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.ChiTietNoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IChiTietNoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChiTietNoiDungService {
    private final IChiTietNoiDungRepository CTNDRepository;
    public ChiTietNoiDung addChiTietNoiDung(ChiTietNoiDung CTND) {
        return CTNDRepository.save(CTND);
    }
}
