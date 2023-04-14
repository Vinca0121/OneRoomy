package com.example.oneroomy.Service;


import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Repository.OneRoomRepository;

public class OneRoomService {

    private final OneRoomRepository oneRoomRepository;

    public OneRoomService(OneRoomRepository oneRoomRepository) {
        this.oneRoomRepository = oneRoomRepository;
    }

    // 원룸 생성
    public OneRoom createOneRoom(OneRoom oneRoom){
        return oneRoomRepository.save(oneRoom);
    }

}
