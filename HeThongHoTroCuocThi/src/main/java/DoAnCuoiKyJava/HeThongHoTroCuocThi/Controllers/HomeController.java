package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.LoaiTruong;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final CuocThiService cuocThiService;
    private final PhieuDangKyService phieuDangKyService;

    @GetMapping("/")
    public String home(Model model) {
        long totalUsers = userService.getTotalUsers();
        long totalCompetitions = cuocThiService.getTotalCuocThis();
        long totalRegistrations = phieuDangKyService.getTotalPDKs();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalCompetitions", totalCompetitions);
        model.addAttribute("totalRegistrations", totalRegistrations);

        List<Object[]> userCountsByLoaiTruong = userService.getUserCountsByLoaiTruong();
        model.addAttribute("userCountsByLoaiTruong", userCountsByLoaiTruong);

        return "home/index";
    }
}
