package DoAnCuoiKyJava.HeThongHoTroCuocThi.Validators;

import Tuan3.TranVietHung_Tuan3.Entities.Category;
import Tuan3.TranVietHung_Tuan3.Validators.annotations.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class ValidCategoryIdValidator implements
        ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category,
                           ConstraintValidatorContext context) {
        return category != null && category.getId() != null;
    }
}
