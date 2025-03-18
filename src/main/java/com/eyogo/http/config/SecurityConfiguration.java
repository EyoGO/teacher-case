package com.eyogo.http.config;

import com.eyogo.http.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity //allows post/preAuthorize, post/preFilter annotations
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/password-reset", "password-reset/update-password").permitAll()
                                .requestMatchers("/login", "/error").permitAll()  // Allows parameters (by default only error in case of not successful log in)
                                .requestMatchers("/registration").hasAuthority(Role.ADMIN.getAuthority())
                                .anyRequest().authenticated())
                .formLogin((login) ->
                        login.loginPage("/login")
                                .successForwardUrl("/units")
                                .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
