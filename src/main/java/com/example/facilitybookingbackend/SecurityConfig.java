package com.example.facilitybookingbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()                // ✅ VERY IMPORTANT
            .and()
            .csrf().disable()      // ✅ REQUIRED for POST from frontend
            .authorizeHttpRequests()
            .anyRequest().permitAll();  // ✅ allow all for now

        return http.build();
    }
}
