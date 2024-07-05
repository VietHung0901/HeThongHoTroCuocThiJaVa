package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.*;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/CuocThis")
public class CuocThiController {
    private final CuocThiService cuocThiService;
    private final MonThiService monThiService;
    private final NoiDungService noiDungService;
    private final QuyDinhService quyDinhService;
    private final ChiTietNoiDungService CTNDService;
    private final ChiTietQuyDinhService CTQDService;
    private final LoaiTruongService loaiTruongService;
    private final PhieuDangKyService phieuDangKyService;

    @GetMapping
    public String showAllCuocThi(@NotNull Model model) {
        model.addAttribute("cuocThis", cuocThiService.getAllCuocThis());
        model.addAttribute("loaiTruongService", loaiTruongService);
        model.addAttribute("phieuDangKyService", phieuDangKyService);
        return "CuocThi/list";
    }

    @GetMapping("/User")
    public String showAllCuocThiUser(@NotNull Model model) {
        model.addAttribute("cuocThis", cuocThiService.getAllCuocThisHien());
        model.addAttribute("loaiTruongService", loaiTruongService);
        model.addAttribute("phieuDangKyService", phieuDangKyService);
        return "CuocThi/listUser";
    }

    @GetMapping("/add")
    public String addCuocThiForm(@NotNull Model model) {
        model.addAttribute("cuocThi", new CuocThi());
        model.addAttribute("listMonThi",monThiService.getAllMonThisHien());
        model.addAttribute("allQuyDinhs", quyDinhService.getAllQuyDinhsHien());
        model.addAttribute("allNoiDungs", noiDungService.getAllNoiDungsHien());
        model.addAttribute("listLoaiTruong",loaiTruongService.getAllLoaiTruongsHien());
        return "CuocThi/add";
    }

    @PostMapping("/add")
    public String addCuocThi(
            @Valid @ModelAttribute("CuocThi") CuocThi cuocThi,
            @RequestParam(value = "selectedNoiDungs", required = false) List<Long> selectedNoiDungIds,
            @RequestParam(value = "selectedQuyDinhs", required = false) List<Long> selectedQuyDinhIds,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("listMonThi",monThiService.getAllMonThisHien());
            model.addAttribute("allQuyDinhs", quyDinhService.getAllQuyDinhsHien());
            model.addAttribute("allNoiDungs", noiDungService.getAllNoiDungsHien());
            model.addAttribute("listLoaiTruong",loaiTruongService.getAllLoaiTruongsHien());
            return "CuocThi/add";
        }

        cuocThiService.addCuocThi(cuocThi);

        for (Long noiDungId : selectedNoiDungIds) {
            ChiTietNoiDung ctnd = new ChiTietNoiDung();
            NoiDung nd = noiDungService.getNoiDungById(noiDungId).orElseThrow(() -> new EntityNotFoundException("LoaiTruong not found with id: "));

            ctnd.setNoiDung(nd);
            ctnd.setCuocThi(cuocThi);
            CTNDService.addChiTietNoiDung(ctnd);
        }

        for (Long quyDinhId : selectedQuyDinhIds) {
            ChiTietQuyDinh ctqd = new ChiTietQuyDinh();
            QuyDinh qd = quyDinhService.getQuyDinhById(quyDinhId).orElseThrow(() -> new EntityNotFoundException("LoaiTruong not found with id: "));

            ctqd.setQuyDinh(qd);
            ctqd.setCuocThi(cuocThi);
            CTQDService.addChiTietQuyDinh(ctqd);
        }
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