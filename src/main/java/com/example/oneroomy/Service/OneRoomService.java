package com.example.oneroomy.Service;


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

}
