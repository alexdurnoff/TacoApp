package ru.durnov.taco.data;

import lombok.Data;

import ru.durnov.taco.Ingredient;

public interface IngredientRepository {
	Iterable<Ingredient> findAll();
	Ingredient findOne(String id);
	Ingredient save(Ingredient ingredient);
}
