package com.eyogo.http;

import com.eyogo.http.config.ApplicationConfiguration;
import com.eyogo.http.service.ImageService;
import com.eyogo.http.service.UnitService;
import com.eyogo.http.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {

//        try (var appContext = new ClassPathXmlApplicationContext("application.xml")) {
        // Need to close AC to call close() - trigger lifecycle callback
        try (var appContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {appContext.register();
            System.out.println(appContext.getBean("unitService"));
            System.out.println(appContext.getBean("unitService2"));
            System.out.println(appContext.getBean("userService"));
            System.out.println(appContext.getBean("userService2"));
        }
    }
}