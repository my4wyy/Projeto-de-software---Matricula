package br.com.demo.regescweb.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation; 
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("API de Aluguel de Automóveis")
            .version("1.0")
            .description("Documentação da API"))
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
            .addSecuritySchemes("BearerAuth",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public OperationCustomizer customGlobalHeader() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            if (operation.getTags() != null && operation.getTags().contains("Pedido")) {
             
                operation.getParameters().removeIf(param -> 
                    param.getName().equalsIgnoreCase("Authorization"));
            }
            return operation;
        };
    }
}
