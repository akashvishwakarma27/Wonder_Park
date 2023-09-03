package com.masai.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketId;
	
	@ManyToOne(cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "activityId")
	private Activity activity;
	
	@CreationTimestamp
	private LocalDate visitDate;
	
	@DecimalMin("0")
	private Double price;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;
	
	
	@Column(nullable = false)
	private Boolean isExpired;
	
	
	@Min(1)
	private Integer personCount;


	public Ticket(Customer customer, Activity activity, LocalDate visitDate, @DecimalMin("0") Double price, Boolean isExpired, @Min(1) Integer personCount) {
		super();
		this.customer = customer;
		this.activity = activity;
		this.visitDate = visitDate;
		this.price = price;
		this.isExpired = isExpired;
		this.personCount = personCount;
	}
   
}
