package com.masai.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.masai.DTO.TicketDTO;
import com.masai.Exception.ActivityException;
import com.masai.Exception.CustomerException;
import com.masai.Exception.TicketException;
import com.masai.model.Activity;
import com.masai.model.Customer;
import com.masai.model.Ticket;
import com.masai.repository.ActivityRepository;
import com.masai.repository.CustomerRepository;
import com.masai.repository.TicketRepository;


@Service
public class TicketServiceImpl implements TicketService {

	
	private TicketRepository ticketRepo;
	private ActivityRepository activityRepo;
	private CustomerRepository customerRepo;
	
	@Autowired
	public TicketServiceImpl(TicketRepository ticketRepo, ActivityRepository activityRepo,
			CustomerRepository customerRepo) {
		
		this.ticketRepo = ticketRepo;
		this.activityRepo = activityRepo;
		this.customerRepo = customerRepo;
	}

	@Override
	public Ticket createTicket(Integer customerId, Integer activityId, TicketDTO ticketDTO) {
		
		Optional<Activity> activity =  activityRepo.findById(activityId);
		
		Optional<Customer> customer = customerRepo.findById(customerId);
		
		if(activity.isEmpty() || (activity.isPresent() && activity.get().getIsDeleted() == true))
			
			throw new  TicketException("Activity with activityId: "+ activityId +" not found");
		 
		
		if(customer.isEmpty() || (customer.isPresent() && customer.get().getIsDeleted() == true))
			
			throw new CustomerException("User not found with userId: "+customerId);

		LocalDate date = LocalDate.parse(ticketDTO.getVisitDate());
		
		if(date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()))
		{
			Integer seats = activity.get().getPersonCapacity();
			
			Integer value = ticketRepo.getPersonCountOfActivityForDate(date, activityId);
			
			if(value == null) value=0;
			
            Integer availableSeats = activity.get().getPersonCapacity() - value;
			
			if(availableSeats == 0) throw new TicketException("All tickets are sold for the day");
			if(availableSeats < ticketDTO.getPersonCount()) throw new TicketException("Only " + availableSeats + " seats are remaining");
			
			Ticket ticket = new Ticket(customer.get(),
					                   activity.get(), 
					                   date,
					                   (activity.get().getPrice()*ticketDTO.getPersonCount())+ (activity.get().getDistance()*10) ,
					                   false,
					                   ticketDTO.getPersonCount());
			
			customer.get().getTickets().add(ticket);
			activity.get().getTickets().add(ticket);
			
			return ticketRepo.save(ticket);
		}
		else
		{
			throw new TicketException("Visiting date should be present or future");
		}
	}

	@Override
	public Ticket updateTicket(Integer customerId, Integer ticketId, TicketDTO ticketDTO) {
		
		
		Optional<Customer> customer = customerRepo.findById(customerId);
		Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
		
		if(customer.isEmpty() || (customer.isPresent() && customer.get().getIsDeleted() == true)) 
			 throw new CustomerException("User not found with userId: "+customerId);
		
		
		if(ticketOpt.isEmpty()) throw new CustomerException("Ticket not found with ticketId: "+ ticketId);
		
		if(ticketOpt.get().getCustomer().getCustomerId() != customerId) 
			
			 throw new CustomerException("Not your ticket");
		
		if(ticketOpt.get().getVisitDate().isBefore(LocalDate.now())) 
		{
		  ticketOpt.get().setIsExpired(true);
		   ticketRepo.save(ticketOpt.get());
			throw new TicketException("Can't modify, Ticket is expired");	
		}
		Ticket ticket = ticketOpt.get();
		ticket.setPrice((ticketDTO.getPersonCount() * ticket.getActivity().getPrice())+ (10* ticket.getActivity().getDistance()));
		ticket.setPersonCount(ticketDTO.getPersonCount());
		
		LocalDate date = LocalDate.parse(ticketDTO.getVisitDate());
		
		if(date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()))
		{
			
			ticket.setVisitDate(date);
		}else
			throw new TicketException("Visiting date should be present or future");
		
		
		return ticketRepo.save(ticket);
	}

	@Override
	public Ticket getTicketById(Integer customerId, Integer ticketId) {
		Optional<Customer> customer = customerRepo.findById(customerId);
		Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
		
		if(customer.isEmpty() || (customer.isPresent() && customer.get().getIsDeleted() == true))
			  throw new CustomerException("User not found with userId: "+customerId);
		if(ticketOpt.isEmpty()) 
			  throw new CustomerException("Ticket not found with ticketId: "+ ticketId);
		
		if(ticketOpt.get().getCustomer().getCustomerId() != customerId) 
			  throw new CustomerException("Not your ticket");
		
		if(ticketOpt.get().getVisitDate().isBefore(LocalDate.now()))
			 ticketOpt.get().setIsExpired(true);
		
		ticketRepo.save(ticketOpt.get());
		
		return ticketOpt.get();
	}

	@Override
	public Boolean deleteTicket(Integer customerId, Integer ticketId) {
		Optional<Customer> customer = customerRepo.findById(customerId);
		Optional<Ticket> ticketOpt = ticketRepo.findById(ticketId);
		
		if(customer.isEmpty() || (customer.isPresent() && customer.get().getIsDeleted() == true))
			throw new CustomerException("User not found with userId: "+customerId);
		
		
		if(ticketOpt.isEmpty()) 
			throw new CustomerException("Ticket not found with ticketId: "+ ticketId);
		
		if(ticketOpt.get().getCustomer().getCustomerId() != customerId) 
			
			throw new CustomerException("Invalid Customer for ticket");

		if(ticketOpt.get().getVisitDate().isBefore(LocalDate.now()))
            
		{
			ticketOpt.get().setIsExpired(true);
		
		    ticketRepo.save(ticketOpt.get());
		    
			throw new TicketException("Ticket is already expired");
		}
			
	
		
		customer.get().setTickets(customer.get().getTickets().stream().filter(s->s.getTicketId()!=ticketId).toList());
		
		Activity activity = ticketOpt.get().getActivity();
		
		activity.setTickets(activity.getTickets().stream().filter(s->s.getTicketId()!=ticketId).toList());
		
		ticketRepo.delete(ticketOpt.get());
		
		if(ticketRepo.findById(ticketId).isEmpty()) return false;
		else return true;
	}

	@Override
	public List<Ticket> viewAllTickets(Integer pageNumber, Integer itemsPerPage) {
		List<Ticket> t = ticketRepo.findAll(PageRequest.of(pageNumber-1, itemsPerPage)).getContent();
		if(t.isEmpty()) 
			  throw new TicketException("Don't have Ticket booking History");
		
		
		return t;
	}

	@Override
	public List<Ticket> getTicketBookingHistory(Integer customerId, Integer pageNumber, Integer itemsPerPage) {
     
		Optional<Customer> customer = customerRepo.findById(customerId);
		
		if(customer.isEmpty() || (customer.isPresent() && customer.get().getIsDeleted() == true))
			throw new CustomerException("User not found with userId: "+customerId);
		
		List<Ticket> t = ticketRepo.findAll(PageRequest.of(pageNumber-1, itemsPerPage)).getContent();
		if(t.isEmpty()) 
			  throw new TicketException("Don't have Ticket booking History");
		
		return t.stream().filter(tick->tick.getCustomer().getCustomerId()==customerId).toList();
	}
	
	@Override
	public List<Ticket> getTicketBookingHistoryForDay(Integer customerId) {
     
		Optional<Customer> customer = customerRepo.findById(customerId);
		
		if(customer.isEmpty() || (customer.isPresent() && customer.get().getIsDeleted() == true))
			throw new CustomerException("User not found with userId: "+customerId);
		
		List<Ticket> t = ticketRepo.findAll();
		if(t.isEmpty()) 
			  throw new TicketException("Don't have Ticket booking History");
		
		return t.stream().filter(tick->tick.getCustomer().getCustomerId()==customerId && tick.getVisitDate().isBefore(LocalDate.now())).toList();
	}
	
	@Override
	public Double getTotalFair(Integer customerId)
	{
		List<Ticket> tickets = getTicketBookingHistoryForDay(customerId);
		
		
		Double fair =0.0;
	  
		
		for(Ticket t :tickets)
		{
			fair+=t.getPrice();
		}
		
		return fair;
	}

	@Override
	public List<Ticket> viewAll() throws TicketException{
		List<Ticket> list=ticketRepo.findAll();
		if(list.isEmpty())throw new TicketException("No Ticket Found");
		return list;
	}

	@Override
	public Ticket getTicketById(Integer ticketId) throws TicketException{
		
		return ticketRepo.findById(ticketId).orElseThrow(()-> new TicketException("No Ticket Found"));
	}
	

}
