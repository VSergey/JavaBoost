package boost.util;

import java.lang.annotation.*;

/**
 * Designates that a field, return value, argument, or variable may be null.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Nullable {
}
