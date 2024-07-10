package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.*;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.PhieuKetQuaRequest;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.CuocThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuDangKyService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuKetQuaService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Viewmodels.PhieuKetQuaGetVm;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Viewmodels.UserGetVM;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/PhieuKetQuas")
@RequiredArgsConstructor
public class PhieuKetQuaController {
    private final PhieuDangKyService phieuDangKyService;
    private final PhieuKetQuaService phieuKetQuaService;
    private final CuocThiService cuocThiService;
    private final UserService userService;

    @GetMapping("/cuocThiId/{id}")
    public String showAllPhieuKetQuaByCuocThi(@PathVariable Long id, @NotNull Model model) {
        CuocThi cuocThi = cuocThiService.getCuocThiById(id).orElseThrow(() -> new EntityNotFoundException(""));
        List<PhieuKetQua> phieuKetQuas = phieuKetQuaService.getAllPhieuKetQuastheoCuocThi(cuocThi);
        model.addAttribute("phieuKetQuas", phieuKetQuas);
        return "PhieuKetQua/list";
    }

    @GetMapping("/add/pkq/pdkId/{id}")
    public String addPhieuKetQuaForm(@PathVariable Long id, @NotNull Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id).orElseThrow(() -> new EntityNotFoundException(""));
        PhieuKetQua phieuKetQua = new PhieuKetQua();
        phieuKetQua.setPhieuDangKy(phieuDangKy);
        model.addAttribute("phieuKetQua", phieuKetQua);
        return "PhieuKetQua/add";
    }

    @PostMapping("/add")
    public String addPhieuKetQua(
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

    @GetMapping("/edit/pkq/pdkId/{id}")
    public String editPhieuKetQuaForm(@PathVariable Long id, @NotNull Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id).orElseThrow(() -> new EntityNotFoundException(""));
        PhieuKetQua phieuKetQua = phieuKetQuaService.getPhieuKetQuaByPhieuDangKy(phieuDangKy);
        model.addAttribute("phieuKetQua", phieuKetQua);
        return "PhieuKetQua/edit";
    }

    @PostMapping("/edit")
    public String editPhieuKetQua(
            @Valid @ModelAttribute("PhieuKetQua") PhieuKetQua phieuKetQua,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "PhieuKetQua/edit";
        }
        phieuKetQuaService.updatePhieuKetQua(phieuKetQua);
        Long cuocThiId = phieuKetQua.getPhieuDangKy().getCuocThi().getId();
        return "redirect:/PhieuDangKys/cuocThi/id/" + cuocThiId;
    }

    @GetMapping("/thongke/cuocThiId/{id}")
    public String thongKeDiem(@PathVariable Long id, Model model) {
        CuocThi cuocThi = cuocThiService.getCuocThiById(id).orElseThrow(() -> new EntityNotFoundException(""));
        List<PhieuKetQua> phieuKetQuas = phieuKetQuaService.getAllPhieuKetQuastheoCuocThi(cuocThi);
        List<Integer> diems = phieuKetQuas.stream().map(phieuKetQua -> phieuKetQua.getDiem()).collect(Collectors.toList());
        model.addAttribute("diems", diems);
        return "PhieuKetQua/thongke";
    }

//    @GetMapping("/search")
//    public String showSearchForm(Principal principal, Model model) {
//        User user = userService.findByUsername(principal.getName());
//        List<PhieuKetQua> listPKQ = phieuKetQuaService.getPkqByUser(user);
//        if(listPKQ != null)
//        {
//            model.addAttribute("phieuKetQuas", listPKQ);
//        }
//        else{
//            model.addAttribute("errorMessage", "Chưa có kết quả cuộc thi!");
//        }
//        return "PhieuKetQua/search";
//    }

        @GetMapping("/search")
    public String showSearchForm() {
        return "PhieuKetQua/search";
    }

    //API lấy thông tin phiếu kết quả
    @GetMapping("/search/{pdkId}")
    public ResponseEntity<PhieuKetQuaGetVm> getPhieuKetQuaByPdkIdAndUserId (@PathVariable Long pdkId) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(pdkId).orElseThrow(() -> new EntityNotFoundException(""));
        return ResponseEntity.ok(phieuKetQuaService.findByPhieuDangKy(phieuDangKy)
                .map(PhieuKetQuaGetVm::from)
                .orElse(null));
    }
}
