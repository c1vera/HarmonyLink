package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

/**
 * 용도 : 게시글의 '등록', '수정' 등에 사용.
 * */
public class ArticlePostDTO {
    private Long post_key;
    private String title;
    private String music_key; // api에서 어떻게 받아오느냐에 따라 자료형 변경 예정.
    private int thumbsUp;
}