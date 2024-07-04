package DoAnCuoiKyJava.HeThongHoTroCuocThi.Request;


import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.MonThi;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.NoiDung;
import DoAnCuoiKyJava.HeThongHoTroCuocThi.Entities.QuyDinh;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CuocThiRequest {
    private Long Id;
    private String diaDiemThi;
    private LocalDate ngayThi;
    private int soLuong;
    private String tenCuocThi;
    private MonThi monThi;
    private QuyDinh quyDinh;
    private NoiDung noiDung;
}
