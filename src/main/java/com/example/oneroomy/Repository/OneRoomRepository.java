package com.example.oneroomy.Repository;

import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OneRoomRepository extends JpaRepository<OneRoom, Long> {

    OneRoom getOne(Long id);

    List<OneRoom> findByProvideUserOrRentalUser(User provideUser, User rentalUser);

}
