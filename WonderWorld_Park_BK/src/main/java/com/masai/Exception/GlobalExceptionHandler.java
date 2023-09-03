package com.masai.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> customerExceptionHandler(NoHandlerFoundException ex, WebRequest we){
		ErrorDetails er= new ErrorDetails();
		er.setMessage(ex.getMessage());
		er.setDescription(we.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionHandler(Exception ex,WebRequest we){
		ErrorDetails er= new ErrorDetails();
		er.setMessage(ex.getMessage());
		er.setDescription(we.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<ErrorDetails> adminExceptionHandler(AdminException ex,WebRequest we){
		ErrorDetails er= new ErrorDetails();
		er.setMessage(ex.getMessage());
		er.setDescription(we.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ActivityException.class)
	public ResponseEntity<ErrorDetails> adminExceptionHandler(ActivityException ex,WebRequest we){
		ErrorDetails er= new ErrorDetails();
		er.setMessage(ex.getMessage());
		er.setDescription(we.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ErrorDetails> customerExceptionHandler(CustomerException ex, WebRequest we){
		ErrorDetails er= new ErrorDetails();
		er.setMessage(ex.getMessage());
		er.setDescription(we.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TicketException.class)
	public ResponseEntity<ErrorDetails> ticketExceptionHandler(TicketException ex, WebRequest we){
		ErrorDetails er= new ErrorDetails();
		er.setMessage(ex.getMessage());
		er.setDescription(we.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.NOT_FOUND);
	}
}
