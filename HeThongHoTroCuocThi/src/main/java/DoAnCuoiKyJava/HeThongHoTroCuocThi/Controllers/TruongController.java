package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.LoaiTruongService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.TruongService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/truong")
public class TruongController {

    private final TruongService truongService;
    private final LoaiTruongService loaiTruongService;

    public TruongController(TruongService truongService, LoaiTruongService loaiTruongService) {
        this.truongService = truongService;
        this.loaiTruongService = loaiTruongService;
    }

    @GetMapping
    public String listTruong(Model model) {
        List<Truong> truongs = truongService.getAllTruong();
        model.addAttribute("truongs", truongs);
        return "truong/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("truong", new Truong());
        model.addAttribute("loaiTruongs", loaiTruongService.getAllLoaiTruong());
        return "truong/create";
    }

    @PostMapping("/create")
    public String createTruong(@ModelAttribute("truong") Truong truong, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "truong/create";
        }
        truongService.createTruong(truong);
        redirectAttributes.addFlashAttribute("success", "Trường mới đã được tạo thành công.");
        return "redirect:/truong";
    }

    // Add more methods for update, delete, and other operations as needed
}
