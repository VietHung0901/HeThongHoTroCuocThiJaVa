package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.CuocThiRequest;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.CuocThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.MonThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.NoiDungService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.QuyDinhService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Optional;

@Controller
@RequestMapping("/cuocthis")
@RequiredArgsConstructor
public class CuocThiController {
    private final CuocThiService cuocThiService;
    private final MonThiService monThiService;
    private final NoiDungService noiDungService;
    private final QuyDinhService quyDinhService;

    @GetMapping
    public String listCuocThis(Model model) {
        model.addAttribute("cuocthis", cuocThiService.getAllCuocThis());
        model.addAttribute("monthis", monThiService.getAllMonThis());
        return "CuocThi/list";
    }

    @GetMapping("/add")
    public String addCuocThiForm(Model model) {
        model.addAttribute("cuocThiRequest", new CuocThiRequest());
        model.addAttribute("allMonThis", monThiService.getAllMonThis());
        model.addAttribute("allQuyDinhs", quyDinhService.getAllQuyDinhs());
        model.addAttribute("allNoiDungs", noiDungService.getAllNoiDungs());
        return "CuocThi/add";
    }

    @PostMapping("/add")
    public String addCuocThi(@Valid @ModelAttribute("cuocThiRequest") CuocThiRequest cuocThiRequest,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("allMonThis", monThiService.getAllMonThis());
            model.addAttribute("allQuyDinhs", quyDinhService.getAllQuyDinhs());
            model.addAttribute("allNoiDungs", noiDungService.getAllNoiDungs());
            return "CuocThi/add";
        }
        cuocThiService.addCuocThi(cuocThiRequest);
        return "redirect:/cuocthis";
    }

    @GetMapping("/edit/{id}")
    public String editCuocThiForm(@PathVariable Long id, Model model) {
        Optional<CuocThi> cuocThiOpt = cuocThiService.getCuocThiById(id);
        if (cuocThiOpt.isPresent()) {
            CuocThiRequest cuocThiRequest = cuocThiService.mapToCuocThiRequest(cuocThiOpt.get());
            model.addAttribute("cuocThiRequest", cuocThiRequest);
            model.addAttribute("allMonThis", monThiService.getAllMonThis());
            model.addAttribute("allQuyDinhs", quyDinhService.getAllQuyDinhs());
            model.addAttribute("allNoiDungs", noiDungService.getAllNoiDungs());
            return "CuocThi/edit";
        }
        return "redirect:/cuocthis";
    }

    @PostMapping("/edit/{id}")
    public String updateCuocThi(@PathVariable Long id, @Valid @ModelAttribute("cuocThiRequest") CuocThiRequest cuocThiRequest,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allMonThis", monThiService.getAllMonThis());
            model.addAttribute("allQuyDinhs", quyDinhService.getAllQuyDinhs());
            model.addAttribute("allNoiDungs", noiDungService.getAllNoiDungs());
            return "CuocThi/edit";
        }

        cuocThiService.updateCuocThi(id, cuocThiRequest);
        return "redirect:/cuocthis";
    }

    @PostMapping("/anhien/{id}")
    public String anHienCuocThi(@PathVariable Long id) {
        cuocThiService.anHienCuocThi(id);
        return "redirect:/cuocthis";
    }
}

