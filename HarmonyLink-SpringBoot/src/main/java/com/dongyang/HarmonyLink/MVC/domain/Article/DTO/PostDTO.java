package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class PostDTO {
    private Long post_key;
    private String title;
    private String type; // 어떤 MBTI 게시판의 글인지 확인
    private int view; // 조회수 기능
    private String content;
    private int thumbsUp;


    /* user의 정보 부분 */
    private Long userKey; // 사용자의 DB Key 저장하여, 1:M 관계 수용(?)
    private String spotifyKey; // spotify에서 저장하는 키값을 기준으로 노래를 저장하기
    private String nickname; // 사용자 이름
}
