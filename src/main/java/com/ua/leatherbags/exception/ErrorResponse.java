package com.ua.leatherbags.exception;

import lombok.Data;

@Data
public class ErrorResponse {
	private int status;
	private String message;
	private long timestamp;
}
