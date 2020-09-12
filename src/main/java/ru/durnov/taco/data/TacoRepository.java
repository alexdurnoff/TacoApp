package ru.durnov.taco.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Taco;

@Repository
public interface TacoRepository extends CrudRepository<Taco, Long> {
	Taco save(Taco design);
}
