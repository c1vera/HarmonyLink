package com.dongyang.HarmonyLink.MVC.ApiContorller.Spotify;

import com.dongyang.HarmonyLink.MVC.Service.Spotify.SpotifySearchService;
import com.dongyang.HarmonyLink.MVC.Service.Spotify.SpotifyTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SpotifyController {


    private SpotifyTokenService spotifyTokenService;

    private SpotifySearchService spotifySearchService;

    public SpotifyController(SpotifyTokenService spotifyTokenService, SpotifySearchService spotifySearchService) {
        this.spotifyTokenService = spotifyTokenService;
        this.spotifySearchService = spotifySearchService;
    }

    @GetMapping("/api/spotify/token")
    public String getSpotifyToken() {
        return spotifyTokenService.getAccessToken();
    }

    @GetMapping("/api/spotify/search") // ?type={type}&word={word} 과 같이 받아야함.
    public ResponseEntity<String> getSearchAlbum(@RequestParam String type, @RequestParam String word) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(spotifySearchService.typeSearch(spotifyTokenService.getAccessToken(), type, word));
    }

}
