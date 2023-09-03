package com.masai.DTO;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {


	@NotNull(message = "Not a valid Date")
	@Column(name = "visitDatesds")
	private String visitDate;
	
	
	@Column(name = "personCount")
	@Min(value = 1, message = "At least 1 person needed to book a ticket")
	private Integer personCount;

}
