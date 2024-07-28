package com.ua.leatherbags.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
		info = @Info(
				title = "Leather Bags API",
				description = "Documentation for Leather Bags website API for developers",
				version = "1.0"
		),
		servers = {
				@Server(
						url = "https://leatherbags.onrender.com/api",
						description = "Production server"
				),
				@Server(
						url = "http://localhost:8080/api",
						description = "Local DEV testing ENV"
				)
		},
		security = {
				@SecurityRequirement(
						name = "JWT Auth"
				)
		}
)
@SecurityScheme(
		name = "JWT Auth",
		description = "JWT Auth using cookie as bearer",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.COOKIE
)
@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addResponses("403Response", new ApiResponse()
								.description("Unauthorized / Token Invalid")));
	}
}
