package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PhieuDangKy;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.PhieuKetQua;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuDangKyService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuKetQuaService;
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
@RequestMapping("/PhieuKetQuas")
@RequiredArgsConstructor
public class PhieuKetQuaController {
    private final PhieuDangKyService phieuDangKyService;
    private final PhieuKetQuaService phieuKetQuaService;

    @GetMapping("/add/pdkId/{id}")
    public String addPhieuKetQuaForm(@PathVariable Long id, @NotNull Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id).orElseThrow(() -> new EntityNotFoundException(""));
        PhieuKetQua phieuKetQua = new PhieuKetQua();
        phieuKetQua.setPhieuDangKy(phieuDangKy);
        model.addAttribute("phieuKetQua", phieuKetQua);
        return "PhieuKetQua/add";
    }

    @PostMapping("/add")
    public String addPhieuKetQus(
            @Valid @ModelAttribute("PhieuKetQua") PhieuKetQua phieuKetQua,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "PhieuKetQua/add";
        }
        phieuKetQuaService.addPhieuKetQua(phieuKetQua);
        Long cuocThiId = phieuKetQua.getPhieuDangKy().getCuocThi().getId();
        return "redirect:/PhieuDangKys/cuocThi/id/" + cuocThiId;
    }
}
