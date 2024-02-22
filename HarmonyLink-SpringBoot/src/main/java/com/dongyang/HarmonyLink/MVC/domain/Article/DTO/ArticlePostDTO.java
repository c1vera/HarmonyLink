package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 용도 :
 * 일반 사용자를 위한
 * 게시글의 '등록', '수정' 등 의 게시글 상호작용에 사용.
 * */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticlePostDTO {
    private Long post_key;
    private String title;
    private String type; // 어떤 MBTI 게시판의 글인지 확인
    private int view; // 조회수 기능
    private String content;
    private String music_key; // api에서 어떻게 받아오느냐에 따라 자료형 변경 예정.
    private int thumbsUp;

    private Long userKey; // 사용자의 DB Key 저장하여, 1:M 관계 수용(?)

    public static ArticlePostDTO toDTO(ArticleEntity entity) {
        return new ArticlePostDTO(
                entity.getPost_key(),
                entity.getTitle(),
                entity.getType(),
                entity.getView(),
                entity.getContent(),
                entity.getMusic_key(),
                entity.getThumbsUp(),
                entity.getUser().getUserKey()
        );
    }

}