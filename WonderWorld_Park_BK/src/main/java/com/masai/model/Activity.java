package com.masai.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Data
public class Activity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer activityId;
	
	@Column(unique = true, nullable = false)
	private String activityName;
	
	private Double price;
	
	private String url;
	
	
	
	private Integer personCapacity;
	
	private Integer distance;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;
	
	@Column(nullable = false)
	private Boolean isDeleted;
	
	@JsonIgnore
	@OneToMany( mappedBy = "activity", cascade = CascadeType.ALL)
	private List<Ticket> tickets = new ArrayList<>();

	public Activity(String activityName, Double price, String url, Integer personCapacity, Integer distance,
			LocalDateTime createdOn, LocalDateTime lastUpdatedOn, List<Ticket> tickets) {
		super();
		this.activityName = activityName;
		this.price = price;
		this.url = url;
		this.isDeleted=false;
		this.personCapacity = personCapacity;
		this.distance = distance;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.tickets = tickets;
	}
	
	
	
}
