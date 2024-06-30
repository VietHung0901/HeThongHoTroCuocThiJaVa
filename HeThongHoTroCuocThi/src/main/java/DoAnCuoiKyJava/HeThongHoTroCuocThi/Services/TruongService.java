package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ITruongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruongService {
    private final ITruongRepository truongRepository;

    @Autowired
    public TruongService(ITruongRepository truongRepository) {
        this.truongRepository = truongRepository;
    }

    public List<Truong> getAllTruong() {
        return truongRepository.findAll();
    }

    public Optional<Truong> getTruongById(Long id) {
        return truongRepository.findById(id);
    }

    public Truong createTruong(Truong truong) {
        truong.setTrangThai(1);
        return truongRepository.save(truong);
    }

    public void deleteTruong(Long id) {
        truongRepository.deleteById(id);
    }
}