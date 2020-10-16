package ru.durnov.taco;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.durnov.taco.data.TacoRepository;
import ru.durnov.taco.web.api.DesignTacoController;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class DesignTacoControlTest {

    @Test
    public void shouldReturnRecentTacos(){
        Taco[] tacos = {
                testTaco(1L), testTaco(2L),
                testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L),
                testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L),
                testTaco(15L), testTaco(16L),
        };
        Flux<Taco> tacoFlux = Flux.just(tacos);
        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient.bindToController(
                new DesignTacoController(tacoRepo)).build();
        testClient.get().uri("/design/recents")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Taco 1")
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo("Taco 2")
                .jsonPath("$[2].id").isEqualTo(tacos[2].getId().toString())
                .jsonPath("$[2].name").isEqualTo("Taco 3")
                .jsonPath("$[3].id").isEqualTo(tacos[3].getId().toString())
                .jsonPath("$[3].name").isEqualTo("Taco 4")
                .jsonPath("$[4].id").isEqualTo(tacos[4].getId().toString())
                .jsonPath("$[4].name").isEqualTo("Taco 5")
                .jsonPath("$[5].id").isEqualTo(tacos[5].getId().toString())
                .jsonPath("$[5].name").isEqualTo("Taco 6")
                .jsonPath("$[6].id").isEqualTo(tacos[6].getId().toString())
                .jsonPath("$[6].name").isEqualTo("Taco 7")
                .jsonPath("$[7].id").isEqualTo(tacos[7].getId().toString())
                .jsonPath("$[7].name").isEqualTo("Taco 8")
                .jsonPath("$[8].id").isEqualTo(tacos[8].getId().toString())
                .jsonPath("$[8].name").isEqualTo("Taco 9")
                .jsonPath("$[9].id").isEqualTo(tacos[9].getId().toString())
                .jsonPath("$[9].name").isEqualTo("Taco 10")
                .jsonPath("$[10].id").isEqualTo(tacos[10].getId().toString())
                .jsonPath("$[10].name").isEqualTo("Taco 11")
                .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
                .jsonPath("$[11].name").isEqualTo("Taco 12")
                .jsonPath("$[12]").doesNotExist();
    }

    private Taco testTaco(Long number){
        Taco taco =  new Taco();
        taco.setId(number);
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(
                new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(
                new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);

        return taco;
    }
}
