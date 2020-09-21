package ru.durnov.taco.web.api;

import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import ru.durnov.taco.Ingredient;

public class IngredientResource extends EntityModel<Ingredient> {
    @Getter
    private final String name;

    @Getter
    private Ingredient.Type type;

    public IngredientResource(Ingredient ingredient){
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }

}
