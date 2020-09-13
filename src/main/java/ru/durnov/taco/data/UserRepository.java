package ru.durnov.taco.data;

import org.springframework.data.repository.CrudRepository;
import ru.durnov.taco.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
