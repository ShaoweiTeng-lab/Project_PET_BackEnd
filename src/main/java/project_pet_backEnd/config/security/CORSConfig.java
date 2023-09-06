package project_pet_backEnd.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://yang-hung-fei.github.io", "http://localhost:5500")//.allowedMethods("GET", "POST", "PUT", "DELETE")
           //  .allowedOriginPatterns("*")//全開不建議
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
