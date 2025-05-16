package br.com.infuse.backendcreditos.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // permite todas as rotas
                .allowedOrigins("http://localhost:4200") // permite chamadas do frontend Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // permite os métodos
                .allowedHeaders("*"); // permite qualquer header
    }
}