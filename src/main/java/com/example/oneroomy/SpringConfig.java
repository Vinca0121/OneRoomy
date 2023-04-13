package com.example.oneroomy;

import com.example.oneroomy.Repository.UserRepository;
import com.example.oneroomy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userrepository;

    @Autowired
    public SpringConfig(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userrepository);
    }
}
