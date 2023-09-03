
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.Exception.ActivityException;
import com.masai.Exception.AdminException;
import com.masai.Exception.CustomerException;
import com.masai.Exception.TicketException;
import com.masai.model.Activity;
import com.masai.model.Admin;
import com.masai.model.Customer;
import com.masai.model.Ticket;
import com.masai.repository.AdminRepository;
import com.masai.security.JwtToken;
import com.masai.service.ActivityService;
import com.masai.service.AdminService;
import com.masai.service.CustomerService;
import com.masai.service.TicketService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/admin")


public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	@PostMapping("/registerAdmin")
	public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) throws AdminException{
		admin.setIsDeleted(false);
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		Admin ad = adminService.insertAdmin(admin);
		return new ResponseEntity<Admin>(ad, HttpStatus.CREATED);
	}
	
	@GetMapping("/signin")
	public ResponseEntity<String> logInUserHandler(Authentication auth) throws AdminException {
		
		Admin admin = adminService.findByEmail(auth.getName()).get();
		return new ResponseEntity<>(admin.getEmail() + " Logged In Successfully", HttpStatus.ACCEPTED);
	}	
	
	
	
	@DeleteMapping("/delete/{adminId}")
	public ResponseEntity<Admin> deleteAdmin(@PathVariable Integer adminId)throws AdminException{
		return new ResponseEntity<Admin>(adminService.deleteAdmin(adminId),HttpStatus.OK);
	}
	
	@GetMapping("/{adminId}")
	public ResponseEntity<Admin> getAdminById(@PathVariable Integer adminId)throws AdminException{
		return new ResponseEntity<Admin>(adminService.findByAdminId(adminId),HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Admin>> getAllAdmins(@RequestParam Integer pageNumbeer,@RequestParam Integer pageSize)throws AdminException{
		return new ResponseEntity<List<Admin>>(adminService.viewAllAdmin(pageNumbeer, pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomer()throws CustomerException{
		return new ResponseEntity<List<Customer>>(customerService.viewAllCustomer(),HttpStatus.OK);
	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.viewCustomerById(customerId),HttpStatus.OK);
	}
	
	@DeleteMapping("/customers/delete/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer customerId)throws CustomerException{
		return new ResponseEntity<Customer>(customerService.deleteCustomer(customerId),HttpStatus.ACCEPTED);
	}
	
	//Activity
	@PostMapping("/activity/add")
	public ResponseEntity<Activity> addActivity(@Valid @RequestBody Activity activity)throws ActivityException{
		activity.setIsDeleted(false);
		return new ResponseEntity<Activity>(activityService.insertActivity(activity),HttpStatus.CREATED);
	}
	
	@PutMapping("/activity/update/{activityId}")
	public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity,@PathVariable Integer activityId)throws ActivityException{
		return new ResponseEntity<Activity>(activityService.updateActivity(activity, activityId),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/activity/delete/{activityId}")
	public ResponseEntity<Activity> deleteActivity(@PathVariable Integer activityId)throws ActivityException{
		
		return new ResponseEntity<Activity>(activityService.deletedActivity(activityId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/activity/all")
	public ResponseEntity<List<Activity>> viewAllActivity(@RequestParam(name = "pageNumber") Integer pageNumber,@RequestParam(name = "pageSize") Integer pageSize)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.viewAllActivities(pageNumber, pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/activity/{activityId}")
	public ResponseEntity<Activity> getActivityById(@PathVariable Integer activityId)throws ActivityException{
		return new ResponseEntity<Activity>(activityService.getActivityById(activityId),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getActivitiesByCharge")
	public ResponseEntity<List<Activity>> getActivitiesByCharge(@RequestParam Double charge)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getActivitiesByCharges(charge),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getNumberOfActivitiesByCharge/{charge}")
	public ResponseEntity<Integer> getNumOfActByCharge(@PathVariable Double charge)throws ActivityException{
		return new ResponseEntity<Integer>(activityService.getCountNumberOfActivitesByCharge(charge),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getAllActivitiesCustomerNameWise")
	public ResponseEntity<List<Activity>> getAllActivitiesByCustomerName()throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesByCustomerName(),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getAllActivitiesDateWise")
	public ResponseEntity<List<Activity>> getAllActivitiesByDateWise()throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesByDateWise(),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getAllActivitiesByDate")
	public ResponseEntity<List<Activity>> getAllActivitiesByDateTime(
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, 
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end)
					throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesOnBasisOfDate(start, end),HttpStatus.OK);
	}
	
	@GetMapping("/activity/getAllActivitiesOfCustomerByDate/{customerId}/date")
	public ResponseEntity<List<Activity>> getAllActivitiesOfCustomerByDate(@PathVariable Integer customerId,@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start)throws ActivityException{
		return new ResponseEntity<List<Activity>>(activityService.getAllActivitiesForDays(customerId, start),HttpStatus.OK);
	}
	
	//Ticket
	@GetMapping("/ticket/getAllTicket")
	public ResponseEntity<List<Ticket>> getAllTicket()throws TicketException{
		return new ResponseEntity<List<Ticket>>(ticketService.viewAll(),HttpStatus.OK);
	}
	
	@GetMapping("/ticket/{ticketId}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable Integer ticketId)throws TicketException{
		return new ResponseEntity<Ticket>(ticketService.getTicketById(ticketId),HttpStatus.OK);
	}
}

