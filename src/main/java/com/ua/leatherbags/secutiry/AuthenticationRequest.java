package com.ua.leatherbags.secutiry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	@Schema(example = "john.doe@example.com")
	private String username;
	@Schema(example = "password")
	private String password;
}
