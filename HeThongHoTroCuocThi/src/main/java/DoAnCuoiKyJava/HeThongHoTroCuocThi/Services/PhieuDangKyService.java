package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PhieuDangKy;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IPhieuDangKyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhieuDangKyService {
    private final IPhieuDangKyRepository phieuDangKyRepository;

    public List<PhieuDangKy> getAllPhieuDangKys() {
        return phieuDangKyRepository.findAll();
    }

    public List<PhieuDangKy> getAllPhieuDangKystheoCuocThi(Long cuocThiId) {
        return phieuDangKyRepository.findByCuocThi_Id(cuocThiId);
    }

    public Optional<PhieuDangKy> getPhieuDangKyById(Long id) {
        return phieuDangKyRepository.findById(id);
    }
    public PhieuDangKy addPhieuDangKy(PhieuDangKy phieuDangKy) {
        phieuDangKy.setTrangThai(1);
        return phieuDangKyRepository.save(phieuDangKy);
    }

    public PhieuDangKy updatePhieuDangKy(PhieuDangKy updatedPhieuDangKy) {
        PhieuDangKy phieuDangKy = getPhieuDangKyById(updatedPhieuDangKy.getId())
                .orElseThrow(() -> new EntityNotFoundException("Truong not found with id"));
        phieuDangKy.setEmail(updatedPhieuDangKy.getEmail());
        phieuDangKy.setSdt(updatedPhieuDangKy.getSdt());
        return phieuDangKyRepository.save(phieuDangKy);
    }
}