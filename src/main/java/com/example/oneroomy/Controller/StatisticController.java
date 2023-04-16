package com.example.oneroomy.Controller;


import com.example.oneroomy.Service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("Contract")
public class StatisticController {

    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    // 원룸을 등록할 때, 자동으로 임대 등록된 평균 보증금, 월세, 임대 기간 계산해서
    // 데이터베이스에 저장하기.
}
