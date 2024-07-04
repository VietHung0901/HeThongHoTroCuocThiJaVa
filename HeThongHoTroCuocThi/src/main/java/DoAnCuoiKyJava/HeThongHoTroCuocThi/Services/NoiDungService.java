package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;




import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.MonThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.INoiDungRepository;
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
public class NoiDungService {
    private final INoiDungRepository noiDungRepository;

    public List<NoiDung> getAllNoiDungs() {
        return noiDungRepository.findAll();
    }

    public Optional<NoiDung> getNoiDungById(Long id) {
        return noiDungRepository.findById(id);
    }

    public void addNoiDung(NoiDung noiDung) {
        noiDungRepository.save(noiDung);
    }

    public void updateNoiDung(@NotNull NoiDung noiDung) {
        NoiDung existingNoiDung = noiDungRepository.findById(noiDung.getId()).orElse(null);
        if (existingNoiDung != null) {
            existingNoiDung.setTenNoiDung(noiDung.getTenNoiDung());
            existingNoiDung.setImageUrl(noiDung.getImageUrl());
            noiDungRepository.save(existingNoiDung);
        }
    }

    @Transactional
    public void anHienMonThi(Long id) {
        NoiDung noiDung = noiDungRepository.findById(id).orElse(null);
        if (noiDung != null) {
            if (noiDung.getTrangThai() != 0) {
                noiDung.setTrangThai(0);
            } else {
                noiDung.setTrangThai(1);
            }
            noiDungRepository.save(noiDung);
        }
    }
}
