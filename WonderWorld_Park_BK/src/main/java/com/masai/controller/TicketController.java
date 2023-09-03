package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.TicketDTO;
import com.masai.Exception.CustomerException;
import com.masai.Exception.TicketException;
import com.masai.model.Ticket;
import com.masai.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	TicketService ticketService;
	@Autowired
	public TicketController(TicketService ticketService) {
		
		this.ticketService = ticketService;
	}
	
	@PostMapping("/{customerId}/{activityId}")
	public ResponseEntity<Ticket> createTicket(
			@Valid @RequestBody TicketDTO ticketDTO,
			@PathVariable Integer customerId,
			@PathVariable Integer activityId
			) 
	{
		
		Ticket ticket = ticketService.createTicket(customerId, activityId, ticketDTO);
	
		return new ResponseEntity<Ticket>(ticket, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{customerId}/{ticketId}")
	public ResponseEntity<Ticket> updateTicket(
			@PathVariable Integer customerId,
			@PathVariable Integer ticketId,
			@Valid @RequestBody TicketDTO ticketDTO) {
		
		Ticket ticket = ticketService.updateTicket(customerId, ticketId, ticketDTO);
		
		return new ResponseEntity<Ticket>(ticket, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{customerId}/{ticketId}")
	public ResponseEntity<Ticket> getTicketByTicketId(
			@PathVariable Integer customerId, @PathVariable Integer ticketId) 
	{
		
		return new ResponseEntity<>(ticketService.getTicketById(customerId, ticketId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}/{ticketId}")
	public ResponseEntity<String> deleteTicket(
			@PathVariable Integer customerId, @PathVariable Integer ticketId) 
					throws TicketException, CustomerException{
		
		Boolean deleted = ticketService.deleteTicket(customerId, ticketId);
		HttpStatus status;
		String message;

		if(deleted) {
			status = HttpStatus.OK;
			message = "Ticket deleted successfully";
		}else {
			status = HttpStatus.BAD_REQUEST;
			message = "Something went wrong";
		}
		
		return new ResponseEntity<>(message, status);
	}
	
	@GetMapping("/history/{customerId}")
	public ResponseEntity<List<Ticket>> getTicketBookingHistory(
			@PathVariable Integer customerId,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("recordsPerPage") Integer itemsPerPage) 
					throws TicketException, CustomerException{
		
		return new ResponseEntity<>(ticketService.getTicketBookingHistory(customerId, pageNumber, itemsPerPage), HttpStatus.OK);
	}
	
	@GetMapping("/todayHistory/{customerId}")
	public ResponseEntity<List<Ticket>> getTicketBookingHistoryForTheDay(
			@PathVariable Integer customerId) {
		
		return new ResponseEntity<>(ticketService.getTicketBookingHistoryForDay(customerId), HttpStatus.OK);
	}
	
	
	@GetMapping("/fair/{customerId}")
	public ResponseEntity<Double> getTotalFairForTheCustomer(
			@PathVariable Integer customerId) {
		
		return new ResponseEntity<>(ticketService.getTotalFair(customerId), HttpStatus.OK);
	}
	
	
	
}
