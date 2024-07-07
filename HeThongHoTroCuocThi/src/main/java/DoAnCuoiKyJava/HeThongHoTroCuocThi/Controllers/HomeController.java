package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.CuocThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.User;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.ICuocThiRepository;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Homes")
@RequiredArgsConstructor

public class HomeController {
    private final IUserRepository userRepository;
    private final ICuocThiRepository cuocThiRepository;

    @GetMapping
    public String home(Model model) {
        List<User> users = userRepository.findAll();
        List<CuocThi> cuocThis = cuocThiRepository.findAll();
        int soLuongUser = users.size(); // Số lượng người dùng
        int soLuongUserMoi = 0; // Số lượng người dùng mới, cần thay đổi theo logic của bạn
        int soLuongCuocThi = cuocThis.size(); // Số lượng cuộc thi, cần thay đổi theo logic của bạn
        int soLuongUserDK = 0; // Số lượt đăng ký, cần thay đổi theo logic của bạn

        // Truyền dữ liệu vào model để hiển thị trên view
        model.addAttribute("soLuongUser", soLuongUser);
        model.addAttribute("soLuongUserMoi", soLuongUserMoi);
        model.addAttribute("soLuongCuocThi", soLuongCuocThi);
        model.addAttribute("soLuongUserDK", soLuongUserDK);

        return "Home/index";
    }
}
