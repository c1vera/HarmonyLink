package com.dongyang.HarmonyLink.MVC.domain.Article.Entity;

import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class TrackEntity {
    @Id
    private String spotifyKey;

    @Column
    private String name;

    @Column
    private String artist;

    @Column
    private String imgURI;


    public static TrackEntity toEntity(TrackDTO track) {
        return new TrackEntity(
                track.getSpotifyKey(),
                track.getTrackName(),
                track.getTrackArtist(),
                track.getTrackImgUri()
        );
    }
}
