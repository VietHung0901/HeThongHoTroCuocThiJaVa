package DoAnCuoiKyJava.HeThongHoTroCuocThi.Validators;

import Tuan3.TranVietHung_Tuan3.Services.UserService;
import Tuan3.TranVietHung_Tuan3.Validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(userService == null)
            return true;
        return userService.findByUsername(username) == null;
    }
}
