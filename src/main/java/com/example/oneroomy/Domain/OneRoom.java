package com.example.oneroomy.Domain;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder // Builder를 통해서 유연하게 객체를 생성가능.
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
@ToString
@Table(name = "oneroom")
public class OneRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private Long roomDeposit;
    private Long roomMonthly;
    private String roomLocations;
    private Long roomRentLength;
    private String roomPhoto;
    private String roomDescription;
    private Long roomContract;


    /** 외래 키 */
    @ManyToOne
    @JoinColumn(name = "provideUser_id") // 외래키 컬럼 명 설정
    private User provideUser;

    @ManyToOne
    @JoinColumn(name = "rentalUser_id") // 외래키 컬럼 명 설정
    private User rentalUser;

}
