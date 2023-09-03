package com.masai.service;

import java.util.List;

import com.masai.DTO.TicketDTO;
import com.masai.Exception.TicketException;
import com.masai.model.Ticket;

public interface TicketService {
	public Ticket createTicket(Integer customerId, Integer activityId, TicketDTO ticketDTO) ;

	public Ticket updateTicket(Integer customerId, Integer ticketId, TicketDTO
			ticketDTO) ;
	
	public Ticket getTicketById(Integer customerId, Integer ticketId) ;
	
	public Boolean deleteTicket(Integer customerId, Integer ticketId) ;
	
	public List<Ticket> viewAllTickets(Integer pageNumber, Integer itemsPerPage) ;
	
	public List<Ticket> getTicketBookingHistory(Integer customerId, Integer pageNumber, Integer itemsPerPage) ;

	List<Ticket> getTicketBookingHistoryForDay(Integer customerId);

	Double getTotalFair(Integer customerId);
	
	List<Ticket> viewAll()throws TicketException;
	
	Ticket getTicketById(Integer ticketId)throws TicketException;

}
