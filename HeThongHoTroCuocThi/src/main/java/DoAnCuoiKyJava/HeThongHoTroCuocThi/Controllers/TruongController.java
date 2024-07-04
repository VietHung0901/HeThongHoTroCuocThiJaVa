package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.LoaiTruongService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.TruongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/truongs")
@RequiredArgsConstructor
public class TruongController {
    private final TruongService truongService;
    private final LoaiTruongService loaiTruongService;

    @GetMapping
    public String listTruongs(Model model) {
        model.addAttribute("truongs", truongService.getAllTruongs());
        model.addAttribute("loaitruongs", loaiTruongService.getAllLoaiTruongs());
        return "Truong/list";
    }

    @GetMapping("/add")
    public String addTruongForm(Model model) {
        model.addAttribute("truong", new Truong());
        model.addAttribute("allLoaiTruongs", loaiTruongService.getAllLoaiTruongs());
        return "Truong/add";
    }

    @PostMapping("/add")
    public String addTruong(@Valid @ModelAttribute("truong") Truong truong,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("allLoaiTruongs", loaiTruongService.getAllLoaiTruongs());
            return "Truong/add";
        }

        truongService.addTruong(truong);
        return "redirect:/truongs";
    }

    @GetMapping("/edit/{id}")
    public String editTruongForm(@PathVariable Long id, Model model) {
        Optional<Truong> truong = truongService.getTruongById(id);
        if (truong.isPresent()) {
            model.addAttribute("truong", truong.get());
            model.addAttribute("allLoaiTruongs", loaiTruongService.getAllLoaiTruongs());
            return "Truong/edit";
        }
        return "redirect:/truongs";
    }

    @PostMapping("/edit/{id}")
    public String updateTruong(@PathVariable Long id, @Valid @ModelAttribute("truong") Truong truong,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("allLoaiTruongs", loaiTruongService.getAllLoaiTruongs());
            return "Truong/edit";
        }

        truong.setId(id); // Ensure the correct ID is set
        truongService.updateTruong(truong);
        return "redirect:/truongs";
    }

    @PostMapping("/anhien/{id}")
    public String anHienTruong(@PathVariable Long id) {
        truongService.anHienTruong(id);
        return "redirect:/truongs";
    }

}
