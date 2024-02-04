package com.dongyang.HarmonyLink.Manager;

import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.JavaBean;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.dongyang.HarmonyLink.Manager.SessionConst.COOKIE_NAME; // 쿠키 이름

/** 사용자의 로그인, 로그아웃 등에 사용되는 session을 관리하기 위한 코드 집합입니다.
 * 싱글 톤을 위해 Configuration 사용. */
@Configuration
public class SessionManager {

    /** 세션 값을 저장해 두는 Map
     * 본 Map에 UUID - id로 저장된 값과 쿠키에서 올라온 id - UUID 쿠키내역을 비교하여 두 id 같은지 비교. */
    Map<String, String> sessionStore = new HashMap<>();

    public static final String cookieName = COOKIE_NAME;

    /** 쿠키와 세션 생성 */
    public Cookie sessionLogin(HttpServletRequest request, UserDTO user) {

        String uuid = UUID.randomUUID().toString();

        // 있으면 가져오고, 없으면 생성
        HttpSession session = request.getSession();

        // session에 uuid - 사용자id attribute 만들어두기.
        session.setAttribute(uuid, user.getId());
        sessionStore.put(uuid, user.getId());

        // 사용자가 쿠키로 저장할 UUID 쿠키 만들어 return

        return new Cookie(COOKIE_NAME, uuid);
    }

    /** 세션 제거 */
    public void sessionLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) session.invalidate();
    }


    public String sessionCheck(String sessionName) {
        return sessionStore.getOrDefault(sessionName, "None");
    }
}
