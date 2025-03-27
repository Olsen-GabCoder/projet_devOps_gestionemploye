package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Appliquer la configuration CORS à tous les points de terminaison sous /api/
                .allowedOrigins("http://16.16.166.194:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Ajout de la méthode OPTIONS
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}