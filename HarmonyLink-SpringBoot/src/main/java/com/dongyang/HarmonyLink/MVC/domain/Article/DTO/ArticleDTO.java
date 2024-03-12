package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 용도 :
 * Article과 Track의 값을 동시에 받아오기 위한 상위 DTO
 * */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleDTO {
    private ArticlePostDTO postDTO;
    private TrackDTO trackDTO;


    /*
    private Long post_key;
    private String title;
    private String type; // 어떤 MBTI 게시판의 글인지 확인
    private int view; // 조회수 기능
    private String content;
    private String music_key; // api에서 어떻게 받아오느냐에 따라 자료형 변경 예정.
    private int thumbsUp;

    private LocalDateTime deletedDate;
    private String whyDeleted;

    private Long userKey;
    /* Foreign Key */
}
