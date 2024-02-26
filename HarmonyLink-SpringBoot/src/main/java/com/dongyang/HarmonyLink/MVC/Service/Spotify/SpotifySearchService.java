package com.dongyang.HarmonyLink.MVC.Service.Spotify;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


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
     * GET 요청쿼리에서 설정한 type대로 검색을 수행.
     */
    public String typeSearch(String accessToken, String type, String name) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json"); // json으로 받을 것.
        headers.add("Authorization", "Bearer " + accessToken); // 엑세스 토큰 필수.

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseSearchingURL)
                .queryParam("q", name)
                .queryParam("type", type);


        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(), // 요청 URL
                HttpMethod.GET, // 통신 방식
                httpEntity, // header 및 기타 내역
                String.class // 값을 반환받을 형식(String외에도 커스텀 class 사용 가능)
        );
        // ResponseEntity<String> response = restTemplate.getForEntity(Url, String.class)
        // getForEntity를 통한 Http 통신은 header 포함 기능이 없으므로, 엑세스 토큰이 필요한 Spotify api요청에는 적합 X

        log.info(String.valueOf(response.getBody()));
        
        //  ============================
        // 받아온 json을 재조립하는 코드 작성하기
        //  ============================
        
        return response.getBody();
    }


}
