package com.eyogo.http.config;

import com.eyogo.http.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;


// This annotation has auditorAware property that can specify how to convert current auditor (@ModifiedBy...), but also we can create a bean:
//@EnableJpaAuditing // It is included in @EnableEnversRepositories, so it overrides all it's properties and can be commented
// Enables Envers and also provides additional queries for Envers tables. By default, scans own package, so let's define location.
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
@Configuration
// After everything is configured for Envers, we can use provided logic to query tables. Go to UserRepository->
public class AuditConfiguration {

    // This bean specifies how to handle current user for default auditing
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("eyogo");
    }
}
