package com.example.facilitybookingbackend.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CorsConfigTest {

    private static final class ExposedCorsRegistry extends CorsRegistry {
        Map<String, CorsConfiguration> exposedCorsConfigurations() {
            return super.getCorsConfigurations();
        }
    }

    @Test
    void addCorsMappings_registersExpectedGlobalCorsPolicy() {
        ExposedCorsRegistry registry = new ExposedCorsRegistry();

        new CorsConfig().addCorsMappings(registry);

        Map<String, CorsConfiguration> configs = registry.exposedCorsConfigurations();
        assertThat(configs).containsKey("/**");

        CorsConfiguration config = configs.get("/**");
        assertThat(config).isNotNull();

        assertThat(config.getAllowedOriginPatterns()).isEqualTo(List.of("*"));
        assertThat(config.getAllowedMethods()).containsExactly("GET", "POST", "PUT", "DELETE", "OPTIONS");
        assertThat(config.getAllowedHeaders()).isEqualTo(List.of("*"));
        assertThat(config.getMaxAge()).isEqualTo(3600L);
    }
}
