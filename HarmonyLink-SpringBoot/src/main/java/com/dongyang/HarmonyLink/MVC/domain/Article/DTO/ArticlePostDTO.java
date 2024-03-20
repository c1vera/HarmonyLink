package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import lombok.*;

/**
 * 용도 :
 * 프론트엔드에서 게시글 작성 시 전달되는 article, track에 대한 모든 정보를 가져오는 DTO
 * */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class ArticlePostDTO {

    /* 게시글 정보 부분 */
    private Long post_key;
    private String title;
    private String type; // 어떤 MBTI 게시판의 글인지 확인
    private int view; // 조회수 기능
    private String content;
    private int thumbsUp;

    private String trackName;
    private String artistName; 
    private String imgUri;


    /* user의 정보 부분 */
    private Long userKey; // 사용자의 DB Key 저장하여, 1:M 관계 수용(?)
    private String spotifyKey; // spotify에서 저장하는 키값을 기준으로 노래를 저장하기
    private String nickname; // 사용자 이름

    /* 추후 빌더로만 사용하기 */
    public static ArticlePostDTO toDTO(ArticleEntity articleEntity) {
        return new ArticlePostDTO(
                articleEntity.getPost_key(),
                articleEntity.getTitle(),
                articleEntity.getType(),
                articleEntity.getView(),
                articleEntity.getContent(),
                articleEntity.getThumbsUp(),
                null,
                null,
                null,
                articleEntity.getUser().getUserKey(),
                articleEntity.getTrack().getSpotifyKey(),
                articleEntity.getUser().getNickname()
        );
    }

    public ArticlePostDTO setTrackInfo(TrackDTO trackDTO) {
        this.trackName = trackDTO.getTrackName();
        this.artistName = trackDTO.getTrackArtist();
        this.imgUri = trackDTO.getTrackImgUri();

        return this;
    }

}