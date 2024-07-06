package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.ChiTietNoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IChiTietNoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiTietNoiDungService {
    private final IChiTietNoiDungRepository CTNDRepository;

    public List<ChiTietNoiDung> getAllCTND() {
        return CTNDRepository.findAll();
    }

    public ChiTietNoiDung addChiTietNoiDung(ChiTietNoiDung CTND) {
        return CTNDRepository.save(CTND);
    }

    //Lấy các Chi tiết nội dung theo cuộc thi
    public List<NoiDung> getAllNoiDungByCuocThi(CuocThi cuocThi) {
        List<ChiTietNoiDung> chiTietNoiDungs = getAllCTND();
        List<NoiDung> noiDungs = new ArrayList<>();
        for (ChiTietNoiDung chiTietNoiDung : chiTietNoiDungs) {
            if(chiTietNoiDung.getCuocThi() == cuocThi) {
                noiDungs.add(chiTietNoiDung.getNoiDung());
                //Xóa, khi sửa sẽ thêm vào lại
                CTNDRepository.delete(chiTietNoiDung);
            }
        }
        return noiDungs;
    }
}
