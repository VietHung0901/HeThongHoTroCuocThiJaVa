package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ICuocThiRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.CuocThiCreateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CuocThiService {
    private final ICuocThiRepository cuocThiRepository;

    public List<CuocThi> getAllCuocThis() {
        return cuocThiRepository.findAll();
    }

//    public List<CuocThi> getAllCuocThisHien() {
//        return cuocThiRepository.findByTrangThai(1);
//    }


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
    public List<CuocThi> searchByNgayThi(LocalDate startDate, LocalDate endDate) {
        return cuocThiRepository.findByNgayThiBetweenOrderByNgayThi(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
    public List<CuocThi> searchByDiaDiemThi(String diaDiemThi) {
        return cuocThiRepository.findByDiaDiemThiContainingIgnoreCase(diaDiemThi);
    }
    public List<CuocThi> searchCuocThi(LocalDate startDate, LocalDate endDate, String diaDiemThi) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        if (diaDiemThi == null || diaDiemThi.isEmpty()) {
            return cuocThiRepository.findByNgayThiBetweenOrderByNgayThi(startDateTime, endDateTime);
        } else {
            return cuocThiRepository.findByNgayThiBetweenAndDiaDiemThiContainingIgnoreCaseOrderByNgayThi(startDateTime, endDateTime, diaDiemThi);
        }
    }

}