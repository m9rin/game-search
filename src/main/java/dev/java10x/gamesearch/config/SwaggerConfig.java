package dev.java10x.gamesearch.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenAPI() {

        Contact contact = new Contact();
        contact.url("https://www.linkedin.com/in/m9rin/");
        contact.email("marinjhow@gmail.com");

        Info info = new Info();
        info.title("Game Search");
        info.version("v1");
        info.description("Aplicação desenvolvida para cadastro e pesquisa de jogos e suas plataformas disponiveis");
        info.contact(contact);


        return new OpenAPI().info(info);
    }
}
