package mak.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAround {

    String before() default "";

    String after() default "";

    String event() default "";

    String[] instanceFields() default {};
    String[] argFields() default {};
}

