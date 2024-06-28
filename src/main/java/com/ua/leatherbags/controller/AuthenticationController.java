package com.ua.leatherbags.controller;

import com.ua.leatherbags.secutiry.AuthenticationRequest;
import com.ua.leatherbags.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		return authenticationService.authenticate(authenticationRequest);
	}
}
