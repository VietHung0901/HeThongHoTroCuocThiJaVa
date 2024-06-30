package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.LoaiTruongService;
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
@RequestMapping("/loai-truong")
public class LoaiTruongController {

    private final LoaiTruongService loaiTruongService;

    public LoaiTruongController(LoaiTruongService loaiTruongService) {
        this.loaiTruongService = loaiTruongService;
    }

    @GetMapping
    public String listLoaiTruong(Model model) {
        List<LoaiTruong> loaiTruongs = loaiTruongService.getAllLoaiTruong();
        model.addAttribute("loaiTruongs", loaiTruongs);
        return "loai-truong/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("loaiTruong", new LoaiTruong());
        return "loai-truong/create";
    }

    @PostMapping("/create")
    public String createLoaiTruong(@ModelAttribute("loaiTruong") LoaiTruong loaiTruong, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "loai-truong/create";
        }
        loaiTruongService.createLoaiTruong(loaiTruong);
        redirectAttributes.addFlashAttribute("success", "Loại trường mới đã được tạo thành công.");
        return "redirect:/loai-truong";
    }

    // Add more methods for update, delete, and other operations as needed
}
