package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.LoaiTruongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loaitruongs")
@RequiredArgsConstructor
public class LoaiTruongController {
    private final LoaiTruongService loaiTruongService;

    @GetMapping("")
    public String getAllLoaiTruongs(Model model) {
        List<LoaiTruong> loaiTruongs = loaiTruongService.getAllLoaiTruongs();
        model.addAttribute("loaitruongs", loaiTruongs);
        return "LoaiTruong/list"; // Thay thế bằng đường dẫn tới template hiển thị danh sách loại trường
    }

    @GetMapping("/add")
    public String addLoaiTruongForm(Model model) {
        model.addAttribute("loaiTruong", new LoaiTruong());
        return "LoaiTruong/add"; // Thay thế bằng đường dẫn tới template thêm loại trường
    }

    @PostMapping("/add")
    public String addLoaiTruong(@ModelAttribute("loaiTruong") @Valid LoaiTruong loaiTruong) {
        loaiTruongService.addLoaiTruong(loaiTruong);
        return "redirect:/loaitruongs"; // Thay thế bằng đường dẫn tới controller xem tất cả loại trường
    }

    @GetMapping("/edit/{id}")
    public String editLoaiTruongForm(@PathVariable Long id, Model model) {
        var loaitruong = loaiTruongService.getLoaiTruongById(id).orElse(null);
        model.addAttribute("loaiTruong", loaitruong != null ? loaitruong : new LoaiTruong());
        return "LoaiTruong/edit";
    }

    @PostMapping("/edit")
    public String editLoaiTruong(@ModelAttribute("loaiTruong") @Valid LoaiTruong loaiTruong) {
        loaiTruongService.updateLoaiTruong(loaiTruong);
        return "redirect:/loaitruongs"; // Thay thế bằng đường dẫn tới controller xem tất cả loại trường
    }

    @PostMapping("/anhien/{id}")
    public String anHienLoaiTruong(@PathVariable Long id) {
        loaiTruongService.anHienLoaiTruong(id);
        return "redirect:/loaitruongs";
    }
}
