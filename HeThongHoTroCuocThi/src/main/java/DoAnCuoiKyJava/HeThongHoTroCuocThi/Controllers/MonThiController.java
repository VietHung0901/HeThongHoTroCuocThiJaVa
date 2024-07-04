package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.MonThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.MonThiService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/MonThis")
@RequiredArgsConstructor
public class MonThiController {
    private final MonThiService monThiService;

    @GetMapping
    public String showAllMonThi(@NotNull Model model) {
        model.addAttribute("monThis", monThiService.getAllMonThis());
        return "MonThi/list";
    }

    @GetMapping("/add")
    public String addMonThiForm(@NotNull Model model) {
        model.addAttribute("monThis", new MonThi());
        return "MonThi/add";
    }

    @PostMapping("/add")
    public String addMonThi(
            @Valid @ModelAttribute("") MonThi monThi,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "MonThi/add";
        }
        monThiService.addMonThi(monThi);
        return "redirect:/MonThis";
    }

    @GetMapping("/edit/{id}")
    public String editLoaiTruong(@PathVariable Long id, Model model) {
        MonThi monThi = monThiService.getMonThiById(id)
                .orElseThrow(() -> new EntityNotFoundException("MonThi not found with id: " + id));
        model.addAttribute("monThi", monThi);
        return "MonThi/edit";
    }

    @PostMapping("/edit")
    public String updateLoaiTruong(@Valid @ModelAttribute("MonThi") MonThi monthi,
                                   @NotNull BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "MonThi/edit";
        }
        monThiService.updateMonThi(monthi);
        return "redirect:/MonThis";
    }

    @GetMapping("/AnHien/{id}")
    public String toggleVisibility(@PathVariable Long id) {
        monThiService.AnHien(id);
        return "redirect:/MonThis";
    }
}
