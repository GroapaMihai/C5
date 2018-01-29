package client_app.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Range {
	double normal_min() default 0.0;
	double normal_max() default 0.0;
    double min() default 0.0;
    double max() default 0.0;
}