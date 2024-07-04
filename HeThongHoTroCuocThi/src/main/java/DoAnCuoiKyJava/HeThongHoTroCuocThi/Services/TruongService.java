package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ITruongRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TruongService {
    private final ITruongRepository truongRepository;
    private final LoaiTruongService loaiTruongService;

    public List<Truong> getAllTruongs() {
        return truongRepository.findAll();
    }

    public Optional<Truong> getTruongById(Long id) {
        return truongRepository.findById(id);
    }

    public void addTruong(Truong truong) {
        Optional<LoaiTruong> loaiTruong = loaiTruongService.getLoaiTruongById(truong.getLoaiTruong().getId());
        loaiTruong.ifPresent(truong::setLoaiTruong);
        truongRepository.save(truong);
    }


    public void updateTruong(@NotNull Truong truong) {
        Truong existingTruong = truongRepository.findById(truong.getId()).orElse(null);
        if (existingTruong != null) {
            existingTruong.setTenTruong(truong.getTenTruong());
            Optional<LoaiTruong> loaiTruong = loaiTruongService.getLoaiTruongById(truong.getLoaiTruong().getId());
            loaiTruong.ifPresent(existingTruong::setLoaiTruong);
            truongRepository.save(existingTruong);
        }
    }

    @Transactional
    public void anHienTruong(Long id) {
        Truong truong = truongRepository.findById(id).orElse(null);
        if (truong != null) {
            if (truong.getTrangThai() != 0) {
                truong.setTrangThai(0);
            } else {
                truong.setTrangThai(1);
            }
            truongRepository.save(truong);
        }
    }
}