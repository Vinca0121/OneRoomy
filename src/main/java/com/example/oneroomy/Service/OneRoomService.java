package com.example.oneroomy.Service;


import com.example.oneroomy.Domain.Contract;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Repository.OneRoomRepository;

import java.util.List;

public class OneRoomService {

    private final OneRoomRepository oneRoomRepository;

    public OneRoomService(OneRoomRepository oneRoomRepository) {
        this.oneRoomRepository = oneRoomRepository;
    }

    // 원룸 생성
    public OneRoom createOneRoom(OneRoom oneRoom){
        return oneRoomRepository.save(oneRoom);
    }

    // 원룸 하나 찾기
    public OneRoom findOneRoomByID(Long id){
        return oneRoomRepository.getOne(id);
    }

    // 원룸 전체 찾기
    public List<OneRoom> findAllOneRooms(){
        return oneRoomRepository.findAll();
    }

    // 원룸 하나 삭제
    public void deleteOneRoom(Long id){
        oneRoomRepository.deleteById(id);
    }

    // USER를 사용해서 원룸 찾기
    // User 정보를 기반으로 해당하는 OneRoom 리스트를 조회하는 메서드
    public List<OneRoom> findOneRoomsByUser(User user) {
        return oneRoomRepository.findByProvideUserOrRentalUser(user, user);
    }


    // 임차인으로 계약된 원룸 검색
    public List<OneRoom> searchRentalOneRoom(User user){
        return oneRoomRepository.findByRentalUser(user);
    }

    // 수정된 원룸 정보 저장 (임차인 삭제)
    public void saveModifiedContracts(List<OneRoom> rentalOneRoomList)
    {
        oneRoomRepository.saveAll(rentalOneRoomList);
    }


}
