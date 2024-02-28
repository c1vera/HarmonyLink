package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.PostRepository;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticleDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.Manager.SessionManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository, SessionManager sessionManager) {
        this.postRepository = postRepository;
    }

    /** 게시글 조회수 증가 및 해당 게시글 정보 가져오기 */
    public ArticlePostDTO getArticle(Long postKey) {

        ArticleEntity entity = postRepository.findById(postKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않음"));

        entity.increaseView(); // 조회수 증가
        postRepository.save(entity); // 조회수가 증가된 entity 저장

        return ArticlePostDTO.toDTO(entity);
    }

    /** 특정 필터링의 게시글 목록 반환 */
    public Page<ArticlePostDTO> getFilteredList(String mbti,
                                                @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC)Pageable pageable) {
        // pageableDefault는 나중에 변경하기.
        return postRepository.findByInputMbti(mbti, pageable)
                .map(i -> ArticlePostDTO.toDTO(i));
        // Page<>는 stream과 비슷하게 .map() 기능 제공.
    }

    /** '모든' 게시글 반환(필터링 X) */
    public List<ArticlePostDTO> getAllArticle() {
        return postRepository.findAll().stream()
                .map(i -> ArticlePostDTO.toDTO(i)).toList();
    }

    /** 게시글 작성 */
    @Transactional
    public ArticlePostDTO postArticle(UserDTO user, ArticlePostDTO dto) {
        UserEntity writeUser = UserEntity.toEntity(user);

        ArticleEntity result = postRepository.save(ArticleEntity.toEntity(dto, writeUser));

        return ArticlePostDTO.toDTO(result);
    }



}
