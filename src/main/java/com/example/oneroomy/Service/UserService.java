package com.example.oneroomy.Service;

import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Repository.UserRepository;
import com.example.oneroomy.DTO.UserDTO;

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

    public User getUserByPhone(String phonenumber)
    {
        return userRepository.findByPhonenumber(phonenumber);
    }

    /** 유저 정보 업데이트 */
    public void updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setId(userDTO.getId());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setPhonenumber(userDTO.getPhonenumber());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setLocations(userDTO.getLocations());
        existingUser.setUniversity(userDTO.getUniversity());
        userRepository.save(existingUser);
    }

}
