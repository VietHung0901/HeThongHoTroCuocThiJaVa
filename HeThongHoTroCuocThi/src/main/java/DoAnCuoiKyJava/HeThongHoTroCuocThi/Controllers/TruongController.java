package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.LoaiTruongService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.TruongService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Truongs")
public class TruongController {
    private final TruongService truongService;
    private final LoaiTruongService loaiTruongService;

    @GetMapping
    public String showAllTruong(@NotNull Model model) {
        model.addAttribute("truongs", truongService.getAllTruongs());
        return "Truong/list";
    }

    @GetMapping("/add")
    public String addTruongForm(@NotNull Model model) {
        model.addAttribute("truong", new Truong());
        model.addAttribute("listLoaiTruong",loaiTruongService.getAllLoaiTruongsHien());
        return "Truong/add";
    }

    @PostMapping("/add")
    public String addTruong(
            @Valid @ModelAttribute("truong") Truong truong,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "Truong/add";
        }

        if(truong.getLoaiTruong() == null)
        {
            model.addAttribute("errorMessage", "Chọn loại trường cho trường.");
            model.addAttribute("listLoaiTruong",loaiTruongService.getAllLoaiTruongsHien());
            return "Truong/add";
        }
        truongService.addTruong(truong);
        return "redirect:/Truongs";
    }

    @GetMapping("/edit/{id}")
    public String editTruong(@PathVariable Long id, Model model) {
        Truong truong = truongService.getTruongById(id)
                .orElseThrow(() -> new EntityNotFoundException("Truong not found with id: " + id));
        model.addAttribute("truong", truong);
        model.addAttribute("listLoaiTruong",loaiTruongService.getAllLoaiTruongsHien());
        return "Truong/edit";
    }

    @PostMapping("/edit")
    public String updateTruong(@Valid @ModelAttribute("truong") Truong truong,
                                   @NotNull BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("listLoaiTruong",loaiTruongService.getAllLoaiTruongsHien());
            return "Truong/edit";
        }
        truongService.updateTruong(truong);
        return "redirect:/Truongs";
    }

    @GetMapping("/AnHien/{id}")
    public String toggleVisibility(@PathVariable Long id) {
        truongService.AnHien(id);
        return "redirect:/Truongs";
    }
}