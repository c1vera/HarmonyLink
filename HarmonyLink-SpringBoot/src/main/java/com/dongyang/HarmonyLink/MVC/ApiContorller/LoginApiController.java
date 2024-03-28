package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.LoginService;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.LoginDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;


@RequestMapping("/api/v1")
@RestController
@Slf4j
public class LoginApiController {

    /*
    * LoginApi는 Controller,Service, Repository 외에 아래와 같은 추가적인 요소를 사용합니다.
    * Manager.ExeptionManager : 각 오류 객체 만드는데 사용 => 아직까지 사용 X
    *
    * */

    private LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
    }


    /** 사용자 로그인 */
    @PostMapping("/user/requestLogin")
    public ResponseEntity<UserDTO> Login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        log.info(loginDTO.toString());

        UserDTO resultDTO = loginService.tryLogin(loginDTO);

        // HttpSession은 Controller 단에서 작성하기.
        HttpSession session = request.getSession();

        // 로그인에 성공한 경우, 해당 사용자의 세션을 생성
        Cookie cookie = loginService.sessionLogin(session, resultDTO);
        log.info("로그인 성공" + cookie.toString());

        // session 인증 위해 제작한 UUID key를 내려보내야 함.
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.getName() + "=" + cookie.getValue() + "; Path=" + cookie.getPath() + "; HttpOnly")
                .body(resultDTO); // 200 성공 코드 및 쿠키와 사용자 정보 반환
    }

    /** 사용자 로그아웃 */
    @PostMapping("/user/requestLogout")
    public ResponseEntity<Void> Logout(HttpServletRequest request) {
        loginService.getAuthUser(request); // 로그인한 사용자인지 확인

        loginService.sessionLogout(request.getSession()); // sessionAttribute 제거

        Cookie cookie = new Cookie("session_id", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        
        // 이름 동일한 비어있는 쿠키 내려보내 쿠키 삭제시키기
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.getName() + "=" + cookie.getValue() + "; Path=" + cookie.getPath() + "; HttpOnly")
                .build();
    }


    public void SessionController(LoginService loginService) {
        this.loginService = loginService;
    }
    @GetMapping("/session/validate")
    public ResponseEntity<?> validateSession(HttpServletRequest request) {
        try {
            Optional<UserDTO> user = loginService.getAuthUser(request);
            if (!user.isPresent()) {
                // 사용자 정보가 없는 경우, 즉 세션이 유효하지 않은 경우
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션 유효하지 않음");
            }
            return ResponseEntity.ok().body(user.get());
        } catch (ResponseStatusException e) {
            // 세션 만료 또는 유효하지 않은 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 유효하지 않음");
        }
    }
    


//    /** userId는 주어진 사용자 정보 json에서 추출해서 사용*/
//    @PostMapping("/user/checkLogin") // 로그인 인증만을 확인하기 위한 용도의 임시 URL. 실제에서는 사용되지 않음.
//    public ResponseEntity<Void> checkLogin(HttpServletRequest request) {
//        // 변수로 이름 조회해야 하므로, cookieValue 대신 request에서 직접 Cookie 찾기
//
//        HttpSession session = request.getSession();
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("session_id".equals(cookie.getName())) {
//                    String cookieValue = cookie.getValue();
//                    // 쿠키 값으로 로그인 세션 확인
//                    return loginService.getAuthUser(session, cookieValue).orElse(null) != null ?
//                            ResponseEntity.status(HttpStatus.OK).build() :
//                            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//                }
//            }
//        }
//
//        // 쿠키를 불러오지 못한 경우 다른 에러 코드 반환
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }


}
