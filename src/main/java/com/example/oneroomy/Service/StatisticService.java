package com.example.oneroomy.Service;

import com.example.oneroomy.Repository.StatisticRepository;

public class StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }
}
