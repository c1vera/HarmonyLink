package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.LoginService;
import com.dongyang.HarmonyLink.MVC.domain.DTO.LoginDTO;
import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/api/v1")
@RestController
@Slf4j
public class LoginApiController {

    /*
    * LoginApi는 Controller,Service, Repository 외에 아래와 같은 추가적인 요소를 사용합니다.
    * Manager.ExeptionManager : 각 오류 객체 만드는데 사용
    *
    * */

    private LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/user/requestLogin")
    public ResponseEntity<Void> Login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        log.info(loginDTO.toString());

        UserDTO resultDTO = loginService.tryLogin(loginDTO);

        /* 로그인 대상이 알맞지 않은 경우. */
        if(resultDTO == null) {
            log.info("로그인 실패. 맞는 로그인 형식 아님");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 로그인에 성공한 경우, 해당 사용자의 세션을 생성
        Cookie cookie = loginService.sessionLogin(resultDTO, request);
        log.info("로그인 성공");

        // session 인증 위해 제작한 UUID key를 내려보내야 함.
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, String.valueOf(cookie)).build();
    }


}
