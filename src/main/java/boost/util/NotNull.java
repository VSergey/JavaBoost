package boost.util;

import java.lang.annotation.*;

/**
 * Designates that a field, return value, argument, or variable is
 * guaranteed to be non-null.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface NotNull {
}
