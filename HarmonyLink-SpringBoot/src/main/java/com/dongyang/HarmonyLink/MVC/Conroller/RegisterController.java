package com.dongyang.HarmonyLink.MVC.Conroller;

import com.dongyang.HarmonyLink.MVC.Service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class RegisterController {
    private RegisterService registerService;


    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /* =====================
    * 사용 용도 : view 구현 안된 상태에서 test 용 view를 사용하기 위한 용도.
    *======================*/


    /* 회원가입 사이트 이동 관련 */
    /** register View Templates 연결하기 */
    @GetMapping("/page/register")
    public String pageRegister() {

        return "userRegister";
    }


    /* 회원가입 기능 관련 */
    @GetMapping("/user/getRegiInfo")
    public void getUserInfo() {

    }


//    @PostMapping("/user/requestRegister")
//    public void register(UserDTO dto) {
//        log.info(dto.toString());
//
//        // 서비스 호출
//        UserDTO resultDTO = registerService.register(dto);
//
//        // return resultDTO != null ? "회원가입 결과 사이트" : "redirect:/page/register"; // 회원 가입 실패에 의한 리다이렉트 또는,
//    }

}
