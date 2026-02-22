package anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import validation.StateValidation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    String message() default "state只能是已发布和草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
