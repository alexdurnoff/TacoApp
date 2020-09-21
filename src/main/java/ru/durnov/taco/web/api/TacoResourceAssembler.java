package ru.durnov.taco.web.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import ru.durnov.taco.Taco;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {



    public TacoResourceAssembler() {
        super(DesignTacoController.class, TacoResource.class);
    }


    /**
     * Converts the given entity into a {@code D}, which extends {@link RepresentationModel}.
     *
     * @param entity
     * @return
     */

    @Override
    public TacoResource toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity);
    }

    protected TacoResource instantiateModel(Taco taco){
        return new TacoResource(taco);
    }

}
