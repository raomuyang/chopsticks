package cn.atomicer.chopsticks.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @author Rao Mengnan
 *         on 2018/5/9.
 */
@Documented
@Target(value = {FIELD, METHOD, PARAMETER, LOCAL_VARIABLE})
@Retention(value = CLASS)
public @interface NotNull {
}
