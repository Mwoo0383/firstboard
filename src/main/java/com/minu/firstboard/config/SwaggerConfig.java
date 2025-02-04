package com.minu.firstboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info);
    }

    Info info = new Info()
            .title("Board Test")
            .version("0.0.1")
            .description("<h3>Board Test</h3>");
}
