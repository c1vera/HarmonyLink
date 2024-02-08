package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.PostService;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.Manager.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class PostApiController {

    private PostService postService;
    private SessionManager sessionManager;

    public PostApiController(PostService postService, SessionManager sessionManager) {
        this.postService = postService;
        this.sessionManager = sessionManager;
    }

    /** 클릭한 게시글 아이디 기준의 게시글 확인 */
    @GetMapping("/user/article/{articleId}")

    /** 게시글 작성하기 */
    @PostMapping("/user/article")
    public ResponseEntity<Void> postArticle(HttpServletRequest request,
                                            @RequestBody ArticlePostDTO dto) {
        UserDTO user = sessionManager.getAuthUser(request)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자 인증 실패"));
        // ResponseStatusException은, 예외와 동시에 Http 상태 코드를 발생 가능. 사용하기 유용할 듯??

        postService.postArticle(user, dto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //@PatchMapping("/user/article")


    /** 사용자가 삭제하는 경우 */
    //@DeleteMapping("/user/article")


    /** 관리자가 삭제하는 경우 */
    //@DeleteMapping("/root/article")


}
