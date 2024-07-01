package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.User;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.UserRequest;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.TruongService;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TruongService truongService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new UserRequest());
        model.addAttribute("listTruong", truongService.getAllTruong());
        return "user/register";
    }

    @PostMapping("/register")
//    public String register(@Valid @ModelAttribute("user") User user,
    public String register(UserRequest userRequest,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("listTruong", truongService.getAllTruong());
            return "user/register";
        }

        String image = userService.saveImage(userRequest.getImageUrl());
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setImageUrl(image);
        user.setTruong(userRequest.getTruong());
        user.setTrangThai(1);
        userService.Save(user);
        userService.setDefaultRole(user.getUsername());
        return "redirect:/login";
    }
}