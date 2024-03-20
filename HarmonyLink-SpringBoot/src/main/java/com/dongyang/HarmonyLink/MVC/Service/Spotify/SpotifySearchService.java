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
     * GET 요청쿼리에서 설정한 track, artist 중 특정 type대로 검색을 수행.
     */
    public String typeSearch(String accessToken, String type, String name) throws JsonProcessingException {

        /* #1 . Spotify search api 요청을 통한 값 받아오기 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json"); // json으로 받을 것.
        headers.add("Authorization", "Bearer " + accessToken); // 엑세스 토큰 필수.

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseSearchingURL)
                .queryParam("q", name)
                .queryParam("type", type); // track or artist


        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(), // 요청 URL
                HttpMethod.GET, // 통신 방식
                httpEntity, // header 및 기타 내역
                String.class // 값을 반환받을 형식(String외에도 커스텀 class 사용 가능)
        );
        // ResponseEntity<String> response = restTemplate.getForEntity(Url, String.class)
        // getForEntity를 통한 Http 통신은 header 포함 기능이 없으므로, 엑세스 토큰이 필요한 Spotify api요청에는 적합 X

        /* #2 . 받아온 response를 특정 값만 골라 json으로 재조립하기 */
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        ObjectNode objectNode = objectMapper.createObjectNode();

        // 검색 결과의 값이 없는 경우, 어떤 값을 return할 것인가?
        // if(jsonNode.isEmpty()) return objectNode.put("message", "검색 내역이 없습니다.").toString();

        if(type.equals("track")) {
            ArrayNode arrayNode = objectNode.putArray("tracks"); // arrayNode는 미리 추가해놔도 됨..?

            ArrayNode items = (ArrayNode) jsonNode.path("tracks").path("items");
            // JsonNode가 []  띄고 있는 경우 ArrayNode로 형변환 가능

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
                    /* 트랙명, 아티스트명 삽입*/
                    jsonTemp.put("trackName", trackName);
                    jsonTemp.put("artistName", artistName);

                    /* 앨범 커버 이미지 가져오기 */
                    JsonNode trackImageNode = trackInfo.path("album").path("images").get(0);

                    if(trackImageNode != null) {
                        String imgUri =
                                trackImageNode.path("url").asText("");
                        int imgWidth =
                                trackImageNode.path("height").asInt(100);
                        int imgHeight =
                                trackImageNode.path("width").asInt(100);

                        jsonTemp.put("imgUri", imgUri);
                        jsonTemp.put("imgWidth", imgWidth);
                        jsonTemp.put("imgHeight", imgHeight);
                    }
                    
                    
                    /* 해당 트랙을 저장해두는 spotify key 가져오기 - primary key 사용 용도 */
                    jsonTemp.put("spotifyKey", trackInfo.path("uri").asText("nonValue"));

                    arrayNode.add(jsonTemp);
                } else log.info("몬가 잘못됨..");
            }
        }

        // artist 기능 임시 제거. 추후 기능 확장시 재사용 가능성 있음.
        /*
        if(type.equals("artist")) {
            ArrayNode arrayNode = objectNode.putArray("artist"); // arrayNode는 미리 추가해놔도 됨..?


            ArrayNode items = (ArrayNode) jsonNode.path("artists").path("items");
            // JsonNode가 []  띄고 있는 경우 ArrayNode로 형변환 가능

            for (int i = 0; i < items.size(); i++) {
                JsonNode artistInfo = items.get(i);

                if (artistInfo != null) { // trackInfo가 null이 아닌 경우에만 처리
                    ObjectNode jsonTemp = objectMapper.createObjectNode();

                    // artistName 추출
                    String artistName = artistInfo.path("name").asText("null");

                    ArrayNode genreArrNode = jsonTemp.putArray("genreArrNode");
                    for(JsonNode genre : artistInfo.path("genres")) genreArrNode.add(genre);


                    // JsonNode -> ObjectNode
                    jsonTemp.put("artistName", artistName);

                    // artist 이미지 가져오기
                    JsonNode artistImageNode = artistInfo.path("images").get(0);

                    if(artistImageNode != null) {
                        String imgUri =
                                artistImageNode.path("url").asText("");
                        int imgWidth =
                                artistImageNode.path("height").asInt(100);
                        int imgHeight =
                                artistImageNode.path("width").asInt(100);

                        jsonTemp.put("imgUri", imgUri);
                        jsonTemp.put("imgWidth", imgWidth);
                        jsonTemp.put("imgHeight", imgHeight);

                    }

                    arrayNode.add(jsonTemp);
                } else log.info("몬가 잘못됨..");
            }
        }

         */


        
        return objectNode.toString();
    }


}
