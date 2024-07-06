package DoAnCuoiKyJava.HeThongHoTroCuocThi.Services;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.INoiDungRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.NoiDungCreateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class NoiDungService {
    private final INoiDungRepository noiDungRepository;

    public List<NoiDung> getAllNoiDungs() {
        return noiDungRepository.findAll();
    }

    public List<NoiDung> getAllNoiDungsHien() {
        return noiDungRepository.findByTrangThai(1);
    }

    public Optional<NoiDung> getNoiDungById(Long id) {
        return noiDungRepository.findById(id);
    }

    public void addNoiDung(NoiDung noiDung) {
        noiDung.setTrangThai(1);
        noiDungRepository.save(noiDung);
    }

    public void updateNoiDung(@NotNull NoiDung noiDung) {
        NoiDung existingNoiDung = noiDungRepository.findById(noiDung.getId()).orElse(null);
        if (existingNoiDung != null) {
            existingNoiDung.setTenNoiDung(noiDung.getTenNoiDung());
            existingNoiDung.setMoTaNoiDung(noiDung.getMoTaNoiDung());
            existingNoiDung.setImageUrl(noiDung.getImageUrl());
            noiDungRepository.save(existingNoiDung);
        }
    }

    public void AnHien(Long id) {
        NoiDung noiDung = noiDungRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));

        noiDung.setTrangThai(noiDung.getTrangThai() == 0 ? 1 : 0);
        noiDungRepository.save(noiDung);
    }

    //hàm chuyển từ NoiDungCreateRequest sang NoiDung
    public NoiDung mapToNoiDung(NoiDungCreateRequest noiDungCreateRequest) {
        NoiDung noiDung = new NoiDung();
        noiDung.setId(noiDungCreateRequest.getId());
        noiDung.setTenNoiDung(noiDungCreateRequest.getTenNoiDung());
        noiDung.setMoTaNoiDung(noiDungCreateRequest.getMoTaNoiDung());
        if (!noiDungCreateRequest.getImageUrl().isEmpty()) {
            String image = saveImage(noiDungCreateRequest.getImageUrl());
            noiDung.setImageUrl(image);
        }
        return noiDung;
    }

    //Hàm lưu ảnh vào local dưới dạng images/imageName
    public String saveImage(MultipartFile file) {
        // Lấy tên file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Đường dẫn lưu file
        String uploadDir = "src/main/resources/static/images/";
        Path filePath = Paths.get(uploadDir, fileName);

        try {
            // Lưu file vào thư mục
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save file: " + fileName, e);
        }

        // Trả về đường dẫn của file đã lưu
        return "/images/" + fileName;
    }
}
