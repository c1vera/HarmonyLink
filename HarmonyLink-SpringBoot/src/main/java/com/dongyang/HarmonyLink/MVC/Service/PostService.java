package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.PostRepository;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.Manager.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    private PostRepository postRepository;
    private SessionManager sessionManager;

    public PostService(PostRepository postRepository, SessionManager sessionManager) {
        this.postRepository = postRepository;
        this.sessionManager = sessionManager;
    }


    @Transactional
    public void postArticle(UserDTO user, ArticlePostDTO dto) {
        UserEntity writeUser = UserEntity.toEntity(user);

        postRepository.save(ArticleEntity.toEntity(dto, writeUser));
    }
}
