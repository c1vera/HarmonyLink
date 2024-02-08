package com.dongyang.HarmonyLink.Manager;

import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

/** 사용자의 로그인, 로그아웃 등에 사용되는 session을 관리하기 위한 코드 집합입니다.
 * 싱글 톤을 위해 Configuration 사용. */
@Configuration
public class SessionManager {

    /** 쿠키와 세션 생성 */
    public Cookie sessionLogin(HttpSession session, UserDTO user) {

        String uuid = UUID.randomUUID().toString();

        // session에 uuid - 사용자 instance attribute 만들어두기.
        session.setAttribute(uuid, user); // 앞으로, @SessionAttribute(COOKIE_NAME) class에 @ModelAttribute(COOKIE_NAME)

        // 사용자가 쿠키로 저장할 UUID 쿠키 만들어 return
        Cookie cookie = new Cookie("session_id", uuid); // 쿠키 이름 자체는 딱히 상관없다..한다.
        cookie.setPath("/");

        return cookie;
    }


    /** 세션 제거 */
    public void sessionLogout(HttpSession session) {
        if(session != null) session.invalidate();
    }



    /**
     * 현재 사용자가 로그인 중인 사용자인지 확인 + 세션에 저장된 사용자 DTO 데이터 불러오기
     * 모든 로그인 기능에 필수적으로 사용할 대상. 사용 시 orElse(null) 사용할 것.
     * */
    public Optional<UserDTO> getAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String cookieValue = getCookieValueForSession(session, request.getCookies());

        if(cookieValue.equals("")) return null;

        return sessionCheck(session, cookieValue);

    }

    /** session_id 쿠키 찾아서 uuid value값 찾기. */
    public String getCookieValueForSession(HttpSession session, Cookie[] cookies) {
        // Cookie[] cookies = request.getCookies();
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                    // 쿠키 값으로 로그인 세션 확인
                }
            }
        }

        return cookieValue;
    }

    /** 찾은 session_id UUID로 세션에 저장된 UserDTO 값 가져오기 */
    public Optional<UserDTO> sessionCheck(HttpSession session, String sessionName) {
        UserDTO sessionUser = null; // 기본 null 설정
        
        if (!session.isNew()) { // 기존에 이미 세션이 존재했던 경우(이미 로그인을 통해 세션 생성된 경우)
            sessionUser = (UserDTO) session.getAttribute(sessionName); // 세션에 저장된 사용자 DTO 데이터 가져옴
        }

        return Optional.ofNullable(sessionUser); // null 또는 사용자 DTO를 반환
    }
}
