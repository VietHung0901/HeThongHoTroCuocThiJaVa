package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.QuyDinh;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.QuyDinhService;
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

@Controller
@RequestMapping("/quydinhs")
@RequiredArgsConstructor
public class QuyDinhController {

    private final QuyDinhService quyDinhService;

    @GetMapping
    public String showAllQuyDinhs(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("quydinhs", quyDinhService.getAllQuyDinhs(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", quyDinhService.getAllQuyDinhs(pageNo, pageSize, sortBy).size() / pageSize);
        return "quydinh/list";
    }

    @GetMapping("/add")
    public String addQuyDinhForm(
            @NotNull Model model) {
        model.addAttribute("quydinh", new QuyDinh());
        return "quydinh/add";
    }

    @PostMapping("/add")
    public String addQuyDinh(
            @Valid @ModelAttribute("quydinh") QuyDinh quyDinh,
            @NotNull BindingResult bindingResult,
            @RequestParam("image") MultipartFile imageFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "quydinh/add";
        }
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

    @GetMapping("/delete/{id}")
    public String deleteQuyDinh(@PathVariable long id) {
        quyDinhService.getQuyDinhById(id)
                .ifPresentOrElse(
                        quyDinh -> quyDinhService.deleteQuyDinhById(id),
                        () -> { throw new IllegalArgumentException("QuyDinh not found"); });
        return "redirect:/quydinhs";
    }

    @GetMapping("/edit/{id}")
    public String editQuyDinhForm(@NotNull Model model,
                                  @PathVariable long id) {
        var quyDinh = quyDinhService.getQuyDinhById(id);
        model.addAttribute("quydinh", quyDinh.orElseThrow(() -> new IllegalArgumentException("QuyDinh not found")));
        return "quydinh/edit";
    }

    @PostMapping("/edit")
    public String editQuyDinh(@Valid @ModelAttribute("quydinh") QuyDinh quyDinh,
                              @NotNull BindingResult bindingResult,
                              Model model,
                              @RequestParam("image") MultipartFile imageFile)
                               {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "quydinh/edit";
        }
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

    @GetMapping("/search")
    public String searchQuyDinh(
            @NotNull Model model,
            @RequestParam String keyword) {
        model.addAttribute("quydinhs", quyDinhService.searchQuyDinh(keyword));
        return "quydinh/list";
    }
    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }
}
