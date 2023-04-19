package com.example.oneroomy.Controller;


import com.example.oneroomy.DTO.OneRoomDTO;
import com.example.oneroomy.Domain.Contract;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.Statistic;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Service.ContractService;
import com.example.oneroomy.Service.OneRoomService;
import com.example.oneroomy.Service.StatisticService;
import com.example.oneroomy.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("OneRoom")
public class OneRoomController {

    private OneRoomService oneRoomService;
    private UserService userService;
    private ContractService contractService;
    private StatisticService statisticService;

    public OneRoomController(OneRoomService oneRoomService, UserService userService, ContractService contractService, StatisticService statisticService) {
        this.oneRoomService = oneRoomService;
        this.userService = userService;
        this.contractService = contractService;
        this.statisticService = statisticService;
    }

    /** 등록페이지로 이동 */
    @GetMapping("/enroll")
    public String goEnrollPage(){
        return "OneRoom/enroll";
    }

    /** 원룸 등록 */
    @PostMapping("/enroll")
    public String registerUser(OneRoomDTO oneRoomDTO, @RequestParam("login_id") Long login_id, @RequestParam("roomPhotoFile") MultipartFile roomPhotoFile)
    {
        if (!roomPhotoFile.isEmpty()) {
            try {
                // 이미지 파일을 저장할 경로 설정

                // 집
//                String uploadDir = "C:/Users/USER/IdeaProjects/OneRoomy/src/main/resources/static/roomPhoto/";
                // 학교
                String uploadDir = "D:/Springs/OneRoomy/src/main/resources/static/roomPhoto/";
                String fileName = roomPhotoFile.getOriginalFilename();
                String filePath = uploadDir + fileName;

                // 이미지 파일을 서버에 저장
                roomPhotoFile.transferTo(new File(filePath));

                // 서버에서 볼(찾을) 이미지 파일의 URL을 OneRoomDTO에 저장
                String imageUrl = "/roomPhoto/" + fileName; // 예시: "/roomPhoto/image.jpg"
                oneRoomDTO.setRoomPhoto(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                // 이미지 파일 저장 실패 시 예외 처리
            }
        }
        System.out.println(login_id);

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

        /*원룸 생성한 유저 찾고, setProvideUser를 통해서 provide_user 외래키에 값 저장*/
        User user = userService.getOneUser(login_id);
        oneRoom.setProvideUser(user);
        /*이후, 원룸 데이터베이스 생성 */
        oneRoomService.createOneRoom(oneRoom);


        /** 2. 원룸 데이터베이스 생성 시, 계약 테이블인 contract 또한 생성되어야 함 */

        // 빌더 시 DB에 저장할 때, 자동으로 id, start_date, end_date 들어감.
        Contract contract = Contract.builder()
                .build();

        // 원룸 생성한 유저를 넘겨서 id 외래키로 저장
        contract.setProvideUser(user);
        // 원룸을 넘겨서 원룸 id 외래키로 저장
        contract.setOneRoom(oneRoom);
        /*이후, 계약 데이터베이스 생성 */
        contractService.createContract(contract);

        /** 3. 원룸 등록 시, 평균 임대 등록된 평균 보증금, 월세, 임대 기간 계산해도록 요청 */
        statisticService.updateStatistic();


        return "redirect:/home?id=" + login_id ;
    }

    @GetMapping("/information")
    public String goInfoPage(@RequestParam("id") Long id, @RequestParam("login_id") Long login_id,  Model model){
        OneRoom oneRoom = oneRoomService.findOneRoomByID(id);
        model.addAttribute("oneRoom",oneRoom);

        // 임대인 임차인 정보를 가져와서 출력해주도록 해야함
        User provide_user = oneRoom.getProvideUser();
        User rental_user = oneRoom.getRentalUser();

        model.addAttribute("provide_user",provide_user);
        model.addAttribute("rental_user",rental_user);


        // 로그인 유저 아이디도 함꼐 넘겨줌
        User user = userService.getOneUser(login_id);
        model.addAttribute("user",user);
        return "OneRoom/information";
    }


    @GetMapping("/delete")
    public String deleteOneRoom(@RequestParam("del_oneroom_id") Long del_oneroom_id, @RequestParam("login_id") Long login_id){

        // 원룸 가져오고
        OneRoom oneRoom = oneRoomService.findOneRoomByID(del_oneroom_id);
        // 원룸의 프로바이더 id가 현재 로그인한 아이디와 일치하는지 검사
        if(oneRoom.getProvideUser().getId() == login_id)
        {
            // 원룸 삭제 전에, 해당 원룸을 알고있는(외래키로 가진) 자식 Contact 튜플을 삭제시켜야함

            // 원룸 삭제하고
            oneRoomService.deleteOneRoom(del_oneroom_id);

            return "redirect:/home?id=" + login_id ;
        }
        else{
            return "Error";
        }
    }


    @GetMapping("/search")
    public String goSearchPage(@RequestParam("login_id") Long login_id, Model model){
        System.out.println("로그인 아이디는 ?");
        System.out.println(login_id);
        /** 로그인한 유저 정보도 모델에 추가해서 전딜 */
        User user = userService.getOneUser(login_id);
        model.addAttribute("user",user);
        return "OneRoom/search";
    }


    @PostMapping("/search")
    public String goSearchResult(OneRoomDTO oneRoomDTO, @RequestParam("login_id") Long login_id, Model model){

        String roomName = oneRoomDTO.getRoomName();
        String roomLocation = oneRoomDTO.getRoomLocations();
        Long roomMonthly = oneRoomDTO.getRoomMonthly();
        Long roomRentLength = oneRoomDTO.getRoomRentLength();

        // 일단 다 가져오자.
        List<OneRoom> oneRoomList = oneRoomService.findAllOneRooms();
        List<OneRoom> resultoneRoomList = new ArrayList<>(); // ArrayList를 사용한 예시
        if(roomMonthly == null) {
            System.out.println("공백이 들어왔을 때 숫자는 null");
        }
        // 이름만 (1)
        if(roomName != "")
        {
            System.out.println("이름");
            System.out.println(roomName);
            resultoneRoomList = oneRoomList.stream()
                    .filter(r -> r.getRoomName().contains(roomName))
                    .collect(Collectors.toList());
            // 이름 + 위치 (2)
            if(roomLocation != "")
            {
                resultoneRoomList = resultoneRoomList.stream()
                        .filter(r -> r.getRoomLocations().contains(roomLocation))
                        .collect(Collectors.toList());
                // 이름 + 워치 +  월세 (3)
                if(roomMonthly != null)
                {
                    resultoneRoomList = resultoneRoomList.stream()
                            .filter(r -> r.getRoomMonthly() <= roomMonthly)
                            .collect(Collectors.toList());
                    // 이름 + 위치 + 월세 + 기간 (4)
                    if(roomRentLength != null)
                    {
                        resultoneRoomList = resultoneRoomList.stream()
                                .filter(r -> roomRentLength >= 7 ? r.getRoomRentLength() > roomRentLength : r.getRoomRentLength() <= roomRentLength)
                                .collect(Collectors.toList());
                    }
                }
            }
            // 이름 + 월세 (5)
            else if(roomMonthly != null)
            {
                resultoneRoomList = resultoneRoomList.stream()
                        .filter(r -> r.getRoomMonthly() <= roomMonthly)
                        .collect(Collectors.toList());
            }
        }

        // 위치만 (6)
        else if(roomLocation != "") {
            System.out.println("위치");
            resultoneRoomList = oneRoomList.stream()
                    .filter(r -> r.getRoomLocations().contains(roomLocation))
                    .collect(Collectors.toList());
            // 위치 + 월세 (7)
            if(roomMonthly != null) {
                resultoneRoomList = resultoneRoomList.stream()
                        .filter(r -> r.getRoomMonthly() <= roomMonthly)
                        .collect(Collectors.toList());
                // 위치 + 월세 + 기간 (8)
                if(roomLocation != "") {
                    resultoneRoomList = resultoneRoomList.stream()
                            .filter(r -> roomRentLength >= 7 ? r.getRoomRentLength() > roomRentLength : r.getRoomRentLength() <= roomRentLength)
                            .collect(Collectors.toList());
                }
            }
            // 위치 + 기간 (9)
            else if(roomRentLength != null)
            {
                resultoneRoomList = resultoneRoomList.stream()
                        .filter(r -> roomRentLength >= 7 ? r.getRoomRentLength() > roomRentLength : r.getRoomRentLength() <= roomRentLength)
                        .collect(Collectors.toList());
            }
        }

        // 월세만 (10)
        else if(roomMonthly != null) {
            System.out.println("월세");
            resultoneRoomList = oneRoomList.stream()
                    .filter(r -> r.getRoomMonthly() <= roomMonthly)
                    .collect(Collectors.toList());
            // 월세 + 기간 (11)
            if(roomRentLength != null) {
                resultoneRoomList = resultoneRoomList.stream()
                        .filter(r -> roomRentLength >= 7 ? r.getRoomRentLength() > roomRentLength : r.getRoomRentLength() <= roomRentLength)
                        .collect(Collectors.toList());
                // 월세 + 기간 + 이름 (12)
                if(roomName != "") {
                    resultoneRoomList = resultoneRoomList.stream()
                            .filter(r -> r.getRoomName().contains(roomName))
                            .collect(Collectors.toList());
                }
            }
        }
        // 기간만 (13)
        else if(roomRentLength != null) {
            System.out.println("기간");
            resultoneRoomList = oneRoomList.stream()
                    .filter(r -> roomRentLength >= 7 ? r.getRoomRentLength() > roomRentLength : r.getRoomRentLength() <= roomRentLength)
                    .collect(Collectors.toList());
            System.out.println("전송된 roomlength는"+roomRentLength.toString());
            // 기간 + 이름 (14)
            if(roomName != "") {
                resultoneRoomList = resultoneRoomList.stream()
                        .filter(r -> r.getRoomName().contains(roomName))
                        .collect(Collectors.toList());
                // 기간 + 이름 + 위치 (15)
                if(roomLocation != "") {
                    resultoneRoomList = resultoneRoomList.stream()
                            .filter(r -> r.getRoomLocations().contains(roomLocation))
                            .collect(Collectors.toList());
                }
            }
        }

        model.addAttribute("roomList",resultoneRoomList);

        /** 로그인한 유저 정보도 모델에 추가해서 전딜 */
        User user = userService.getOneUser(login_id);
        model.addAttribute("user",user);

        return "OneRoom/search";
    }


    @GetMapping("/myRoom")
    public String goMyRoomPage(@RequestParam("login_id") Long login_id, Model model)
    {
        User current_user = userService.getOneUser(login_id);

        // 원룸 DB에서 현재 user가 임대인 또는 임차인으로 등록된 원룸을 가져온다.
        List<OneRoom> roomList = oneRoomService.findOneRoomsByUser(current_user);

        model.addAttribute("roomList",roomList);
        model.addAttribute("user",current_user);
        return "OneRoom/myRoom";
    }
}
