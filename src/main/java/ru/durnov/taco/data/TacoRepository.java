package ru.durnov.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Taco;

@Repository
public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {

}
