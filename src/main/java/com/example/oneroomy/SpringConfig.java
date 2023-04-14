package com.example.oneroomy;

import com.example.oneroomy.Repository.OneRoomRepository;
import com.example.oneroomy.Repository.UserRepository;
import com.example.oneroomy.Service.OneRoomService;
import com.example.oneroomy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    /** User */
    private final UserRepository userrepository;

    /** OneRoom */
    private final OneRoomRepository oneRoomRepository;

    @Autowired
    public SpringConfig(UserRepository userrepository, OneRoomRepository oneRoomRepository) {
        this.userrepository = userrepository;
        this.oneRoomRepository = oneRoomRepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userrepository);
    }

    @Bean
    public OneRoomService oneRoomService(){
        return new OneRoomService(oneRoomRepository);
    }

}
