package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.LoginService;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.Service.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1") // controller 또한 매핑 가능. 이를 통해 컨트롤러 별 버전 유지도 가능.
@Slf4j
public class RegisterApiController {
    private RegisterService registerService;

    private LoginService loginService;

    public RegisterApiController(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
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

        UserDTO dto = registerService.getInfo(id);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 사용대상 : User
     * 용도 : 사이트에서 회원가입을 요청.
     * 수행 내역 : 회원 가입(POST)
     * */
    @PostMapping("/user/Register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO dto) {
        log.info(dto.toString());

        // 서비스 호출
        UserDTO resultDTO = registerService.register(dto);

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }


    /**
     * 사용대상 : User
     * 용도 : 사이트에서 회원정보 수정
     * 수행 내역 : 회원 정보 수정(PATCH)
     * */

    @PatchMapping("/user/Register")
    public ResponseEntity<UserDTO> patchUser(HttpServletRequest request, @RequestBody UserDTO dto) {
        Optional<UserDTO> user = loginService.getAuthUser(request);

        // 현재 권한을 지니고 작업을 수행하는 사용자가 작업대상을 작성했던 인원이 맞는지 확인하는 함수가 필요하다.



        UserDTO resultDTO = registerService.patchUser(dto);

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }


    /**
     * 사용대상 : User
     * 용도 : 사이트에서 회원 탈퇴
     * 수행 내역 : 회원 정보 삭제(DELETE)
     * */
    @DeleteMapping("/user/Register")
    public void deleteUser(@RequestBody UserDTO dto) {
        registerService.deleteUser(dto);
    }

}
