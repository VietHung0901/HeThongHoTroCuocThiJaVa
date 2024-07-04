package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;



import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ILoaiTruongRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class LoaiTruongService {
    private final ILoaiTruongRepository loaiTruongRepository;

    public List<LoaiTruong> getAllLoaiTruongs() {
        return loaiTruongRepository.findAll();
    }

    public Optional<LoaiTruong> getLoaiTruongById(Long id) {
        return loaiTruongRepository.findById(id);
    }

    public void addLoaiTruong(LoaiTruong loaiTruong) {
        loaiTruongRepository.save(loaiTruong);
    }

    public void updateLoaiTruong(@NotNull LoaiTruong loaiTruong) {
        LoaiTruong existingLoaiTruong = loaiTruongRepository
                .findById(loaiTruong.getId())
                .orElse(null);
        Objects.requireNonNull(existingLoaiTruong)
                .setTenLoaiTruong(loaiTruong.getTenLoaiTruong());
        loaiTruongRepository.save(existingLoaiTruong);
    }

    @Transactional
    public void anHienLoaiTruong(Long id) {
        LoaiTruong loaiTruong = loaiTruongRepository.findById(id).orElse(null);
        if (loaiTruong != null) {
            if (loaiTruong.getTrangThai() != 0) {
                loaiTruong.setTrangThai(0);
            } else {
                loaiTruong.setTrangThai(1);
            }
            loaiTruongRepository.save(loaiTruong);
        }
    }
}
