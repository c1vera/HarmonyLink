package com.dongyang.HarmonyLink.Manager.MapperManager;

import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.PostDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildEntityManager {

    public static ArticleEntity buildArticleEntity(UserEntity userEntity, PostDTO postDTO, TrackEntity trackEntity) {


        return ArticleEntity.builder()
                .title(postDTO.getTitle())
                .type(postDTO.getType())
                .view(postDTO.getView())
                .content(postDTO.getContent())
                .thumbsUp(postDTO.getThumbsUp())
                .user(userEntity)
                .track(trackEntity)
                .build();
    }

    public static TrackEntity buildTrackEntity(TrackDTO trackDTO) {
        log.info(trackDTO.toString());
        return TrackEntity.builder()
                .name(trackDTO.getTrackName())
                .artist(trackDTO.getTrackArtist())
                .imgURI(trackDTO.getTrackImgUri())
                .spotifyKey(trackDTO.getSpotifyKey())
                .build();
    }
}
