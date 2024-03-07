package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.LoginService;
import com.dongyang.HarmonyLink.MVC.Service.PostService;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.Manager.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class PostApiController {
    // 에러 발생은 컨트롤러가 아닌, 서비스 계층에 수행하기.

    private PostService postService;
    private LoginService loginService;

    public PostApiController(PostService postService, LoginService loginService) {
        this.postService = postService;
        this.loginService = loginService;
    }


    // 포스트의 확인은 로그인한 사용자만 가능한가?
    /** 클릭한 게시글 아이디 기준의 게시글 확인 */
    @GetMapping("/user/post/{postKey}")
    public ResponseEntity<ArticlePostDTO> getArticle(@PathVariable("postKey") Long postKey) {
        ArticlePostDTO article = postService.getArticle(postKey);

        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    /** 필터된 게시글 리스트 제공용. 요청 시, get parameter로 page, size를 작성하여 페이징 기능 구현 가능합니다. */
    @GetMapping("/user/postListFiltered/{mbtiParam}")
    public ResponseEntity<Page<ArticlePostDTO>> getFilteredList(@PathVariable("mbtiParam") String mbti,
                                                                @PageableDefault(size = 1, sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable) {
        // pageableDefault는 나중에 변경하기.

        Page<ArticlePostDTO> list = postService.getFilteredList(mbti, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    /*
    * @GetMapping("/user/postListFiltered/{mbtiParam}")
    public ResponseEntity<List<ArticlePostDTO>> getFilteredList(@PathVariable("mbtiParam") String mbti) {
        List<ArticlePostDTO> list = postService.getFilteredList(mbti);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }*/


    /** 모든 게시글 제공용. 필터링 기능이 고려되지 않음. - 사용 안함. */
    @GetMapping("/user/postList")
    public ResponseEntity<List<ArticlePostDTO>> getList() {
        List<ArticlePostDTO> list = postService.getAllArticle();

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }



    /** 게시글 작성하기 */
    @PostMapping("/user/requestPost")
    public ResponseEntity<ArticlePostDTO> postArticle(HttpServletRequest request,
                                            @RequestBody ArticlePostDTO dto) {
        UserDTO user = loginService.getAuthUser(request);
        // ResponseStatusException은, 예외와 동시에 Http 상태 코드를 발생 가능. 사용하기 유용할 듯??

        ArticlePostDTO resultDTO = postService.postArticle(user, dto);

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }

    /** 게시글 수정하기 */
    @PatchMapping("/user/article")
    public ResponseEntity<ArticlePostDTO> patchArticle(HttpServletRequest request,
                                                       @RequestBody ArticlePostDTO dto) {

        UserDTO user = loginService.getAuthUser(request);

        ArticlePostDTO resultDTO = postService.patchArticle(user, dto);

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }

    /** 사용자가 삭제하는 경우 */
    //@DeleteMapping("/user/article")


    /** 관리자가 삭제하는 경우 */
    //@DeleteMapping("/root/article")


}
