package com.example.oneroomy.Controller;


import com.example.oneroomy.DTO.UserDTO;
import com.example.oneroomy.Domain.OneRoom;
import com.example.oneroomy.Domain.Statistic;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Service.OneRoomService;
import com.example.oneroomy.Service.StatisticService;
import com.example.oneroomy.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class UserController {

    // autowired 필요 X Configuration사용
    private UserService userService;
    private OneRoomService oneRoomService;
    private StatisticService statisticService;

    public UserController(UserService userService, OneRoomService oneRoomService, StatisticService statisticService) {
        this.userService = userService;
        this.oneRoomService = oneRoomService;
        this.statisticService = statisticService;
    }

    /** 기본적으로 로그인 화면으로 이동 */
    @GetMapping("/")
    public String showMainPage(){

        // 뭔가 웹 페이지에 저장된 값을 통해서, 로그인이 되어있는 경우 바로 홈화면으로 전환되도록 하는 것이 필요할듯.
        return "login";
    }

    /** 회원정보 수정 취소 시 , 홈화면으로 이동 */
    @GetMapping("/home")
    public String showHome(@RequestParam Long id, Model model){
        // 뭔가 웹 페이지에 저장된 값을 통해서, 로그인이 되어있는 경우 바로 홈화면으로 전환되도록 하는 것이 필요할듯.
        User user = userService.getOneUser(id);
        model.addAttribute("user",user);

        /** 2. 원룸전체찾기 */
        List<OneRoom> roomList = oneRoomService.findAllOneRooms();
        model.addAttribute("roomList",roomList);

        /** 3. 맨마지막 Statistic 찾아서 반환 */
        Statistic statistic = statisticService.findFinalStatistic();
        model.addAttribute("statistic",statistic);

        return "home";
    }



    /** 회원가입 시 발생하는 action
     * 이후 로그인 페이지로 아동*/
    @PostMapping("/signup")
    public String registerUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .phonenumber(userDTO.getPhonenumber())
                .password(userDTO.getPassword())
                .locations(userDTO.getLocations())
                .university(userDTO.getUniversity())
                .build();
        userService.createUser(user);
        return "login";
    }
    /** 로그인 후 메인 페이지로 이동 */
    @PostMapping("/signin")
    public String tologin(UserDTO userDTO, Model model) {
        User user = userService.getByCredentials(
                userDTO.getPhonenumber(),
                userDTO.getPassword()
        );
        /**반환 된 유저가 객체가 있으면 홈페이지로*/
        /** 반환 된 유저의 id를 크롬 세션데이터에 저장하도록 값을 넘김 */
        model.addAttribute("user",user);

        /** 2. 원룸전체찾기 */
        List<OneRoom> roomList = oneRoomService.findAllOneRooms();
        model.addAttribute("roomList",roomList);

        /** 3. 맨마지막 Statistic 찾아서 반환 */
        Statistic statistic = statisticService.findFinalStatistic();
        if(statistic == null)
        {
            // 없는 경우 평균을 내서 기본 데이터를 새로 만들고 다시 가져옴.
            statisticService.updateStatistic();
            statistic = statisticService.findFinalStatistic();
        }
        model.addAttribute("statistic",statistic);

        if(user != null)
        {
            return "home";
        }
        else{
            return "redirect:/";
        }
    }

    /** 회원정보수정화면으로 이동 */
    @GetMapping("/editinfo")
    public String showEditInfo(@RequestParam Long id, Model model){
        User user = userService.getOneUser(id);
        model.addAttribute("user",user);
        return "editinfo";
    }

    /** 회원정보수정 요청 */
    @PostMapping("/editAccount")
    public String EditAccount(UserDTO userDTO, Model model){
        // 폰번호와 비밀번호로 기존 유저 ID를 찾아서 해당 아이디로, user를 빌더함.
        User exist_user = userService.getByCredentials(userDTO.getPhonenumber(), userDTO.getPassword());

        User user = User.builder()
                .id(exist_user.getId())
                .username(userDTO.getUsername())
                .phonenumber(userDTO.getPhonenumber())
                .password(userDTO.getPassword())
                .locations(userDTO.getLocations())
                .university(userDTO.getUniversity())
                .build();

        // create같지만 id가 같으므로 중복되지않고, 덮어씌워짐
        userService.createUser(user);
        model.addAttribute("user",user);

        /** 2. 원룸전체찾기 */
        List<OneRoom> roomList = oneRoomService.findAllOneRooms();
        model.addAttribute("roomList",roomList);

        /** 3. 맨마지막 Statistic 찾아서 반환 */
        Statistic statistic = statisticService.findFinalStatistic();
        model.addAttribute("statistic",statistic);

        return "home";
    }


    /** 회원 탈퇴 요청 */
    @GetMapping("/deleteAccount")
    public String deleteAccount(@RequestParam Long id, String description){
        userService.deleteUser(id);
        return "redirect:/";
    }

    /** 로그아웃 */
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }




}
