package com.example.oneroomy.Service;

import com.example.oneroomy.Domain.Contract;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Repository.ContractRepository;
import com.example.oneroomy.Repository.OneRoomRepository;

public class ContractService {

    private final ContractRepository contractRepository;


    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    // 계약 생성
    public Contract createContract(Contract contract){
        return contractRepository.save(contract);
    }

}
