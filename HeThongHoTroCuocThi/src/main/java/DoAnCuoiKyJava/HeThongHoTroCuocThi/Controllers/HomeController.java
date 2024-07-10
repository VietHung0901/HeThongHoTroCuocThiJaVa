package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.CuocThiService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.PhieuDangKyService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.QuyDinhService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final CuocThiService cuocThiService;
    private final PhieuDangKyService phieuDangKyService;
    private final QuyDinhService quyDinhService;
    @GetMapping
    public String home(Model model) {
        model.addAttribute("quyDinhs", quyDinhService.getAllQuyDinhsHien());
        return "home/index";
    }

    @GetMapping("/ThongKe")
    public String thongKe(Model model) {
        long totalUsers = userService.getTotalUsers();
        long totalCompetitions = cuocThiService.getTotalCuocThis();
        long totalRegistrations = phieuDangKyService.getTotalPDKs();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCompetitions", totalCompetitions);
        model.addAttribute("totalRegistrations", totalRegistrations);

        List<Object[]> userCountsByLoaiTruong = userService.getUserCountsByLoaiTruong();
        model.addAttribute("userCountsByLoaiTruong", userCountsByLoaiTruong);

        return "home/thongKe";
    }
}
