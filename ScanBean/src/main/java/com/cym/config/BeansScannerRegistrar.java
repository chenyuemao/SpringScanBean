package com.cym.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class BeansScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean acceptAllBeans = true;
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(BeansScanner.class.getName()));
        BeanPackageScanner scanner = new BeanPackageScanner(registry);

        Class<? extends Annotation> annotationClass = annotationAttributes.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            acceptAllBeans = false;
            scanner.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
        }

        List<String> basePackage = new ArrayList<String>();
        for (String pkg : annotationAttributes.getStringArray("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackage.add(pkg);
            }
        }

        for (String pkg : annotationAttributes.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackage.add(pkg);
            }
        }

        if (acceptAllBeans) {
            scanner.addIncludeFilter(new TypeFilter() {
                @Override
                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return true;
                }
            });
        }

//        if (acceptAllBeans) {
//            scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> {
//                return true;
//            });
//        }
        scanner.doScan(StringUtils.toStringArray(basePackage));
    }


}
