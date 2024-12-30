package com.eyogo.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringProperties;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

    public static void main(String[] args) {
        /*ConfigurableApplicationContext context = */SpringApplication.run(ApplicationRunner.class, args);
//        log.info(String.valueOf(context.getBeanDefinitionCount()));
//
//        log.info(SpringProperties.getProperty("test.message"));
//        log.info(SpringProperties.getProperty("test.full.message"));
//
//        log.info(SpringProperties.getProperty("app.name"));
//        log.info(SpringProperties.getProperty("app.description"));
//
//        TestComponent bean = context.getBean(TestComponent.class);
//        log.info(bean.toString());

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