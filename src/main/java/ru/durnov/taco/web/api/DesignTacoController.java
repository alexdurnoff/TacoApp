package ru.durnov.taco.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.durnov.taco.Ingredient;
import ru.durnov.taco.Taco;
import ru.durnov.taco.data.TacoRepository;
import ru.durnov.taco.data.IngredientRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
    private TacoRepository tacoRepo;
    private IngredientRepository ingredientRepo;

    @Autowired
    EntityLinks entityLinks;

    public DesignTacoController(TacoRepository tacoRepo, IngredientRepository ingredientRepo){
        this.tacoRepo = tacoRepo;
        this.ingredientRepo = ingredientRepo;
    }


    //Выяснилось, что классы Resources, Resource заменены теперь на CollectionModel, EntityModel
    //А класс ControllerLinkBuilder теперь deprecated. Пока так, потом попробуем по-другому.
    //Ну вот, вместо ControllerLinkBuilder теперь WebMvcLinkBuilder.
    @GetMapping("/recents")
    public CollectionModel<TacoResource> recentTacos() {
        Taco myTaco = new Taco();
        myTaco.setName("BullShit");
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for (Ingredient ingredient:ingredientRepo.findAll()){
            myIngredients.add(ingredient);
        }
        myTaco.setIngredients(myIngredients);
        tacoRepo.save(myTaco);
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
            //List<TacoResource> tacoResources = (List<TacoResource>) new TacoResourceAssembler().toCollectionModel(tacos);
            //CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
            //CollectionModel<TacoResource> recentResources = CollectionModel.of(tacoResources);
            /*Из трехпредыдущих строк первые две - мое творчество. Вернулся к этому классу через три дня,
             * и уже не помню, чего я хотел тогда сказать. Там был uncheckedCust, кстати. Тогда как
             * из класса TacoResourceAssembler можно получить CollectionModel просто в лоб... И вроде бы
             * все красиво вырисовывается...*/
        CollectionModel<TacoResource> recentResources = new TacoResourceAssembler().toCollectionModel(tacos);
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class)
                .recentTacos()).withRel("recents");
        recentResources.add(link);
        log.info(recentResources.toString());
        log.info(recentResources.getContent().toString());
        return recentResources;
    }

    @GetMapping("/ingredients")
    public CollectionModel<IngredientResource> getIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
       for(Ingredient ingredient:ingredientRepo.findAll()){
            ingredients.add(ingredient);
       }
        log.info(ingredients.toString());
        CollectionModel<IngredientResource> ingredientResources = new IngredientResourceAssembler().toCollectionModel(ingredients);
        log.info(ingredientResources.toString());
        return ingredientResources;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco = tacoRepo.findById(id);
        return optTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
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
