package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;




import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.QuyDinh;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.QuyDinhService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/quydinhs")
@RequiredArgsConstructor
public class QuyDinhController {
    private final QuyDinhService quyDinhService;

    @GetMapping("")
    public String showAllQuyDinh(@NonNull Model model) {
        model.addAttribute("quydinhs", quyDinhService.getAllQuyDinhs());
        return "QuyDinh/list";
    }

    @GetMapping("/add")
    public String addQuyDinhForm(@NonNull Model model) {
        model.addAttribute("quyDinh", new QuyDinh());
        return "QuyDinh/add";
    }

    @PostMapping("/add")
    public String addQuyDinh(@ModelAttribute("quyDinh") QuyDinh quyDinh, @RequestParam("image") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                quyDinh.setImageUrl("/images/" + imageName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        quyDinhService.addQuyDinh(quyDinh);
        return "redirect:/quydinhs";
    }

    @GetMapping("/edit/{id}")
    public String editQuyDinhForm(@NonNull Model model, @PathVariable long id)
    {
        var quydinh = quyDinhService.getQuyDinhById(id).orElse(null);
        model.addAttribute("quyDinh", quydinh != null ? quydinh : new QuyDinh());
        return "QuyDinh/edit";
    }

    @PostMapping("/edit")
    public String editQuyDinh(@ModelAttribute("quyDinh") QuyDinh quyDinh, @RequestParam("image") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                quyDinh.setImageUrl("/images/" + imageName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        quyDinhService.updateQuyDinh(quyDinh);
        return "redirect:/quydinhs";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }

    @PostMapping("/anhien/{id}")
    public String anHienMonThi(@PathVariable("id") Long id) {
        quyDinhService.anHienMonThi(id);
        return "redirect:/quydinhs";
    }
}
