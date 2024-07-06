package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.*;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ICuocThiRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.CuocThiCreateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CuocThiService {
    private final ICuocThiRepository cuocThiRepository;
    private final ChiTietNoiDungService CTNDService;
    private final ChiTietQuyDinhService CTQDService;
    private final NoiDungService noiDungService;
    private final QuyDinhService quyDinhService;


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

    private CuocThi mapToCuocThi(CuocThiCreateRequest cuocThiRequest) {
        CuocThi cuocThi = new CuocThi();
        cuocThi.setTenCuocThi(cuocThiRequest.getTenCuocThi());
        cuocThi.setNgayThi(cuocThiRequest.getNgayThi());
        cuocThi.setSoLuongThiSinh(cuocThiRequest.getSoLuong());
        cuocThi.setDiaDiemThi(cuocThiRequest.getDiaDiemThi());
        cuocThi.setMonThi(cuocThiRequest.getMonThi());
        return cuocThi;
    }
}