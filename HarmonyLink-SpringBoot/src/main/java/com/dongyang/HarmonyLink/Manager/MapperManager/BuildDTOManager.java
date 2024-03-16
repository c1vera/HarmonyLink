package com.dongyang.HarmonyLink.Manager.MapperManager;

import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.PostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;

/** 여러 dto의 빌딩을 담당하는 매니저. */
public class BuildDTOManager {
    /**
     *
     * Entity -> DTO 변환 용도의 function
     *
     * */

    public static TrackDTO toTrackDTO(TrackEntity trackEntity) {
        return TrackDTO.builder()
                .trackName(trackEntity.getName())
                .trackArtist(trackEntity.getArtist())
                .trackImgUri(trackEntity.getImgURI())
                .build();
    }


    /**
     *
     * DTO 간의 병합이나 분할을 다루는 function
     *
     * */
    /** ArticlePostDTO -> PostDTO : ArticlePostDTO에서의 분할 */
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

    /** ArticlePostDTO -> TrackDTO : ArticlePostDTO에서의 분할 */
    public static TrackDTO buildTrackDTO(ArticlePostDTO dto) {
        return TrackDTO.builder()
                .spotifyKey(dto.getSpotifyKey())
                .trackName(dto.getTrackName())
                .trackArtist(dto.getArtistName())
                .trackImgUri(dto.getImgUri())
                .build();
    }


    /** ArticlePostDTO + TrackDTO -> ArticlePostDTO : ArticlePostDTO로의 병합 */
    public static ArticlePostDTO buildArticlePostDTO(PostDTO postDTO, TrackDTO trackDTO) {
        return ArticlePostDTO.builder()
                .post_key(postDTO.getPost_key())
                .title(postDTO.getTitle())
                .type(postDTO.getType())
                .view(postDTO.getView())
                .content(postDTO.getContent())
                .thumbsUp(postDTO.getThumbsUp())
                .trackName(trackDTO.getTrackName())
                .artistName(trackDTO.getTrackArtist())
                .imgUri(trackDTO.getTrackArtist())
                .userKey(postDTO.getUserKey())
                .spotifyKey(trackDTO.getSpotifyKey())
                .nickname(postDTO.getNickname())
                .build();
    }


}
