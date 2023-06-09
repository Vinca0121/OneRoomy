package com.example.oneroomy.Repository;

import com.example.oneroomy.Domain.Contract;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByRentalUser(User user);
}


