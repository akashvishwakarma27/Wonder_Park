package com.masai.service;

import java.util.List;
import java.util.Optional;

import com.masai.Exception.CustomerException;
import com.masai.model.Customer;

public interface CustomerService {

	Customer registerCustomer(Customer customer)throws CustomerException;
	Customer updateCustomer(Customer customer, Integer customerId) throws CustomerException;
	Customer deleteCustomer(Integer customerId)throws CustomerException;
	List<Customer> viewAllCustomer()throws CustomerException;
	Customer viewCustomerById(Integer customerId)throws CustomerException;
	Optional<Customer> findByEmail(String email)throws CustomerException;
}
