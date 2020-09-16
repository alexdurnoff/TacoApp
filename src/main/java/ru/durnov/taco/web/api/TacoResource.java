package ru.durnov.taco.web.api;

import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import ru.durnov.taco.Ingredient;
import ru.durnov.taco.Taco;

import java.util.Date;
import java.util.List;

public class TacoResource extends EntityModel<Taco> {
    @Getter
    private final String name;

    @Getter
    private final Date createAt;

    @Getter
    private final List<Ingredient> ingredients;

    public TacoResource(Taco taco){
        this.name = taco.getName();
        this.createAt = taco.getCreateAt();
        this.ingredients = taco.getIngredients();
    }

}
