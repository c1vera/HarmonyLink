package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.LoginService;
import com.dongyang.HarmonyLink.MVC.domain.DTO.LoginDTO;
import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import com.dongyang.HarmonyLink.Manager.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dongyang.HarmonyLink.Manager.SessionConst.COOKIE_NAME;

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
    public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        log.info(loginDTO.toString());

        UserDTO resultDTO = loginService.tryLogin(loginDTO);

        /* 로그인 대상이 알맞지 않은 경우. */
        if(resultDTO == null) {
            log.info("로그인 실패. 맞는 로그인 형식 아님");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 에러 코드
        }

        // 로그인에 성공한 경우, 해당 사용자의 세션을 생성
        Cookie cookie = loginService.sessionLogin(resultDTO, request);
        log.info("로그인 성공" + String.valueOf(cookie));

        // session 인증 위해 제작한 UUID key를 내려보내야 함.
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, String.valueOf(cookie))
                .body(resultDTO); // 200 성공 코드 및 쿠키와 사용자 정보 반환
    }


    @PostMapping("/user/checkLogin") // 로그인 인증만을 확인하기 위한 용도의 임시 URL. 실제에서는 사용되지 않음.
    public ResponseEntity<Void> checkLogin(HttpServletRequest request) {
        // 변수로 이름 조회해야 하므로, cookieValue 대신 request에서 직접 Cookie 찾기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    // 쿠키 값으로 로그인 세션 확인
                    return !loginService.sessionCheck(cookieValue).equals("None") ?
                            ResponseEntity.status(HttpStatus.OK).build() :
                            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }
        
        // 쿠키를 불러오지 못한 경우 다른 에러 코드 반환
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }


}
