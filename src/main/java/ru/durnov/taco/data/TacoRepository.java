package ru.durnov.taco.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Taco;

@Repository
public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

}
