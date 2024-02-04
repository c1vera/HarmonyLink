package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.UserRepository;
import com.dongyang.HarmonyLink.MVC.domain.DTO.LoginDTO;
import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.Entity.UserEntity;
import com.dongyang.HarmonyLink.Manager.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    private UserRepository userRepository;

    // @Autowired
    private SessionManager sessionManager;

    public LoginService(UserRepository userRepository, SessionManager sessionManager) {
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    /** dto를 통해 id, pw만 전달받아 로그인 비교 수행 */
    /* 별도의 Entity를 사용하지 않음. */
    public UserDTO tryLogin(LoginDTO loginDTO) {

        UserEntity user = userRepository.tryLogin(loginDTO.getId(), loginDTO.getPw())
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패. 동일한 아이디가 없음."));

        return UserDTO.toDTO(user);
    }

    /** 로그인 권한 확인 위한 session 생성 작업
     * 참고 : 권한 확인 방법 == session attribute 값의 유무 확인 */
    public Cookie sessionLogin(UserDTO user, HttpServletRequest request) {

        // 상수UUID - UUID 쌍의 쿠키를 만들고, session에는 UUID - 사용자id와 같은 키값쌍을 저장해둠
        return sessionManager.sessionLogin(request, user);
    }

    public void sessionLogout(HttpServletRequest request) {
        sessionManager.sessionLogout(request);
    }

}
