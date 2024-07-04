package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.MonThi;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ICuocThiRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IMonThiRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.INoiDungRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IQuyDinhRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.CuocThiRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class CuocThiService {
    /*private final ICuocThiRepository cuocThiRepository;
    private final LoaiTruongService loaiTruongService;
    private final MonThiService monThiService;*/

    private final ICuocThiRepository cuocThiRepository;
    private final IMonThiRepository monThiRepository;
    private final INoiDungRepository noiDungRepository;
    private final IQuyDinhRepository quyDinhRepository;

    public List<CuocThi> getAllCuocThis() {
        return cuocThiRepository.findAll();
    }

    public Optional<CuocThi> getCuocThiById(Long id) {
        return cuocThiRepository.findById(id);
    }

    public void addCuocThi(CuocThiRequest cuocThiRequest) {
        CuocThi cuocThi = mapToCuocThi(cuocThiRequest);
        cuocThiRepository.save(cuocThi);
    }

    public void updateCuocThi(Long id, CuocThiRequest cuocThiRequest) {
        Optional<CuocThi> cuocThiOpt = cuocThiRepository.findById(id);
        if (cuocThiOpt.isPresent()) {
            CuocThi cuocThi = mapToCuocThi(cuocThiRequest);
            cuocThi.setId(id); // Set the ID to ensure it's an update
            cuocThiRepository.save(cuocThi);
        }
    }

    public void anHienCuocThi(Long id) {
        Optional<CuocThi> cuocThiOpt = cuocThiRepository.findById(id);
        cuocThiOpt.ifPresent(cuocThi -> {
            cuocThi.setTrangThai(cuocThi.getTrangThai() == 1 ? 0 : 1);
            cuocThiRepository.save(cuocThi);
        });
    }

    private CuocThi mapToCuocThi(CuocThiRequest cuocThiRequest) {
        CuocThi cuocThi = new CuocThi();
        cuocThi.setTenCuocThi(cuocThiRequest.getTenCuocThi());
        cuocThi.setNgayThi(Date.from(cuocThiRequest.getNgayThi().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        cuocThi.setSoLuongThiSinh(cuocThiRequest.getSoLuong());
        cuocThi.setDiaDiemThi(cuocThiRequest.getDiaDiemThi());
        cuocThi.setMonThi(cuocThiRequest.getMonThi());
        cuocThi.setQuyDinh(cuocThiRequest.getQuyDinh());
        cuocThi.setNoiDung(cuocThiRequest.getNoiDung());
        return cuocThi;
    }

    public CuocThiRequest mapToCuocThiRequest(CuocThi cuocThi) {
        return CuocThiRequest.builder()
                .Id(cuocThi.getId())
                .tenCuocThi(cuocThi.getTenCuocThi())
                .ngayThi(cuocThi.getNgayThi().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .soLuong(cuocThi.getSoLuongThiSinh())
                .diaDiemThi(cuocThi.getDiaDiemThi())
                .monThi(cuocThi.getMonThi())
                .quyDinh(cuocThi.getQuyDinh())
                .noiDung(cuocThi.getNoiDung())
                .build();
    }

}
