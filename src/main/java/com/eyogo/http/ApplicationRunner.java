package com.eyogo.http;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("application.xml");
        System.out.println(appContext.getBean(String.class));
    }
}
