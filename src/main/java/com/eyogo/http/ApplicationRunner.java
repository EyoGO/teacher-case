package com.eyogo.http;

import com.eyogo.http.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.SpringProperties;

@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
        System.out.println(context.getBeanDefinitionCount());

        System.out.println(SpringProperties.getProperty("test.message"));
        System.out.println(SpringProperties.getProperty("test.full.message"));

//        try (var appContext = new ClassPathXmlApplicationContext("application.xml")) {
        // Need to close AC to call close() - trigger lifecycle callback
//        try (var appContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
//            System.out.println(appContext.getBean("unitService"));
//            System.out.println(appContext.getBean("unitService2"));
//            System.out.println(appContext.getBean("userService"));
//            System.out.println(appContext.getBean("userService2"));
//        }
    }
}