package com.api.myfavgames.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "My Favorite games API",
        description = "A API for store your favorites games",
        version = "v1",
        contact = @Contact(
            name = "Eric Oliveira",
            email = "eriicdiiego@hotmail.com",
            url = "https://github.com/ericoliveiras"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local server")
    }
)
public class SwaggerConfig {}
