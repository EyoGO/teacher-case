package com.eyogo.http;

import com.eyogo.http.dao.UnitDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {

        // Need to close AC to call close() - trigger lifecycle callback
        try (ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("application.xml")) {
            System.out.println(appContext.getBean("str1"));
            System.out.println(appContext.getBean(UnitDao.class));
        }
    }
}
