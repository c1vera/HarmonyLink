package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.Repository.TrackRepository;
import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.TrackDTO;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import com.dongyang.HarmonyLink.Manager.MapperManager.BuildEntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class TrackService {
    private TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public TrackDTO inputTrack(TrackDTO track) {
            TrackEntity trackEntity = null;
        try{
            trackEntity = trackRepository.save(BuildEntityManager.buildTrackEntity(track));
        } catch(ConstraintViolationException e) { // unique 제약 조건에 의한 에러 일어난 경우 '만'
            // 이미 값이 존재하는 경우므로, 해당 노래에 대한 정보를 불러오기
            trackEntity = trackRepository.findBySpotifyKey(track.getSpotifyKey());

            return TrackDTO.toDTO(trackEntity);
        } catch (PersistenceException e) { // 삽입이 정상적으로 수행되지 않은 나머지 경우만
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }


        if(trackEntity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");

        return TrackDTO.toDTO(trackEntity);
    }
}