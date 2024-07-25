package com.project.test.util;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplicationContextUtil   {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    public static void refreshContext(String basePackages){
        context.scan(basePackages);
        context.refresh();
    }

    public static AnnotationConfigApplicationContext getContext(){
        return context;
    }

}
