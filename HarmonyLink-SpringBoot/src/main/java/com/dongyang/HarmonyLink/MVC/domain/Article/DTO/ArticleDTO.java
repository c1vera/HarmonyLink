package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleDTO {
    private Long post_key;
    private String title;
    private String music_key; // api에서 어떻게 받아오느냐에 따라 자료형 변경 예정.
    private int thumbsUp;

    private boolean isDeleted;
    private LocalDateTime deletedDate;
    private String whyDeleted;


    /* Foreign Key */
    private Long userKey;
    private String id;
}
