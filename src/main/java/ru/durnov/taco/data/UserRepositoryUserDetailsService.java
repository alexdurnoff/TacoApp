package ru.durnov.taco.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.durnov.taco.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    //Непонятно, почему вместо UserDetails в методе ниже вернули просто user?! В книге об этом ничего не сказано...
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) return user;
        throw new UsernameNotFoundException("User - \"" + username + "\" не найден");
    }
}
