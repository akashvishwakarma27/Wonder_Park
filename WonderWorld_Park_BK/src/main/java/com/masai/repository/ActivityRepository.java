package com.masai.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.masai.model.Activity;
import com.masai.model.Customer;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query(value = "SELECT a FROM Activity a WHERE a.price <= :charge")
	List<Activity> findByCharge(Double charge);
	
	@Query(value = "SELECT COUNT(a) FROM Activity a WHERE a.price <= :charge")
	int findCountByCharge(Double charge);
	
	 @Query("SELECT a FROM Activity a JOIN a.tickets t JOIN t.customer c ORDER BY c.username ASC")
	    List<Activity> findAllSortedByCustomerName();
	 
	 List<Activity> findAllByOrderByCreatedOnAsc();
	 
	 @Query("SELECT a FROM Activity a JOIN a.tickets t JOIN t.customer c " +
	           "WHERE c.customerId = :customer AND a.createdOn >= :startDate")
	    List<Activity> findActivitiesByCustomerAndDate(Integer customer, LocalDateTime startDate);

	 @Query("SELECT a FROM Activity a WHERE a.createdOn BETWEEN :startDate AND :endDate")
	    List<Activity> findActivitiesForDays(LocalDateTime startDate, LocalDateTime endDate);
	 
	 @Query("SELECT a FROM Activity a JOIN a.tickets t JOIN t.customer c WHERE c.customerId = :customerId")
	    List<Activity> findAllActivitiesByCustomerId(Integer customerId);

}
