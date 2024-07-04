package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.NoiDungService;
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
@RequestMapping("/noidungs")
@RequiredArgsConstructor
public class NoiDungController {

    private final NoiDungService noiDungService;

    @GetMapping("")
    public String showAllNoiDung(@NonNull Model model) {
        model.addAttribute("noidungs", noiDungService.getAllNoiDungs());
        return "NoiDung/list";
    }

    @GetMapping("/add")
    public String addNoiDungForm(@NonNull Model model) {
        model.addAttribute("noiDung", new NoiDung());
        return "NoiDung/add";
    }

    @PostMapping("/add")
    public String addNoiDung(@ModelAttribute("noiDung") NoiDung noiDung, @RequestParam("image") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                noiDung.setImageUrl("/images/" + imageName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        noiDungService.addNoiDung(noiDung);
        return "redirect:/noidungs";
    }

    @GetMapping("/edit/{id}")
    public String editNoiDungForm(@NonNull Model model, @PathVariable long id)
    {
        var noidung = noiDungService.getNoiDungById(id).orElse(null);
        model.addAttribute("noiDung", noidung != null ? noidung : new NoiDung());
        return "NoiDung/edit";
    }

    @PostMapping("/edit")
    public String editNoiDung(@ModelAttribute("monThi") NoiDung noiDung, @RequestParam("image") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                noiDung.setImageUrl("/images/" + imageName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        noiDungService.updateNoiDung(noiDung);
        return "redirect:/noidungs";
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
        noiDungService.anHienMonThi(id);
        return "redirect:/noidungs";
    }
}
