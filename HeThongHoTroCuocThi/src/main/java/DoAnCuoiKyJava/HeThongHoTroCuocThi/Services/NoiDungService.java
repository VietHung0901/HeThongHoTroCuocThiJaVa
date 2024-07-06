package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

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

    public List<NoiDung> getAllNoiDungs(Integer pageNo,
                                        Integer pageSize,
                                        String sortBy) {
        return noiDungRepository.findAllNoiDungs(pageNo, pageSize, sortBy);
    }

    public Optional<NoiDung> getNoiDungById(Long id) {
        return noiDungRepository.findById(id);
    }

    public void addNoiDung(NoiDung noiDung) {
        noiDungRepository.save(noiDung);
    }

    public void updateNoiDung(@NotNull NoiDung noiDung) {
        NoiDung existingNoiDung = noiDungRepository.findById(noiDung.getId())
                .orElse(null);
        Objects.requireNonNull(existingNoiDung).setTenNoiDung(noiDung.getTenNoiDung());
        existingNoiDung.setImageUrl(noiDung.getImageUrl());
        existingNoiDung.setTrangThai(noiDung.getTrangThai());
        existingNoiDung.setChiTietNoiDungs(noiDung.getChiTietNoiDungs());
        noiDungRepository.save(existingNoiDung);
    }

    public void deleteNoiDungById(Long id) {
        noiDungRepository.deleteById(id);
    }

    public List<NoiDung> searchNoiDung(String keyword) {
        return noiDungRepository.searchNoiDung(keyword);
    }
}
