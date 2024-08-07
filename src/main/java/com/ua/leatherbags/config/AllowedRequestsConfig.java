package com.ua.leatherbags.config;

import com.ua.leatherbags.secutiry.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AllowedRequestsConfig {
	private static final Logger log = LoggerFactory.getLogger(AllowedRequestsConfig.class);
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Value("${ALLOWED_ORIGIN}")
	private String allowedOrigin;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.authorizeHttpRequests((auth) -> auth
						.requestMatchers(HttpMethod.POST,
								"/bags",
								"/auth/login",
								"/ping")
						.permitAll()
						.requestMatchers(HttpMethod.GET,
								"/v2/api-docs/**",
								"/v3/api-docs/**",
								"/configuration/ui",
								"/configuration/security",
								"/swagger-resources/**",
								"/webjars/**",
								"/swagger-ui.html",
								"/swagger-ui/**")
						.permitAll()
						.anyRequest().authenticated())
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins(allowedOrigin)
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}
}
