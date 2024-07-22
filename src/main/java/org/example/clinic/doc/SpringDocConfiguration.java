package org.example.clinic.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Clinic")
                        .description("API Rest de la aplicación Clinic, administra las consultas de una clinica con pacientes y medicos.")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@clinic.net"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://forum.net/api/licencia")));  }
}

