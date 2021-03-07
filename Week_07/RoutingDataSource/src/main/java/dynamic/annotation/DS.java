package dynamic.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    String value() default "WriteDS";

    OperationEnum operate();
}
