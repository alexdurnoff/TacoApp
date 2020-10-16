package ru.durnov.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Ingredient;




@Repository
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String>{
}
