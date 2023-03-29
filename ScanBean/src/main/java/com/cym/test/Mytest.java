package com.cym.test;

import com.cym.beans.Teacher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Mytest {
    @Test
    public void test1() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestBean.class);
        Teacher bean = applicationContext.getBean(Teacher.class);
        System.out.println(bean.getUsername());

    }

}
