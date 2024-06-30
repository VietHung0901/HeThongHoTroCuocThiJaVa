package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ILoaiTruongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiTruongService {
    private final ILoaiTruongRepository loaiTruongRepository;

    public LoaiTruongService(ILoaiTruongRepository loaiTruongRepository) {
        this.loaiTruongRepository = loaiTruongRepository;
    }

    public LoaiTruong createLoaiTruong(LoaiTruong loaiTruong) {
        loaiTruong.setTrangThai(1); // Mặc định là trạng thái kích hoạt
        return loaiTruongRepository.save(loaiTruong);
    }

    public LoaiTruong getLoaiTruongByTen(String tenLoaiTruong) {
        return loaiTruongRepository.findByTenLoaiTruong(tenLoaiTruong)
                .orElseThrow(() -> new RuntimeException("Loại trường không tồn tại"));
    }

    public List<LoaiTruong> getAllLoaiTruong() {
        return loaiTruongRepository.findAll();
    }

    public LoaiTruong updateLoaiTruong(LoaiTruong loaiTruong) {
        return loaiTruongRepository.save(loaiTruong);
    }

    public void deleteLoaiTruong(Long id) {
        LoaiTruong loaiTruong = loaiTruongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loại trường không tồn tại"));
        loaiTruong.setTrangThai(0); // Đổi trạng thái thành 0 (vô hiệu hóa)
        loaiTruongRepository.save(loaiTruong);
    }
}
