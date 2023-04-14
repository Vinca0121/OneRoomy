package com.example.oneroomy.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@Builder // Builder를 통해서 유연하게 객체를 생성가능.
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp startDate;

    @UpdateTimestamp
    private Timestamp EndDate;

    /** 외래 키 */
    @ManyToOne
    @JoinColumn(name = "provide_user_id") // 외래키 컬럼 명 설정
    private User provideUser;

    @ManyToOne
    @JoinColumn(name = "rental_user_id") // 외래키 컬럼 명 설정
    private User rentalUser;

    @OneToOne
    @JoinColumn(name = "oneroom_id") // 외래키 컬럼 명 설정
    private OneRoom oneRoom;

}
