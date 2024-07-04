package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.MonThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.MonThiService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/monthis")
@RequiredArgsConstructor
public class MonThiController {

    private final MonThiService monThiService;

    @GetMapping("")
    public String showAllMonThi(@NonNull Model model) {
        model.addAttribute("monthis", monThiService.getAllMonThis());
        return "MonThi/list";
    }

    @GetMapping("/add")
    public String addMonThiForm(@NonNull Model model) {
        model.addAttribute("monThi", new MonThi());
        return "MonThi/add";
    }

    @PostMapping("/add")
    public String addMonThi(@ModelAttribute("monThi") MonThi monThi) {
        /*if(monThiService.getMonThiById(monThi.getId()).isEmpty())
            monThiService.addMonThi(monThi);*/
        if (monThi.getId() == null) { // Kiểm tra nếu ID là null
            monThiService.addMonThi(monThi);
        }
        return "redirect:/monthis";
    }

    @GetMapping("/edit/{id}")
    public String editMonThiForm(@NonNull Model model, @PathVariable long id)
    {
        var monthi = monThiService.getMonThiById(id).orElse(null);
        model.addAttribute("monThi", monthi != null ? monthi : new MonThi());
        return "MonThi/edit";
    }

    @PostMapping("/edit")
    public String editMonThi(@ModelAttribute("monThi") MonThi monThi) {
        monThiService.updateMonThi(monThi);
        return "redirect:/monthis";
    }

    @PostMapping("/anhien/{id}")
    public String anHienMonThi(@PathVariable("id") Long id) {
        monThiService.anHienMonThi(id);
        return "redirect:/monthis";
    }

}
