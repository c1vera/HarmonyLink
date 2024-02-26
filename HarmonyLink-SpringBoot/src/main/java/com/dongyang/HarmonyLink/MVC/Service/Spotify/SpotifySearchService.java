package com.dongyang.HarmonyLink.MVC.Service.Spotify;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private ObjectMapper objectMapper;

    private String baseSearchingURL = "https://api.spotify.com/v1/search";
    // 무언가 추가하고자 한다면,  URL query로 추가하기. (ex. 검색어(q), 타입(type) 등)

    public SpotifySearchService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    /**
     * GET 요청쿼리에서 설정한 type대로 검색을 수행. (일단 track으로 고정하여 test 진행중)
     */
    public String typeSearch(String accessToken, String type, String name) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json"); // json으로 받을 것.
        headers.add("Authorization", "Bearer " + accessToken); // 엑세스 토큰 필수.

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseSearchingURL)
                .queryParam("q", name)
                .queryParam("type", "track");


        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(), // 요청 URL
                HttpMethod.GET, // 통신 방식
                httpEntity, // header 및 기타 내역
                String.class // 값을 반환받을 형식(String외에도 커스텀 class 사용 가능)
        );
        // ResponseEntity<String> response = restTemplate.getForEntity(Url, String.class)
        // getForEntity를 통한 Http 통신은 header 포함 기능이 없으므로, 엑세스 토큰이 필요한 Spotify api요청에는 적합 X

        
        //  ============================
        // 받아온 json을 재조립하는 코드 작성하기
        //  ============================

        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        ObjectNode objectNode = objectMapper.createObjectNode();
        ArrayNode arrayNode = objectNode.putArray("tracks"); // arrayNode는 미리 추가해놔도 됨..?


        ArrayNode items = (ArrayNode) jsonNode.path("tracks").path("items");
        for (int i = 0; i < items.size(); i++) {
            JsonNode trackInfo = items.get(i);

            if (trackInfo != null) { // trackInfo가 null이 아닌 경우에만 처리
                ObjectNode jsonTemp = objectMapper.createObjectNode();

                // trackName과 artistName 추출하기
                String trackName = trackInfo.path("name").asText("null");
                JsonNode artists = trackInfo.path("artists");
                String artistName = "null";
                if (artists.isArray() && artists.size() > 0) {
                    artistName = artists.get(0).path("name").asText("null");
                }

                // JsonNode -> ObjectNode
                jsonTemp.put("trackName", trackName);
                jsonTemp.put("artistName", artistName);

                arrayNode.add(jsonTemp);
            }

            else log.info("몬가 잘못됨..");
        }


        
        return objectNode.toString();
    }


}
