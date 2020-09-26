package ru.durnov.taco.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Ingredient;




@Repository
public interface IngredientRepository extends CrudRepository <Ingredient, String>{

    Ingredient findByname(String name);

}
