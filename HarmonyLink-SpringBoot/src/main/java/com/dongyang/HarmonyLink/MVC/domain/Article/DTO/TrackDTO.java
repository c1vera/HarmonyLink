package com.dongyang.HarmonyLink.MVC.domain.Article.DTO;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class TrackDTO {
    private String trackName;
    private String trackArtist;
    private String trackImgURL;

    // Article Foreign Key, Many To One
    private String post_key;
}
