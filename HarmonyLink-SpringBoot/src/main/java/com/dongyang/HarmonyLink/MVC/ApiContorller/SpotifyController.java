package com.dongyang.HarmonyLink.MVC.ApiContorller;

import com.dongyang.HarmonyLink.MVC.Service.SpotifyTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {

    @Autowired
    private SpotifyTokenService spotifyTokenService;

    @GetMapping("/api/spotify/token")
    public String getSpotifyToken() {
        return spotifyTokenService.getAccessToken();
    }
}
