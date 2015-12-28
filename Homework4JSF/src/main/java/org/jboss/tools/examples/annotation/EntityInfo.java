package org.jboss.tools.examples.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityInfo {
    String info() default "";
    int weight() default 1;
    Class<?> expected() default Exception.class;
}