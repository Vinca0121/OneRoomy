package com.example.oneroomy.Service;

import com.example.oneroomy.Domain.Contract;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Repository.ContractRepository;
import com.example.oneroomy.Repository.OneRoomRepository;

import java.util.List;

public class ContractService {

    private final ContractRepository contractRepository;


    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    // 계약 생성
    public Contract createContract(Contract contract){
        return contractRepository.save(contract);
    }

    // 임차인으로 계약 되어있는 원룸 검색

    public List<Contract> searchRentalContract(User user){
        return contractRepository.findByRentalUser(user);
    }

    // 수정된 계약들을 다시 저장(삭제될 계약들은 null로 변경함)
    public void saveModifiedContracts(List<Contract> rentalContractList)
    {
        contractRepository.saveAll(rentalContractList);
    }

}
