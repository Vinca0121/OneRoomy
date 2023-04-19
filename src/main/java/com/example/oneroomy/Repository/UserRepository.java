package com.example.oneroomy.Repository;

import com.example.oneroomy.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhonenumber(String phonenumber);
//    Boolean existsByEmail(String email);
//    UserEntity findByEmailAndPassword(String email, String password);
    @Override
    User getOne(Long id);

    @Override
    void deleteById(Long id);


}
