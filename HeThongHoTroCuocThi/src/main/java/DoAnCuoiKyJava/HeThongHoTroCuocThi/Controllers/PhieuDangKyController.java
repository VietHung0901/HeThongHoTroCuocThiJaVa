package DoAnCuoiKyJava.HeThongHoTroCuocThi.Controllers;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.*;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Request.PhieuDangKyCreate;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Services.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/PhieuDangKys")
public class PhieuDangKyController {
    private final PhieuDangKyService phieuDangKyService;
    private final CuocThiService cuocThiService;
    private final UserService userService;
    private final TruongService truongService;
    private final LoaiTruongService loaiTruongService;
    private final PhieuKetQuaService phieuKetQuaService;

    @GetMapping("/cuocThi/id/{id}")
    public String showAllPhieuDangKyTheoCuocThi(@NotNull Model model, @PathVariable Long id) {
        model.addAttribute("phieuDangKys", phieuDangKyService.getAllPhieuDangKystheoCuocThi(id));
        model.addAttribute("phieuKetQuaService", phieuKetQuaService);
        model.addAttribute("truongService", truongService);
        model.addAttribute("cuocThiId", id);
        return "PhieuDangKy/list";
    }

    @GetMapping("/add/id/{id}")
    public String addPhieuDangKyForm(@NotNull Model model, @PathVariable Long id) {
        PhieuDangKyCreate phieuDangKy = new PhieuDangKyCreate();
        CuocThi cuocThi = cuocThiService.getCuocThiById(id)
                .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: " + id));
        phieuDangKy.setCuocThi(cuocThi);
        model.addAttribute("phieuDangKy", phieuDangKy);
        model.addAttribute("cuocThi", cuocThi);
        model.addAttribute("loaiTruongService", loaiTruongService);
        return "PhieuDangKy/add";
    }

    @PostMapping("/add")
    public String addPhieuDangKy(
            @Valid @ModelAttribute("PhieuDangKy") PhieuDangKyCreate phieuDangKyCreate,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errorMessage", errors);

            CuocThi cuocThi = cuocThiService.getCuocThiById(phieuDangKyCreate.getCuocThi().getId())
                    .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: "));
            phieuDangKyCreate.setCuocThi(cuocThi);
            model.addAttribute("phieuDangKy", phieuDangKyCreate);
            model.addAttribute("cuocThi", cuocThi);
            model.addAttribute("loaiTruongService", loaiTruongService);
            return "PhieuDangKy/add";
        }

        // Kiểm tra các dữ liệu đầu vào không được null
        if (phieuDangKyCreate.getUserId() == null
                || phieuDangKyCreate.getSdt() == null
                || phieuDangKyCreate.getEmail() == null
                || phieuDangKyCreate.getTruongId() == null) {
            model.addAttribute("errorMessage", "Vui lòng xác nhận thông tin");
            CuocThi cuocThi = cuocThiService.getCuocThiById(phieuDangKyCreate.getCuocThi().getId())
                    .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: "));
            phieuDangKyCreate.setCuocThi(cuocThi);
            model.addAttribute("phieuDangKy", phieuDangKyCreate);
            model.addAttribute("cuocThi", cuocThi);
            model.addAttribute("loaiTruongService", loaiTruongService);
            return "PhieuDangKy/add";
        }

        // Kiểm tra xem số lượng thí sinh đã đạt mức tối đa chưa
        int soLuongThiSinhHienTai = phieuDangKyService.countPhieuDangKyByCuocThiId(phieuDangKyCreate.getCuocThi().getId());
        if (soLuongThiSinhHienTai >= phieuDangKyCreate.getCuocThi().getSoLuongThiSinh()) {
            model.addAttribute("errorMessage", "Số lượng thí sinh tối đa cho cuộc thi này đã đạt.");
            CuocThi cuocThi = cuocThiService.getCuocThiById(phieuDangKyCreate.getCuocThi().getId())
                    .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: "));
            phieuDangKyCreate.setCuocThi(cuocThi);
            model.addAttribute("phieuDangKy", phieuDangKyCreate);
            model.addAttribute("cuocThi", cuocThi);
            model.addAttribute("loaiTruongService", loaiTruongService);
            return "PhieuDangKy/add";
        }

        //Kiểm tra cấp học của người dùng có phù hợp với cuộc thi
        User user = userService.findById(phieuDangKyCreate.getUserId());
        Truong truongUser = truongService.getTruongById(user.getTruong().getId())
                .orElseThrow(() -> new EntityNotFoundException(""));
        if(truongUser.getLoaiTruong().getId() != phieuDangKyCreate.getCuocThi().getLoaiTruongId())
        {
            model.addAttribute("errorMessage", "Cuộc thi không dành cho cấp học của bạn");
            CuocThi cuocThi = cuocThiService.getCuocThiById(phieuDangKyCreate.getCuocThi().getId())
                    .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: "));
            phieuDangKyCreate.setCuocThi(cuocThi);
            model.addAttribute("phieuDangKy", phieuDangKyCreate);
            model.addAttribute("cuocThi", cuocThi);
            model.addAttribute("loaiTruongService", loaiTruongService);
            return "PhieuDangKy/add";
        }

        // Kiểm tra xem người dùng đã đăng ký cuộc thi này chưa
        if (phieuDangKyService.tonTaiPhieuDangKyUserId_CuocThiId(phieuDangKyCreate.getUserId(), phieuDangKyCreate.getCuocThi().getId())) {
            model.addAttribute("errorMessage", "Bạn đã đăng ký cho cuộc thi này rồi.");
            CuocThi cuocThi = cuocThiService.getCuocThiById(phieuDangKyCreate.getCuocThi().getId())
                    .orElseThrow(() -> new EntityNotFoundException("CuocThi not found with id: "));
            phieuDangKyCreate.setCuocThi(cuocThi);
            model.addAttribute("phieuDangKy", phieuDangKyCreate);
            model.addAttribute("cuocThi", cuocThi);
            model.addAttribute("loaiTruongService", loaiTruongService);
            return "PhieuDangKy/add";
        }

        PhieuDangKy phieuDangKy = phieuDangKyService.mapToPhieuDangKy(phieuDangKyCreate);
        phieuDangKyService.addPhieuDangKy(phieuDangKy);
        return "redirect:/CuocThis";
    }

    @GetMapping("/edit/{id}")
    public String editPhieuDangKy(@PathVariable Long id, Model model) {
        PhieuDangKy phieuDangKy = phieuDangKyService.getPhieuDangKyById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tồn tại phiếu đăng ký có id: " + id));
        model.addAttribute("phieuDangKy", phieuDangKy);
        model.addAttribute("listTruong", truongService.getAllTruongsHien());
        model.addAttribute("loaiTruongService", loaiTruongService);
        return "PhieuDangKy/edit";
    }

    @PostMapping("/edit")
    public String updatePhieuDangKy(@Valid @ModelAttribute("PhieuDangKy") PhieuDangKy phieuDangKy,
                               @NotNull BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "PhieuDangKy/edit";
        }
        phieuDangKyService.updatePhieuDangKy(phieuDangKy);
        return "redirect:/PhieuDangKys/cuocThi/id/" + phieuDangKy.getCuocThi().getId();
    }
}