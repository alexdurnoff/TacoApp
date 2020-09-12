package ru.durnov.taco.data;

import lombok.Data;

import org.hibernate.annotations.SQLInsert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Ingredient;

import java.util.List;


@Repository
public interface IngredientRepository extends CrudRepository <Ingredient, String>{

    Ingredient findByname(String name);

}
