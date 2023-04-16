package com.example.oneroomy.Domain;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@Builder // Builder를 통해서 유연하게 객체를 생성가능.
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 보증금
    Long avgDeposit;
    // 월세
    Long avgMonthly;
    // 거주기간
    Long avgRentalLength;
    // 통계 날짜
    @CreationTimestamp
    private Timestamp statisticDate;
}
