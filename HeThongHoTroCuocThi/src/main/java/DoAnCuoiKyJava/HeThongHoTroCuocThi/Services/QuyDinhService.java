package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.QuyDinh;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IQuyDinhRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class QuyDinhService {

    private final IQuyDinhRepository quyDinhRepository;

    public List<QuyDinh> getAllQuyDinhs() {
        return quyDinhRepository.findAll();
    }

    public List<QuyDinh> getAllQuyDinhsHien() {
        return quyDinhRepository.findByTrangThai(1);
    }

    public Optional<QuyDinh> getQuyDinhById(Long id) {
        return quyDinhRepository.findById(id);
    }

    public void addQuyDinh(QuyDinh quyDinh) {
        quyDinhRepository.save(quyDinh);
    }

    public void updateQuyDinh(@NotNull QuyDinh quyDinh) {
        QuyDinh existingQuyDinh = quyDinhRepository.findById(quyDinh.getId()).orElse(null);
        if (existingQuyDinh != null) {
            existingQuyDinh.setTenQuyDinh(quyDinh.getTenQuyDinh());
            existingQuyDinh.setImageUrl(quyDinh.getImageUrl());
            quyDinhRepository.save(existingQuyDinh);
        }
    }

    @Transactional
    public void anHienMonThi(Long id) {
        QuyDinh quyDinh = quyDinhRepository.findById(id).orElse(null);
        if (quyDinh != null) {
            if (quyDinh.getTrangThai() != 0) {
                quyDinh.setTrangThai(0);
            } else {
                quyDinh.setTrangThai(1);
            }
            quyDinhRepository.save(quyDinh);
        }
    }
}
