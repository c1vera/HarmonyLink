package com.dongyang.HarmonyLink.MVC.Service.Spotify;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/** 본 서비스는 일단 앨범이름 검색만을 기준으로 합니다. */
@Service
@Slf4j
public class SpotifySearchService {

    private RestTemplate restTemplate;

    private String baseSearchingURL = "https://api.spotify.com/v1/search";
    // 무언가 추가하고자 한다면,  URL query로 추가하기. (ex. 검색어(q), 타입(type) 등)

    public SpotifySearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 앨범 이름만을 기준으로 검색하는 형식. 클라이언트에서 추가 폼을 활용하여 검색 타입 지정시킬 수 있게 가능함.
     * => java에서 UriComponentsBuilder를 활용하여 각 query를 알맞게 추가하여 GET 요청 URL 작성.
     *
     * @return
     */
    public String searchToAlbumName(String accessToken, String name) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseSearchingURL)
                .queryParam("q", name)
                .queryParam("type", "album"); // type도 수정하여 기능 확장 가능

        // String 타입을 Json을 사용할 수 있는 클래스 등으로 변환하여 얻어오는게 가능할까?
        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        log.info(String.valueOf(response.getBody()));

        return response.getBody();
    }


}
