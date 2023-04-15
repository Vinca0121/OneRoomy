package com.example.oneroomy.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OneRoomDTO {
    private Long id;
    private String roomName;
    private Long roomDeposit;
    private Long roomMonthly;
    private String roomLocations;
    private Long roomRentLength;
    /** URL */
    private String roomPhoto;
    private String roomDescription;
    private Long roomContract;
}
