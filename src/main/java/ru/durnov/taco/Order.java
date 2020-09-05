package ru.durnov.taco;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;

@Data
public class Order {
	@NotBlank(message="name is requred")
	private String name;
	@NotBlank(message="street is requred")
	private String street;
	@NotBlank(message="city is requred")
	private String city;
	@NotBlank(message="state is requred")
	private String state;
	@NotBlank(message="Zip code is requred")
	private String zip;
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;
	//@Pattern(regexp="^(0[1-9]|1[0-2]) ([\\/])([1-9][0-9])$", message="Must be formatted MM/YY")
	private String ccExpiration;
	@Digits (integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
}
