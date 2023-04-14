package com.example.oneroomy.Controller;


import com.example.oneroomy.DTO.OneRoomDTO;
import com.example.oneroomy.DTO.UserDTO;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Service.OneRoomService;
import com.example.oneroomy.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("OneRoom")
public class OneRoomController {

    private OneRoomService oneRoomService;
    private UserService userService;

    public OneRoomController(OneRoomService oneRoomService, UserService userService) {
        this.oneRoomService = oneRoomService;
        this.userService = userService;
    }



    /** 등록페이지로 이동 */
    @GetMapping("/enroll")
    public String goEnrollPage(){
        return "OneRoom/enroll";
    }

    /** 원룸 등록 */
    @PostMapping("/enroll")
    public String registerUser(OneRoomDTO oneRoomDTO, @RequestParam("login_id") Long login_id) {

        log.debug("로그인한 아이디는 >>>>.",login_id.toString());

        // 빌더해서 DB에 저장할 때, 자동으로 id값 들어감.
        OneRoom oneRoom = OneRoom.builder()
                .roomName(oneRoomDTO.getRoomName())
                .roomDeposit(oneRoomDTO.getRoomDeposit())
                .roomMonthly(oneRoomDTO.getRoomMonthly())
                .roomLocations(oneRoomDTO.getRoomLocations())
                .roomRentLength(oneRoomDTO.getRoomRentLength())
                .roomPhoto(oneRoomDTO.getRoomPhoto())
                .roomDescription(oneRoomDTO.getRoomDescription())
                .roomContract(oneRoomDTO.getRoomContract())
                .build();

        /*원룸 생성한 유저 찾기*/
        User user = userService.getOneUser(login_id);
        oneRoom.setProvideUser(user);
        oneRoomService.createOneRoom(oneRoom);
        return "login";
    }
}
