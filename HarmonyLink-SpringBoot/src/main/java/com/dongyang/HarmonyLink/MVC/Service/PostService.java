package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.PostRepository;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.Manager.SessionManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository, SessionManager sessionManager) {
        this.postRepository = postRepository;
    }

    public ArticlePostDTO getArticle(Long postKey) {
        ArticleEntity entity = postRepository.findById(postKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않음"));


        return ArticlePostDTO.toDTO(entity);
    }

    /** 모든 게시글 반환(필터링 X) */
    public List<ArticlePostDTO> getAllArticle() {
        return postRepository.findAll().stream()
                .map(i -> ArticlePostDTO.toDTO(i)).toList();
    }

    /** 게시글 작성 */
    @Transactional
    public void postArticle(UserDTO user, ArticlePostDTO dto) {
        UserEntity writeUser = UserEntity.toEntity(user);

        postRepository.save(ArticleEntity.toEntity(dto, writeUser));
    }



}
