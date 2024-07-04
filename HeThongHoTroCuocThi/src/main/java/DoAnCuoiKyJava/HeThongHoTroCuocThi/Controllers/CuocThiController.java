package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.CuocThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.MonThiService;
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
@RequestMapping("/CuocThis")
public class CuocThiController {
    private final CuocThiService cuocThiService;
    private final MonThiService monThiService;

    @GetMapping
    public String showAllCuocThi(@NotNull Model model) {
        model.addAttribute("cuocThis", cuocThiService.getAllCuocThis());
        return "CuocThi/list";
    }

    @GetMapping("/add")
    public String addCuocThiForm(@NotNull Model model) {
        model.addAttribute("cuocThi", new CuocThi());
        model.addAttribute("listMonThi",monThiService.getAllMonThisHien());
        return "CuocThi/add";
    }

    @PostMapping("/add")
    public String addCuocThi(
            @Valid @ModelAttribute("CuocThi") CuocThi cuocThi,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("listMonThi",monThiService.getAllMonThisHien());
            return "CuocThi/add";
        }
        cuocThiService.addCuocThi(cuocThi);
        return "redirect:/CuocThis";
    }

    @GetMapping("/edit/{id}")
    public String editCuocThi(@PathVariable Long id, Model model) {
        CuocThi cuocThi = cuocThiService.getCuocThiById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoaiTruong not found with id: " + id));
        model.addAttribute("cuocThi", cuocThi);
        model.addAttribute("listMonThi",monThiService.getAllMonThisHien());
        return "CuocThi/edit";
    }

    @PostMapping("/edit")
    public String updateCuocThi(@Valid @ModelAttribute("CuocThi") CuocThi cuocThi,
                               @NotNull BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("listMonThi",monThiService.getAllMonThisHien());
            return "CuocThi/edit";
        }
        cuocThiService.updateCuocThi(cuocThi);
        return "redirect:/CuocThis";
    }

    @GetMapping("/AnHien/{id}")
    public String toggleVisibility(@PathVariable Long id) {
        cuocThiService.AnHien(id);
        return "redirect:/CuocThis";
    }
}