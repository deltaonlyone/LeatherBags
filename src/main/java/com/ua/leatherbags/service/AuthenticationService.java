package com.ua.leatherbags.service;

import com.ua.leatherbags.secutiry.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public String authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), request.getPassword()));

		UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
		return jwtService.generateToken(user);
	}
}
