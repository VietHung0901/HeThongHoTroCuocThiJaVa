package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ICuocThiRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ITruongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuocThiService {
    private final ICuocThiRepository cuocThiRepository;

    public List<CuocThi> getAllCuocThis() {
        return cuocThiRepository.findAll();
    }

    public List<CuocThi> getAllCuocThisHien() {
        return cuocThiRepository.findByTrangThai(1);
    }

    public Optional<CuocThi> getCuocThiById(Long id) {
        return cuocThiRepository.findById(id);
    }
    public CuocThi addCuocThi(CuocThi cuocThi) {
        cuocThi.setTrangThai(1);
        return cuocThiRepository.save(cuocThi);
    }

    public CuocThi updateCuocThi(CuocThi updatedCuocThi) {
        CuocThi cuocThi = getCuocThiById(updatedCuocThi.getId())
                .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id"));
        cuocThi.setTenCuocThi(updatedCuocThi.getTenCuocThi());
        cuocThi.setNgayThi(updatedCuocThi.getNgayThi());
        cuocThi.setSoLuongThiSinh(updatedCuocThi.getSoLuongThiSinh());
        cuocThi.setDiaDiemThi(updatedCuocThi.getDiaDiemThi());
        cuocThi.setMonThi(updatedCuocThi.getMonThi());
        return cuocThiRepository.save(cuocThi);
    }

    public void AnHien(Long id) {
        CuocThi cuocThi = cuocThiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: " + id));

        cuocThi.setTrangThai(cuocThi.getTrangThai() == 0 ? 1 : 0);
        cuocThiRepository.save(cuocThi);
    }
}