package com.ua.leatherbags.scheduled;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
		name = "Ping",
		description = "Ping the server"
)
public class PingController {

	@GetMapping("/ping")
	@Operation(
			description = "Ping the server to check if it's accessible",
			summary = "Check server accessibility",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Server is accessible"
					)
			}
	)
	@SecurityRequirements()
	public void ping() {
	}
}
