package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.Service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1") // controller 또한 매핑 가능. 이를 통해 컨트롤러 별 버전 유지도 가능.
@Slf4j
public class RegisterApiController {
    private RegisterService registerService;

    public RegisterApiController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * 사용대상 : User
     * 용도 : 사용자의 회원 정보를 제공.
     * 수행 내역 : 회원 정보 확인(GET)
     * */
    @GetMapping("/user/getUserInfo/{id}")
    public ResponseEntity<UserDTO> getInfo(@PathVariable String id) {
        // 중요! 사용자의 id또한 primary key 입니다.
        // db id는 사용자가 직접 알 수 없으므로, 일반 id를 사용하여 요청을 수행하도록 합니다.

        /* 로그인 구현은 어떻게 할 것인가? */

        UserDTO dto = registerService.getInfo(id);

        // == json으로 return?
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    /**
     * 사용대상 : User
     * 용도 : 사이트에서 회원가입을 요청.
     * 수행 내역 : 회원 가입(POST)
     * */
    @PostMapping("/user/requestRegister")
    public void register(@RequestBody UserDTO dto) {
        log.info(dto.toString());

        // 서비스 호출
        UserDTO resultDTO = registerService.register(dto);
    }


//    @PatchMapping("/user/requestInfoEdit")
//    public void edit(@RequestBody UserDTO dto) {
//
//        UserDTO resultDTO = registerService.patch(dto);
//    }


}
