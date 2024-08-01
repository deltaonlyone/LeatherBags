package com.ua.leatherbags.controller;

import com.ua.leatherbags.secutiry.AuthenticationRequest;
import com.ua.leatherbags.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
		name = "Authentication",
		description = "Authentication operations"
)
public class AuthenticationController {
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Value("${jwt.cookieExpire}")
	private int cookieExpire;


	@PostMapping("/login")
	@Operation(
			description = "Log in the user",
			summary = "Log in",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "User data",
					content = @Content(schema = @Schema(implementation = AuthenticationRequest.class))
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Logged in successfully"
					)
			}
	)
	@SecurityRequirements()
	public void authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), request.getPassword()));

		if (authentication.isAuthenticated()) {
			UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
			String token = jwtService.generateToken(user);
			ResponseCookie cookie = ResponseCookie.from("jwt", token)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.maxAge(cookieExpire)
					.build();
			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			response.setStatus(HttpsURLConnection.HTTP_OK);
		}
	}
}
