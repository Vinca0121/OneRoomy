package com.example.oneroomy.Controller;


import com.example.oneroomy.DTO.UserDTO;
import com.example.oneroomy.Domain.User;
import com.example.oneroomy.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class UserController {

    // autowired 필요 X Configuration사용
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** 기본적으로 로그인 화면으로 이동 */
    @GetMapping("/")
    public String showMainPage(){
        // 뭔가 웹 페이지에 저장된 값을 통해서, 로그인이 되어있는 경우 바로 홈화면으로 전환되도록 하는 것이 필요할듯.
        return "login";
    }

    /** 로그인화면에서 회원 가입을 누를 시, 회원 가입 화면으로 이동 */
    @GetMapping("/signup")
    public String showSignupPage(){
        return "signup";
    }

    /** 회원가입 시 발생하는 action
     * 이후 로그인 페이지로 아동*/
    @PostMapping("/signup")
    public String registerUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .phonenumber(userDTO.getPhonenumber())
                .password(userDTO.getPassword())
                .location(userDTO.getLocation())
                .university(userDTO.getUniversity())
                .build();
        userService.createUser(user);
        return "login";
    }
    /** 로그인 후 메인 페이지로 이동 */
    @PostMapping("/signin")
    public String tologin(@RequestBody UserDTO userDTO) {
        User user = userService.getByCredentials(
                userDTO.getPhonenumber(),
                userDTO.getPassword()
        );
        /**반환 된 유저가 객체가 있으면 홈페이지로*/
        if(user != null)
        {
            return "home";
        }
        else{
            return "signin";
        }
    }

}
