package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<TrackEntity, String> {

    /* track이 지니는 spotifyKey를 기준으로 찾기 */
    public Optional<TrackEntity> findBySpotifyKey(String spotifyKey);
}
