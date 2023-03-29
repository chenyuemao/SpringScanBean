package com.cym.config;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(BeansScannerRegistrar.class)
public @interface BeansScanner {
    String[] value() default {};
    String[] basePackages() default {};

     Class<? extends Annotation> annotationClass() default Annotation.class;
}
