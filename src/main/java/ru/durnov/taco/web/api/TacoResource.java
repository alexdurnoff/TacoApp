package ru.durnov.taco.web.api;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;
import ru.durnov.taco.Taco;

import java.util.Date;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResource extends EntityModel<Taco> {
    private static final IngredientResourceAssembler ingredientResourceAssembler = new IngredientResourceAssembler();

    @Getter
    private final String name;

    @Getter
    private final Date createAt;


    //private final List<IngredientResource> ingredients;
    @Getter
    private final CollectionModel<IngredientResource> ingredients;

    public TacoResource(Taco taco){
        this.name = taco.getName();
        this.createAt = taco.getCreateAt();
        //this.ingredients = new ArrayList<>();
        //List<Ingredient> tacoIngredients = taco.getIngredients();
        //В связи с новой api в hateoas у меня туповато-кривой конструктор. Но должно работать. Не понял пока,
        //как сделать изящно...
        /*for (Ingredient ingredient:tacoIngredients){
            ingredients.add(ingredientResourceAssembler.toModel(ingredient));
        }*/
        //Сейчас попробую изящно))) Меняю поле с типа List на CollectionModel
        this.ingredients = ingredientResourceAssembler.toCollectionModel(taco.getIngredients());
        //Вот так, вроде, правильно.
    }
}
