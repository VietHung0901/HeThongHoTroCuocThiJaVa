package DoAnCuoiKyJava.HeThongHoTroCuocThi.Request;

import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.Truong;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private MultipartFile imageUrl;
    private Truong truong;
}
