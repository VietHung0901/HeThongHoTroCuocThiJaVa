package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.ChiTietQuyDinh;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IChiTietQuyDinhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietQuyDinhService {
    private final IChiTietQuyDinhRepository CTQDRepository;
    public ChiTietQuyDinh addChiTietQuyDinh(ChiTietQuyDinh CTQD) {
        return CTQDRepository.save(CTQD);
    }
}
