package com.dongyang.HarmonyLink.MVC.domain.Article.Entity;

import jakarta.persistence.ManyToOne;

public class TrackEntity {

    private String trackName;
    private String trackArtist;
    private String trackImgURI;


    private ArticleEntity post_key;
}
