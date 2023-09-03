package com.masai.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.masai.Exception.ActivityException;
import com.masai.Exception.CustomerException;
import com.masai.Exception.TicketException;
import com.masai.model.Activity;
import com.masai.model.Customer;
import com.masai.model.Ticket;
import com.masai.service.ActivityService;
import com.masai.service.CustomerService;
import com.masai.service.TicketService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private TicketService ticketService;
	
    @Autowired
	private PasswordEncoder  passwordEncoder;
    
	
	
	@PostMapping("/registerCustomer")
	private ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer)throws CustomerException{
		customer.setIsDeleted(false);
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer c = customerService.registerCustomer(customer);
		return new ResponseEntity<Customer>(c, HttpStatus.CREATED);	
	}
	
	@GetMapping("/signin")
	public ResponseEntity<Customer> logInUserHandler(Authentication auth) throws CustomerException {
		Customer custo = customerService.findByEmail(auth.getName()).get();
		
		return new ResponseEntity<>(custo, HttpStatus.ACCEPTED);
	}	
	
	
	
	@PutMapping("/update/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.updateCustomer(customer, customerId),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.deleteCustomer(customerId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.viewCustomerById(customerId),HttpStatus.OK);
	}
	
	//Activity
	@GetMapping("/activity/all")
	public ResponseEntity<List<Activity>> viewAllActivity(@RequestParam(name = "pageNumber") Integer pageNumber,@RequestParam(name = "pageSize") Integer pageSize)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.viewAllActivities(pageNumber, pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getActivitiesByCharge")
	public ResponseEntity<List<Activity>> getActivitiesByCharge(@RequestParam Double charge)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getActivitiesByCharges(charge),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getNumberOfActivitiesByCharge/{charge}")
	public ResponseEntity<Integer> getNumOfActByCharge(@PathVariable Double charge)throws ActivityException{
		return new ResponseEntity<Integer>(activityService.getCountNumberOfActivitesByCharge(charge),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getAllActivitiesByDate")
	public ResponseEntity<List<Activity>> getAllActivitiesByDateTime(
			 @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
	            @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesOnBasisOfDate(start, end),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getAllActivitiesOfCustomerByDate/{customerId}/date")
	public ResponseEntity<List<Activity>> getAllActivitiesOfCustomerByDate(@PathVariable Integer customerId,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start)
					throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesForDays(customerId, start),HttpStatus.OK);
	}
	
	@GetMapping("/activity/{customerId}")
		public ResponseEntity<List<Activity>> getAllActivitiesByCustomerId(@PathVariable Integer customerId)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivityByCustomerId(customerId),HttpStatus.OK);
	}
	
	//Ticket
	
	@PostMapping("/ticket/{customerId}/{activityId}")
	public ResponseEntity<Ticket> createTicket(
			@Valid @RequestBody TicketDTO ticketDTO,
			@PathVariable Integer customerId,
			@PathVariable Integer activityId
			) 
	{
		
		Ticket ticket = ticketService.createTicket(customerId, activityId, ticketDTO);
	
		return new ResponseEntity<Ticket>(ticket, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/ticket/{customerId}/{ticketId}")
	public ResponseEntity<Ticket> updateTicket(
			@PathVariable Integer customerId,
			@PathVariable Integer ticketId,
			@Valid @RequestBody TicketDTO ticketDTO) {
		
		Ticket ticket = ticketService.updateTicket(customerId, ticketId, ticketDTO);
		
		return new ResponseEntity<Ticket>(ticket, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/ticket/{customerId}/{ticketId}")
	public ResponseEntity<Ticket> getTicketByTicketId(
			@PathVariable Integer customerId, @PathVariable Integer ticketId) 
	{
		
		return new ResponseEntity<>(ticketService.getTicketById(customerId, ticketId), HttpStatus.OK);
	}
	
	@DeleteMapping("/ticket/{customerId}/{ticketId}")
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
	
	@GetMapping("/ticket/history/{customerId}")
	public ResponseEntity<List<Ticket>> getTicketBookingHistory(
			@PathVariable Integer customerId,
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("recordsPerPage") Integer itemsPerPage) 
					throws TicketException, CustomerException{
		
		return new ResponseEntity<>(ticketService.getTicketBookingHistory(customerId, pageNumber, itemsPerPage), HttpStatus.OK);
	}
	
	@GetMapping("/ticket/todayHistory/{customerId}")
	public ResponseEntity<List<Ticket>> getTicketBookingHistoryForTheDay(
			@PathVariable Integer customerId) {
		
		return new ResponseEntity<>(ticketService.getTicketBookingHistoryForDay(customerId), HttpStatus.OK);
	}
	
	
	@GetMapping("/ticket/fair/{customerId}")
	public ResponseEntity<Double> getTotalFairForTheCustomer(
			@PathVariable Integer customerId) {
		
		return new ResponseEntity<>(ticketService.getTotalFair(customerId), HttpStatus.OK);
	}
	
}
