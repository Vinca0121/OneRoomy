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

    // 계약 삭제 - 원룸 삭제 시 함께 이루어지도록 한다.




}
