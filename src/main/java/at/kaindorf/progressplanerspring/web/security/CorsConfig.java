package at.kaindorf.progressplanerspring.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Value("${server.address}")
    private String serverAddress;

    /**
     * Adds Cross-Origin Resource Sharing (CORS) mappings to the provided {@link CorsRegistry} object. This method
     * allows the specified origin to access resources on the expo-server.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(String.format("http://%s:4200", serverAddress))
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

