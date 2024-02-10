package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.UserRepository;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.LoginDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.Manager.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 실패. 동일한 아이디가 없음."));

        return UserDTO.toDTO(user);
    }

    /** 로그인 권한 확인 위한 session 생성 작업
     * 참고 : 권한 확인 방법 == session attribute 값의 유무 확인 */
    public Cookie sessionLogin(HttpSession session, UserDTO user) {
        // if(!session.isNew()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그인한 회원입니다.");
        // 위 에러처리는 로그아웃 기능 구현 완료 후 수행.

        // 'session_id' - UUID 쌍의 쿠키를 만들고, session에는 UUID - UserDTO와 같은 키값쌍을 저장해둠
        return sessionManager.sessionLogin(session, user);
    }

    @Transactional
    public UserDTO getAuthUser(HttpServletRequest request) {
        UserDTO userInfo = sessionManager.getAuthUser(request)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자 인증 실패"));

        return userInfo;
    }

    public void sessionLogout(HttpSession session) {
        sessionManager.sessionLogout(session);
    }

}
