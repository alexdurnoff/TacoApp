package ru.durnov.taco.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.durnov.taco.Taco;
import ru.durnov.taco.data.TacoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
    private TacoRepository tacoRepo;

    @Autowired
    EntityLinks entityLinks;

    public DesignTacoController(TacoRepository tacoRepo){
        this.tacoRepo = tacoRepo;
    }


    //Выяснилось, что классы Resources, Resource заменены теперь на CollectionModel, EntityModel
    //А класс ControllerLinkBuilder теперь deprecated. Пока так, потом попробуем по-другому.
    //Ну вот, вместо ControllerLinkBuilder теперь WebMvcLinkBuilder.
    @GetMapping("/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        List<TacoResource> tacoResources = (List<TacoResource>) new TacoResourceAssembler().toCollectionModel(tacos);
        //CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
        CollectionModel<TacoResource> recentResources = new CollectionModel<>(tacoResources);
        Link link = WebMvcLinkBuilder.linkTo(DesignTacoController.class)
                .slash("recent")
                .withRel("recents");
        recentResources.add(link);
        return recentResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco = tacoRepo.findById(id);
        return optTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepo.save(taco);
    }

    @DeleteMapping("/{tacoId}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable ("tacoId") Long tacoId){
        try {
            tacoRepo.deleteById(tacoId);
        } catch (EmptyResultDataAccessException exception){

        }

    }



}
