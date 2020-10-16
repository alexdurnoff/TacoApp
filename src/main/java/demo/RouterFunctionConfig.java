package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.durnov.taco.Taco;
import ru.durnov.taco.data.TacoRepository;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.just;

@Configuration
public class RouterFunctionConfig {
    @Autowired
    private TacoRepository tacoRepo;
    @Bean
    public RouterFunction<?> helloRouterFunction(){
        return route(GET("/hello"),
                request -> ok().body(just("Hello World"), String.class))
                .andRoute(GET("/bye"),
                        serverRequest -> ok().body(just("See ya!"), String.class));
    }

    @Bean
    public RouterFunction<?> routerFunction(){
        return route(GET("/design/taco"),
                serverRequest -> ok().body(tacoRepo.findAll().take(12), Taco.class))
                .andRoute(POST("/design"), this::postTaco);
    }

    public Mono<ServerResponse> postTaco(ServerRequest serverRequest){
        Mono<Taco> taco = serverRequest.bodyToMono(Taco.class);
        Mono<Taco> savedTaco = tacoRepo.save(taco.block());//В книге .block нет!!!
        return ServerResponse.created(URI.create("http://localhost:8080/design/taco/" +
                savedTaco.block().getId())).body(savedTaco, Taco.class);//В книге .block нет!!!
    }
}
