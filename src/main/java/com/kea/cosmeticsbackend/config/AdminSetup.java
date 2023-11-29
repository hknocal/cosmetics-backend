package com.kea.cosmeticsbackend.config;

import com.kea.cosmeticsbackend.model.User;
import com.kea.cosmeticsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminSetup implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("admin", "1234");
        userService.save(user);
    }
}
