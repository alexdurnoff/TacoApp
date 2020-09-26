package ru.durnov.taco;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@RestResource(rel="tacos", path="tacos")
public class Taco {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Date createAt;
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	@ManyToMany(targetEntity=Ingredient.class)
	@Size(min=1, message="You must choose at least one ingredient")
	private List<Ingredient> ingredients;
	
	@PrePersist
	void createdAt() {
		this.createAt = new Date();
	}

}
