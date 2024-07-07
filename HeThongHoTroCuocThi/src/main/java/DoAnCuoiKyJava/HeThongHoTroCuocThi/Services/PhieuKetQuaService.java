package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.*;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IPhieuKetQuaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhieuKetQuaService {
    private final IPhieuKetQuaRepository phieuKetQuaRepository;

    public List<PhieuKetQua> getAllPhieuKetQua() {
        return phieuKetQuaRepository.findAll();
    }

    public List<PhieuKetQua> getAllPhieuKetQuastheoCuocThi(CuocThi cuocThi) {
        List<PhieuKetQua> listPKQ = new ArrayList<>();
        for (PhieuKetQua pkq : getAllPhieuKetQua()) {
            if(pkq.getPhieuDangKy().getCuocThi() == cuocThi) {
                listPKQ.add(pkq);
            }
        }
        return listPKQ;
    }

    public Optional<PhieuKetQua> getPhieuKetQuaById(Long id) {
        return phieuKetQuaRepository.findById(id);
    }

    public PhieuKetQua addPhieuKetQua(PhieuKetQua phieuKetQua) {
        phieuKetQua.setTrangThai(1);
        return phieuKetQuaRepository.save(phieuKetQua);
    }

    public String getDiemByCuocThiIdvaUserId(Long cuocThiId,Long userId)
    {
        for (PhieuKetQua pkq : getAllPhieuKetQua()) {
            if(pkq.getPhieuDangKy().getCuocThi().getId() == cuocThiId && pkq.getPhieuDangKy().getUser().getId() == userId) {
                return String.valueOf(pkq.getDiem());
            }
        }
        return " ";

    }

}