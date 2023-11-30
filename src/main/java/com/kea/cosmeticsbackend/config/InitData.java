package com.kea.cosmeticsbackend.config;

import com.kea.cosmeticsbackend.model.User;
import com.kea.cosmeticsbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User("admin", "1234");
        userRepository.save(u1);

    }
}
