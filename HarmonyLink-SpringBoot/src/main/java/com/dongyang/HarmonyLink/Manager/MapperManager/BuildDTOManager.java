package com.dongyang.HarmonyLink.Manager.MapperManager;

import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.PostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;

/** 여러 dto의 빌딩을 담당하는 매니저. */
public class BuildDTOManager {

    /** ArticlePostDTO -> PostDTO*/
    public static PostDTO buildPostDTO(ArticlePostDTO dto) {
        return PostDTO.builder()
                .post_key(dto.getPost_key())
                .title(dto.getTitle())
                .type(dto.getType())
                .view(dto.getView())
                .content(dto.getContent())
                .thumbsUp(dto.getThumbsUp())
                .userKey(dto.getUserKey())
                .spotifyKey(dto.getSpotifyKey())
                .nickname(dto.getNickname())
                .build();
    }

    /** ArticlePostDTO -> TrackDTO*/
    public static TrackDTO buildTrackDTO(ArticlePostDTO dto) {
        return TrackDTO.builder()
                .spotifyKey(dto.getSpotifyKey())
                .trackName(dto.getTrackName())
                .trackArtist(dto.getArtistName())
                .trackImgUri(dto.getImgUri())
                .build();
    }
}
