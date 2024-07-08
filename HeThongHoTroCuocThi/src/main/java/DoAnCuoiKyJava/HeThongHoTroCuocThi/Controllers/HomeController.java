package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    @GetMapping
    public String home() {
        return "home/index";
    }



}
