package com.example.oneroomy.Controller;


import com.example.oneroomy.Domain.Contract;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Service.ContractService;
import com.example.oneroomy.Service.OneRoomService;
import com.example.oneroomy.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("Contract")
public class ContractController {

    private ContractService contractService;
    private OneRoomService oneRoomService;
    private UserService userService;

    public ContractController(ContractService contractService, OneRoomService oneRoomService, UserService userService) {
        this.contractService = contractService;
        this.oneRoomService = oneRoomService;
        this.userService = userService;
    }

    @GetMapping("/startContract")
    public String startContract(@RequestParam("oneroom_id") Long oneroom_id, @RequestParam("login_id") Long login_id, Model model){

        // 선택된 원룸
        OneRoom oneRoom = oneRoomService.findOneRoomByID(oneroom_id);
        // 선택된 원룸에 대한 계약
        Contract contract = oneRoom.getContract();
        // 로그인한 사용자 (현재 계약을 요청하는 사람)
        User rentalUser = userService.getOneUser(login_id);

        /** rentalUser 외래키 등록 */
        // rental 유저 정보를 외래키로 저장
        oneRoom.setRentalUser(rentalUser);
        contract.setRentalUser(rentalUser);

        /** Room 계약 상태 변경 */
        oneRoom.setRoomContract(1L);

        /** DB에 OneRoom, Contract 업데이트 */
        oneRoomService.createOneRoom(oneRoom);
        contractService.createContract(contract);

        User provide_user = oneRoom.getProvideUser();
        User rental_user = oneRoom.getRentalUser();

        model.addAttribute("oneRoom",oneRoom);
        model.addAttribute("provide_user",provide_user);
        model.addAttribute("rental_user",rental_user);

        return "OneRoom/information";
    }

    // 계약 취소
    @GetMapping("/stopContract")
    public String stoptContract(@RequestParam("oneroom_id") Long oneroom_id, @RequestParam("login_id") Long login_id, Model model){

        // 선택된 원룸
        OneRoom oneRoom = oneRoomService.findOneRoomByID(oneroom_id);
        // 선택된 원룸에 대한 계약
        Contract contract = oneRoom.getContract();

        // 현재 로그인 유저가 원룸 임대인 또는 임차인인 경우만 취소할 수 있음.
        if(login_id == oneRoom.getProvideUser().getId() || login_id == oneRoom.getRentalUser().getId())
        {
            /** rentalUser 외래키 제거 */
            // rental 유저 정보를 외래키로 저장
            oneRoom.setRentalUser(null);
            contract.setRentalUser(null);

            /** Room 계약 상태 변경 */
            oneRoom.setRoomContract(0L);

            /** DB에 OneRoom, Contract 업데이트 */
            oneRoomService.createOneRoom(oneRoom);
            contractService.createContract(contract);

            User provide_user = oneRoom.getProvideUser();
            User rental_user = oneRoom.getRentalUser();

            model.addAttribute("oneRoom",oneRoom);
            model.addAttribute("provide_user",provide_user);
            model.addAttribute("rental_user",rental_user);
            model.addAttribute("rental_user2",rental_user);
            return "OneRoom/information";
        }
        // 권한이 없는 경우
        else{
            return "Error";
        }

    }


    // 계약 완료(종료)
    @GetMapping("/endContract")
    public String endContract(@RequestParam("oneroom_id") Long oneroom_id, @RequestParam("login_id") Long login_id, Model model){

        // 선택된 원룸
        OneRoom oneRoom = oneRoomService.findOneRoomByID(oneroom_id);
        // 선택된 원룸에 대한 계약
        Contract contract = oneRoom.getContract();

        //로그인한 사용자의 ID가 임대인의 ID와 동일한 경우 + 원룸의 계약 진행 상태가 1L 일때
        if(login_id == oneRoom.getProvideUser().getId())
        {
            if(oneRoom.getRoomContract() == 1L) {
                /** Room 계약 상태 변경 */
                oneRoom.setRoomContract(2L);

                /** DB에 OneRoom, Contract 업데이트 */
                oneRoomService.createOneRoom(oneRoom);
                contractService.createContract(contract);
            }

            User provide_user = oneRoom.getProvideUser();
            User rental_user = oneRoom.getRentalUser();

            model.addAttribute("oneRoom", oneRoom);
            model.addAttribute("provide_user", provide_user);
            model.addAttribute("rental_user", rental_user);

            return "OneRoom/information";
        }

        else{
            return "Error";
        }

    }





}
