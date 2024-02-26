package com.dongyang.HarmonyLink.MVC.Service.Spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class SpotifyTokenService {
  /* https://developer.spotify.com/documentation/web-api/tutorials/client-credentials-flow */
  // 이해 시, 위 내용 참조하기!


  @Value("${spotify.client.id}")
  private String clientId = "ac7a2e1f0fd54e72a09ed09e6bd4d215";
  
  @Value("${spotify.client.secret}")
  private String clientSecret = "958a76bfe06a41aa98f20d7b6087fd1a"; 

  // 토큰과 만료 시간을 관리하기 위한 변수
  private String accessToken = null;
  private long expiryTime = 0;

  private RestTemplate restTemplate;

  public SpotifyTokenService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /** 토큰이 만료되었는지 확인하고, 만료된 경우 재생성하여 제공*/
  public synchronized String getAccessToken() {
    // 현재 시간을 기준으로 토큰이 만료되었는지 확인
    if (accessToken == null || System.currentTimeMillis() >= expiryTime) {
      refreshAccessToken();
    }
    return accessToken;
  }

  private void refreshAccessToken() {


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    String credentials = clientId + ":" + clientSecret; // clientid와 clientSecret 이용
    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes()); // base64로 암호화한다
    headers.add("Authorization", "Basic " + encodedCredentials); // 암호화된 내용을 header로 사용함.

    HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

    ResponseEntity<Map> response = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, Map.class);

    if (response.getStatusCode() == HttpStatus.OK) {
      Map<String, Object> responseBody = response.getBody();
      accessToken = responseBody.get("access_token").toString();
      // 액세스 토큰을 콘솔에 출력
      log.info("New Access Token: " + accessToken);

      int expiresIn = (int) responseBody.get("expires_in");
      expiryTime = System.currentTimeMillis() + (expiresIn * 1000); // 만료 시간 업데이트
    } else {
      throw new RuntimeException("Failed to retrieve access token");
    }
}

}
