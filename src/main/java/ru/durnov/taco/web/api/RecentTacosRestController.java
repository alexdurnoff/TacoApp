package ru.durnov.taco.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ru.durnov.taco.Taco;
import ru.durnov.taco.data.TacoRepository;

import java.util.List;

@RepositoryRestController
public class RecentTacosRestController {
    private TacoRepository tacoRepo;

    public RecentTacosRestController(TacoRepository tacoRepo){
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(path="/tacos/recents", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        CollectionModel<TacoResource> recentResources = new TacoResourceAssembler().toCollectionModel(tacos);
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class)
                .recentTacos()).withRel("recents");
        recentResources.add(link);
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }
}
