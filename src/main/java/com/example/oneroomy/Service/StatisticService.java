package com.example.oneroomy.Service;

import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.Statistic;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Repository.OneRoomRepository;
import com.example.oneroomy.Repository.StatisticRepository;

import java.util.List;

public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final OneRoomRepository oneRoomRepository;


    public StatisticService(StatisticRepository statisticRepository, OneRoomRepository oneRoomRepository) {
        this.statisticRepository = statisticRepository;
        this.oneRoomRepository = oneRoomRepository;
    }

    // 통계 객체 업데이트(새로운 걸로 저장)
    public void updateStatistic(){
        List<OneRoom> oneRoomList = oneRoomRepository.findAll();
        Long avg_roomDeposit = 0L;
        Long avg_roomMonthly = 0L;
        Long avg_roomRentLength = 0L;

        // 보증금, 월세, 임대 기간의 합을 계산
        for (OneRoom oneRoom : oneRoomList) {
            avg_roomDeposit += oneRoom.getRoomDeposit();
            avg_roomMonthly += oneRoom.getRoomMonthly();
            avg_roomRentLength += oneRoom.getRoomRentLength();
        }

        // 평균을 계산
        if (!oneRoomList.isEmpty()) {
            avg_roomDeposit /= oneRoomList.size();
            avg_roomMonthly /= oneRoomList.size();
            avg_roomRentLength /= oneRoomList.size();
        }

        // 계산된 평균값을 DB에 업데이트
        Statistic new_statistic = Statistic.builder()
                        .avgDeposit(avg_roomDeposit)
                        .avgMonthly(avg_roomMonthly)
                        .avgRentalLength(avg_roomRentLength)
                        .build();

        statisticRepository.save(new_statistic);
    }

    // 맨 마지막id로 통계 객체 찾아서 전송
    public Statistic findFinalStatistic(){
        Statistic statistic = statisticRepository.findFirstByOrderByStatisticDateDesc();

        return statistic; // Statistic 객체 반환 (null일 경우에도 null 반환)
    }

    // 아이디 값을 이용해서 통계 객체 반환
    public Statistic findOneStatistic(Long id){
        return statisticRepository.getOne(id);
    }
}
