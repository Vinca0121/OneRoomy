package com.example.oneroomy.Service;

import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    /**안되면 void로*/
    public User createUser(User user){
        return userRepository.save(user);
    }

    public UserService(UserRepository userrepository) {
        this.userRepository = userrepository;
    }

    /**등록된 회원 정보에 일치하는 애가 있는지..*/
    public User getByCredentials(String phonenumber, String password){
        final User originUser = userRepository.findByPhonenumber(phonenumber);
        if(originUser != null && password.equals(originUser.getPassword())){
            return originUser;
        }
        else{
            return null;
        }
    }
}
