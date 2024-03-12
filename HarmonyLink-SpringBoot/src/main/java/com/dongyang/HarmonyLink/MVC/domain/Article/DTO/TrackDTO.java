package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class TrackDTO {
    private String trackName;
    private String trackArtist;
    private String trackImgUri;

    // Article Foreign Key, Many To One
    private Long spotifyKey;

    public static TrackDTO toDTO(TrackEntity trackEntity) {
        return new TrackDTO(
                trackEntity.getName(),
                trackEntity.getArtist(),
                trackEntity.getImgURI(),
                trackEntity.getSpotifyKey()
        );
    }
}
