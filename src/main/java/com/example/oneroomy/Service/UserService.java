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

    /**등록된 회원 정보에 일치하는 애가 있는지.. 리턴은 찾아진 유저*/
    public User getByCredentials(String phonenumber, String password){
        final User originUser = userRepository.findByPhonenumber(phonenumber);
        if(originUser != null && password.equals(originUser.getPassword())){
            return originUser;
        }
        else{
            return null;
        }
    }
    /** id로 유저 찾기 */
    public User getOneUser(Long id){
        return userRepository.getOne(id);
    }

    /** 유저 삭제 */
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
