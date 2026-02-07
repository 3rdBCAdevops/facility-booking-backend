package com.example.facilitybookingbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // Frontend URLs (with defaults, safe for tests)
    @Value("${frontend.url.prod1:https://facility-booking-frontend-git-main-asika-ms-projects.vercel.app}")
    private String prodFrontend1;

    @Value("${frontend.url.prod2:https://asika-facility-booking-frontend.vercel.app}")
    private String prodFrontend2;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // apply CORS only to API endpoints
                        .allowedOrigins(prodFrontend1, prodFrontend2)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);  // if using cookies or auth
            }
        };
    }
}
