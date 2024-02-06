package com.dongyang.HarmonyLink.Manager;

import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static com.dongyang.HarmonyLink.Manager.SessionConst.COOKIE_NAME; // 쿠키 이름

/** 사용자의 로그인, 로그아웃 등에 사용되는 session을 관리하기 위한 코드 집합입니다.
 * 싱글 톤을 위해 Configuration 사용. */
@Configuration
public class SessionManager {

//    /** 세션 값을 저장해 두는 Map
//     * 본 Map에 UUID - id로 저장된 값과 쿠키에서 올라온 id - UUID 쿠키내역을 비교하여 두 id 같은지 비교. */
//    Map<String, String> sessionStore = new HashMap<>();
    // HttpSession 활용하므로, 위 Map은 사용 X

    public static final String cookieName = COOKIE_NAME;

    /** 쿠키와 세션 생성 */
    public Cookie sessionLogin(HttpSession session, UserDTO user) {

        String uuid = UUID.randomUUID().toString();

        // session에 uuid - 사용자id attribute 만들어두기.
        session.setAttribute(uuid, user.getId());

        // 사용자가 쿠키로 저장할 UUID 쿠키 만들어 return
        Cookie cookie = new Cookie(COOKIE_NAME, uuid);
        cookie.setPath("/");

        return cookie;
    }

    /** 세션 제거 */
    public void sessionLogout(HttpSession session) {
        if(session != null) session.invalidate();
    }


    public boolean sessionCheck(HttpSession session, String sessionName) {
        if(!session.isNew()) {
            String sessionUser = (String)session.getAttribute(sessionName);

            if(sessionUser != null) {
                return true;
            }
        }

        return false;
    }
}
