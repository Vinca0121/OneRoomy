package com.example.oneroomy.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {
    private Long id;
    // 보증금
    private Long avgDeposit;
    // 월세
    private Long avgMonthly;
    // 거주기간
    private Long avgRentalLength;
}



