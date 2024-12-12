package com.eyogo.http.config;

import com.eyogo.http.config.condition.JpaCondition;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@Conditional(JpaCondition.class)
public class MyJpaConfiguration {

    // Перший спосіб ініціалізації пропертісів в клас (створили пустий бін і анотація уже потім заповнятиме все по префіксу). Другий спосіб над класом.
//    @Bean
//    @ConfigurationProperties(prefix = "db")
//    public DatabaseProperties databaseProperties() {
//        return new DatabaseProperties();
//    }

    @PostConstruct
    public void init() {
        log.info("MyJpaConfiguration post construct");
    }

    @PreDestroy
    public void destroy() {
        log.info("MyJpaConfiguration pre destroy");
    }
}
