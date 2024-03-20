package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.TrackRepository;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import com.dongyang.HarmonyLink.Manager.MapperManager.BuildDTOManager;
import com.dongyang.HarmonyLink.Manager.MapperManager.BuildEntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class TrackService {
    private TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /** Track에 대한 save() 수행을 대체하여 사용되는 함수
     * 게시글에 포함된 track이 존재하면 이를 반환하고, 없으면 저장한 후에 반환한다.
     *
     * */
    @Transactional
    public Optional<TrackEntity> inputTrack(TrackDTO track) {
        Optional<TrackEntity> trackEntity = trackRepository.findBySpotifyKey(track.getSpotifyKey());

        if(trackEntity.isEmpty()) {
            trackEntity = Optional.of(trackRepository.save(BuildEntityManager.buildTrackEntity(track)));
        }

        return trackEntity;
    }


    public TrackEntity getTrackBySpotifyKey(String SpotifyKey) {
        Optional<TrackEntity> trackEntity = trackRepository.findBySpotifyKey(SpotifyKey);

        if(trackEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 내역을 찾을 수 없습니다.");

        return trackEntity.get();
    }

}
