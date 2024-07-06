package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.NoiDungService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/noidungs")
@RequiredArgsConstructor
public class NoiDungController {

    private final NoiDungService noiDungService;

    @GetMapping
    public String showAllNoiDungs(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("noidungs", noiDungService.getAllNoiDungs(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", noiDungService.getAllNoiDungs(pageNo, pageSize, sortBy).size() / pageSize);
        return "noidung/list";
    }

    @GetMapping("/add")
    public String addNoiDungForm(
            @NotNull Model model) {
        model.addAttribute("noidung", new NoiDung());
        return "noidung/add";
    }

    @PostMapping("/add")
    public String addNoiDung(
            @Valid @ModelAttribute("noidung") NoiDung noiDung,
            @NotNull BindingResult bindingResult,
            @RequestParam("image") MultipartFile imageFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "noidung/add";
        }
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

    @GetMapping("/delete/{id}")
    public String deleteNoiDung(@PathVariable long id) {
        noiDungService.getNoiDungById(id)
                .ifPresentOrElse(
                        noiDung -> noiDungService.deleteNoiDungById(id),
                        () -> { throw new IllegalArgumentException("NoiDung not found"); });
        return "redirect:/noidungs";
    }

    @GetMapping("/edit/{id}")
    public String editNoiDungForm(@NotNull Model model,
                                  @PathVariable long id) {
        var noiDung = noiDungService.getNoiDungById(id);
        model.addAttribute("noidung", noiDung.orElseThrow(() -> new IllegalArgumentException("NoiDung not found")));
        return "noidung/edit";
    }

    @PostMapping("/edit")
    public String editNoiDung(@Valid @ModelAttribute("noidung") NoiDung noiDung,
                              @NotNull BindingResult bindingResult,
                              Model model,
                              @RequestParam("image") MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "noidung/edit";
        }
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

    @GetMapping("/search")
    public String searchNoiDung(
            @NotNull Model model,
            @RequestParam String keyword) {
        model.addAttribute("noidungs", noiDungService.searchNoiDung(keyword));
        return "noidung/list";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }
}
