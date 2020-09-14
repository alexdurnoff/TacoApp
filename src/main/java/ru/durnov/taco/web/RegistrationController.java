package ru.durnov.taco.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.durnov.taco.User;
import ru.durnov.taco.data.UserRepository;
import ru.durnov.taco.security.RegistrationForm;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        log.info(form.getUsername());
        userRepo.save(form.toUser(passwordEncoder));
        Iterable<User> users = userRepo.findAll();
        for (User user : users) {
            log.info(String.valueOf(user.getId()));
            log.info(user.getUsername());
            log.info(user.getPhoneNumber());
            log.info(user.getCity());
            log.info(user.getState());
            log.info(user.getStreet());
            log.info(user.getZip());
        }
        return "redirect:/login";
    }
}
