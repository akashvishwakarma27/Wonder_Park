package com.masai.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masai.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	
	@Query("SELECT SUM(t.personCount) FROM Ticket t WHERE t.visitDate = :localDate AND t.activity.activityId = :activityId")
	public Integer getPersonCountOfActivityForDate(@Param("localDate") LocalDate localDate, @Param("activityId") Integer activityId);
	
}
