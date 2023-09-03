package com.masai.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;



import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class AbstractUser {

	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "password can't be blank")	
	private String password;

	private String address;

	@Column(unique = true, nullable = false)
	@Pattern(regexp = "^[6-9]\\d{9}")
	private String mobileNumber;

	@Column(unique = true, nullable = false)
	@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$" , message = "email should be in a proper format i.e example@domain.com")
	@NotBlank
	private String email;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;
	
	
	@Column(nullable = false)
	private Boolean isDeleted=false;	
	
	
	@Column(nullable = true)
	private LocalDateTime deletionTime;
	
//	@Enumerated(EnumType.STRING)
//	@JsonIgnore
//	private Role role;

	public AbstractUser(String username, @NotBlank(message = "password can't be blank") String password, String address,
			@Pattern(regexp = "^[6-9]\\d{9}") String mobileNumber,
			@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "email should be in a proper format i.e example@domain.com") @NotBlank String email,
			LocalDateTime createdOn, LocalDateTime lastUpdatedOn, LocalDateTime deletionTime) {
		super();
		this.username = username;
		this.password = password;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.deletionTime = deletionTime;
	}
	
	

}