package ru.durnov.taco.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.durnov.taco.Taco;
import ru.durnov.taco.data.TacoRepository;

@Slf4j
@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
    private TacoRepository tacoRepo;


    public DesignTacoController(TacoRepository tacoRepo){
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recents")
    public Flux<Taco> recentTacos(){
        return tacoRepo.findAll().take(12);
    }

    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") Long id){
       return tacoRepo.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono){
        return tacoRepo.saveAll(tacoMono).next();
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
