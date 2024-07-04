package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.MonThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IMonThiRepository;
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
public class MonThiService {

    private final IMonThiRepository monThiRepository;
    
    public List<MonThi> getAllMonThis() {
        return monThiRepository.findAll();
    }
    
    public Optional<MonThi> getMonThiById(Long id) {
        return monThiRepository.findById(id);
    }

    public void addMonThi(MonThi monThi) {
        monThiRepository.save(monThi);
    }

    public void updateMonThi(@NotNull MonThi monThi) {
        MonThi existingMonThi = monThiRepository
                .findById(monThi.getId())
                .orElse(null);
        Objects.requireNonNull(existingMonThi)
                .setTenMonThi(monThi.getTenMonThi());
        monThiRepository.save(existingMonThi);
    }

    @Transactional
    public void anHienMonThi(Long id) {
        MonThi monThi = monThiRepository.findById(id).orElse(null);
        if (monThi != null) {
            if (monThi.getTrangThai() != 0) {
                monThi.setTrangThai(0);
            } else {
                monThi.setTrangThai(1);
            }
            monThiRepository.save(monThi);
        }
    }
}
