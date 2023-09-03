package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.Exception.AdminException;
import com.masai.Exception.CustomerException;
import com.masai.model.Admin;
import com.masai.model.Customer;
import com.masai.model.Role;
import com.masai.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public Customer registerCustomer(Customer customer) throws CustomerException {
		log.debug("Calling findByEmail method from CustomerJpa Repository");
		Optional<Customer> c=customerRepo.findByEmail(customer.getEmail());
		if(c.isPresent())throw new CustomerException("Email is already present");
		log.debug("Calling save method from CustomerJpa Repository");
//		customer.setRole(Role.USER);
		customer.setCreatedOn(LocalDateTime.now());
		Customer main= customerRepo.save(customer);
		log.info("Customer is saved sucessfully");
		return main;
	}

	@Override
	public Customer updateCustomer(Customer customer, Integer customerId) throws CustomerException {
		log.debug("Calling findById method from CustomerJpa Repository");
		Optional<Customer> c=customerRepo.findById(customerId);
		if(c.isPresent()) {
			Customer existingCustomer=c.get();
			existingCustomer.setAddress(customer.getAddress());
			existingCustomer.setMobileNumber(customer.getMobileNumber());
			existingCustomer.setUsername(customer.getUsername());
			
			log.info("Customer address, its mobile numbeer , and its username updated sucessfully");
			return existingCustomer;
		}else {
			throw new CustomerException("No customer found by this customer id");
		}
		
		
	}

	@Override
	public Customer deleteCustomer(Integer customerId) throws CustomerException {
		log.debug("Calling findById method from CustomerJpa Repository");
		Customer c=customerRepo.findById(customerId).orElseThrow(()->new CustomerException("No customer found by this customer id"));
		log.debug("Calling customer is deleted method from CustomerJpa Repository");
		
		c.setIsDeleted(true);
		c.setDeletionTime(LocalDateTime.now());
		customerRepo.delete(c);
		log.info("Customer deleted sucessfully");
		
		return c;
	}

	@Override
	public List<Customer> viewAllCustomer() throws CustomerException {
		log.debug("Calling findAll method from CustomerJpa Repository");
		List<Customer> customers = customerRepo.findAll();
		if(customers.isEmpty())throw new CustomerException("No Customer found");
		
		return customers;
	}

	@Override
	public Customer viewCustomerById(Integer customerId) throws CustomerException {
		log.debug("Calling findById method from CustomerJpa Repository");
		return customerRepo.findById(customerId).orElseThrow(()->new CustomerException("No customer found by this customer id"));
	}
	
	
	@Override
	public Optional<Customer> findByEmail(String email) throws CustomerException{
		Optional<Customer> user= customerRepo.findByEmail(email);
		 if(user.isEmpty()) throw new CustomerException("No Customer found");
		 return user;
	}

}
