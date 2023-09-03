package com.masai.Exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDetails {

	private String message;
	private String description;
	private LocalDateTime date=LocalDateTime.now();
	public ErrorDetails(String message, String description) {
		super();
		this.message = message;
		this.description = description;
	}
	
	
}
