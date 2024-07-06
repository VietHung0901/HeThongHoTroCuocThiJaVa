package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.QuyDinh;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IQuyDinhRepository;
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
public class QuyDinhService {

    private final IQuyDinhRepository quyDinhRepository;

    public List<QuyDinh> getAllQuyDinhs(Integer pageNo,
                                        Integer pageSize,
                                        String sortBy) {
        return quyDinhRepository.findAllQuyDinhs(pageNo, pageSize, sortBy);
    }

    public Optional<QuyDinh> getQuyDinhById(Long id) {
        return quyDinhRepository.findById(id);
    }

    public void addQuyDinh(QuyDinh quyDinh) {
        quyDinhRepository.save(quyDinh);
    }

    public void updateQuyDinh(@NotNull QuyDinh quyDinh) {
        QuyDinh existingQuyDinh = quyDinhRepository.findById(quyDinh.getId())
                .orElse(null);
        Objects.requireNonNull(existingQuyDinh).setTenQuyDinh(quyDinh.getTenQuyDinh());
        existingQuyDinh.setImageUrl(quyDinh.getImageUrl());
        existingQuyDinh.setTrangThai(quyDinh.getTrangThai());
        existingQuyDinh.setChiTietQuyDinhs(quyDinh.getChiTietQuyDinhs());
        quyDinhRepository.save(existingQuyDinh);
    }

    public void deleteQuyDinhById(Long id) {
        quyDinhRepository.deleteById(id);
    }

    public List<QuyDinh> searchQuyDinh(String keyword) {
        return quyDinhRepository.searchQuyDinh(keyword);
    }
}
