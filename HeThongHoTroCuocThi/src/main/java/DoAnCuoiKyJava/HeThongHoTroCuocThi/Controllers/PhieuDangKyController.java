package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PhieuDangKy;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.User;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.PhieuDangKyCreate;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.CuocThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuDangKyService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Viewmodels.UserGetVM;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/PhieuDangKys")
public class PhieuDangKyController {
    private final PhieuDangKyService phieuDangKyService;
    private final CuocThiService cuocThiService;
    private final UserService userService;

    @GetMapping("/cuocThi/id/{id}")
    public String showAllPhieuDangKyTheoCuocThi(@NotNull Model model, @PathVariable Long id) {
        model.addAttribute("phieuDangKys", phieuDangKyService.getAllPhieuDangKystheoCuocThi(id));
        return "PhieuDangKy/list";
    }

    @GetMapping("/add/id/{id}")
    public String addPhieuDangKyForm(@NotNull Model model, @PathVariable Long id) {
        PhieuDangKyCreate phieuDangKy = new PhieuDangKyCreate();
        CuocThi cuocThi = cuocThiService.getCuocThiById(id)
                .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: " + id));
        phieuDangKy.setCuocThi(cuocThi);
        model.addAttribute("phieuDangKy", phieuDangKy);
        model.addAttribute("cuocThi", cuocThi);
        return "PhieuDangKy/add";
    }

    @PostMapping("/add")
    public String addPhieuDangKy(
            @Valid @ModelAttribute("PhieuDangKy") PhieuDangKyCreate phieuDangKyCreate,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "PhieuDangKy/add";
        }

        PhieuDangKy phieuDangKy = new PhieuDangKy();
        phieuDangKy.setNgayDangKy(phieuDangKyCreate.getNgayDangKy());
        phieuDangKy.setCuocThi(phieuDangKyCreate.getCuocThi());
        phieuDangKy.setSdt(phieuDangKyCreate.getSdt());
        phieuDangKy.setEmail(phieuDangKyCreate.getEmail());
        phieuDangKy.setTruongId(phieuDangKyCreate.getTruongId());
        phieuDangKy.setNgayDangKy(LocalDateTime.now());

        User user = userService.findById(phieuDangKyCreate.getUserId());
        phieuDangKy.setUser(user);

        phieuDangKyService.addPhieuDangKy(phieuDangKy);
        return "redirect:/PhieuDangKys";
    }

    @GetMapping("/edit/{id}")
    public String editPhieuDangKy(@PathVariable Long id, Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoaiTruong not found with id: " + id));
        model.addAttribute("phieuDangKy", phieuDangKy);
        return "PhieuDangKy/edit";
    }

    @PostMapping("/edit")
    public String updatePhieuDangKy(@Valid @ModelAttribute("PhieuDangKy") PhieuDangKy phieuDangKy,
                               @NotNull BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "PhieuDangKy/edit";
        }
        phieuDangKyService.updatePhieuDangKy(phieuDangKy);
        return "redirect:/PhieuDangKys";
    }
}