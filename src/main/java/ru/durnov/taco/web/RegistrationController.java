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


    //Здесь мне непонятно: каким образом объект form привязывался к запросу?!
    //В объявдении формы в html не было этого объекта... Я сам добавил th:object="${form}"...
    //Самое интересное, что ничего не изменилось. Все работало также и без th:object="${form}".
    //Возможно, объект-аргумент метода @PostMapping автоматически заполняется из формы html?
    @PostMapping
    public String processRegistration(RegistrationForm form){
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
