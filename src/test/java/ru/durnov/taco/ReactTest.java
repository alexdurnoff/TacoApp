package ru.durnov.taco;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;


public class ReactTest {

    @Test
    public void createAFlux(){
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        fruitFlux.subscribe((e) -> System.out.println("Фрукт из потока - " + e));
    }

    @Test
    public void chechStepVerifier(){
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();

    }

    @Test
    public void createAFlux_fromArray(){
        String[] fruits = new String[]{"Apple", "Orange", "Grape", "Banana",
        "Strawberry"};
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFlux_fromIterable(){
        String[] fruits = new String[]{"Apple", "Orange", "Grape", "Banana",
                "Strawberry"};
        ArrayList<String> fruitsList = new ArrayList<>();
        for (String s : fruits) {
            fruitsList.add(s);
        }
        Flux<String> fruitFlux = Flux.fromIterable(fruitsList);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFlux_fromStream(){
        String[] fruits = new String[]{"Apple", "Orange", "Grape", "Banana",
                "Strawberry"};
        Stream<String> fruitStream = Arrays.stream(fruits);
        Flux<String> fruitFlux = Flux.fromStream(fruitStream);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public  void createAFlux_range(){
        Flux<Integer> intervalFlux = Flux.range(1,5);
        StepVerifier.create(intervalFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    public void createAFlux_interval(){
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }

    @Test
    public void myTestIntervalFlux(){
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
        Stream<Long> myStream = intervalFlux.toStream();
        myStream.forEach((e) -> System.out.println(e));
    }

    @Test
    public void mergeFluxes(){
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbosa")
                .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux.just("Lasagna", "Lolipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));
        Flux<String> megaFlux = characterFlux.mergeWith(foodFlux);
        Stream<String> megaStream = megaFlux.toStream();
        megaStream.forEach((e) -> System.out.println(e));

    }

    @Test
    public void zipFluxes(){
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbosa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lolipops", "Apples");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux,foodFlux);
        StepVerifier.create(zippedFlux)
                .expectNextMatches(p -> p.getT1().equals("Garfield") &&
                        p.getT2().equals("Lasagna"))
                .expectNextMatches(p -> p.getT1().equals("Kojak") &&
                        p.getT2().equals("Lolipops"))
                .expectNextMatches(p -> p.getT1().equals("Barbosa") &&
                        p.getT2().equals("Apples"))
                .verifyComplete();
    }

    @Test
    public void zipFluxesToObject(){
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbosa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lolipops", "Apples");

        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux,
                (c,f) -> c + " eats " + f);
        Stream<String> zippedStream = zippedFlux.toStream();
        zippedStream.forEach((e) -> System.out.println(e));
    }

    @Test
    public void firstFlux(){
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
        Flux<String> firstFlux = Flux.first(slowFlux,fastFlux);
        Stream<String> firstStream = firstFlux.toStream();
        firstStream.forEach(e -> System.out.println(e));

    }

    @Test
    public void skipAFew(){
        Flux<String> skipFlux = Flux.just("one", "two", "skip a few", "ninety nine",
                "one hundred")
                .skip(3);
        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void skipAFewSeconds(){
        Flux<String> skipFlux = Flux.just("one", "two", "skip a few", "ninety nine",
                "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));
        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void take() {
        Flux<String> nationalParkFlux = Flux.just(
                "Yellowstone", "Yosemite", "Grand Canyon",
                "Zion", "Grand Teton")
                .take(3);
        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void takeOnSeconds() {
        Flux<String> nationalParkFlux = Flux.just(
                "Yellowstone", "Yosemite", "Grand Canyon",
                "Zion", "Grand Teton")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));
        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void filter(){
        Flux<String> nationalParkFlux = Flux.just(
                "Yellowstone", "Yosemite", "Grand Canyon",
                "Zion", "Grand Teton")
                .filter(np -> !np.contains(" "));
        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Zion")
                .verifyComplete();
    }

    @Test
    public void distinct(){
        
    }

}
