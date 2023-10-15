package project_pet_backEnd.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //開啟跨域請求
                .allowedOrigins("https://yang-hung-fei.github.io","http://localhost:5500")//.allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
