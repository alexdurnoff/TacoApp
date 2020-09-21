package ru.durnov.taco.web.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import ru.durnov.taco.Ingredient;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {



    public IngredientResourceAssembler() {
        super(DesignTacoController.class, IngredientResource.class);
    }


    /**
     * Converts the given entity into a {@code D}, which extends {@link RepresentationModel}.
     *
     * @param entity
     * @return
     */

    @Override
    public IngredientResource toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }

    protected IngredientResource instantiateModel(Ingredient ingredient){
        return new IngredientResource(ingredient);
    }

}
