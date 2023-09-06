package com.sp.fc.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 테스트를 위해 임의의 포트를 할당하여 내장 웹 서버를 실행
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicAuthenticationTest {

  @LocalServerPort
  int port;

  RestTemplate client = new RestTemplate();

  private String greetingUrl() {

    return "http://localhost:" + port + "/greeting";

  }

  @DisplayName("1. 인증 실패")
  @Test
  void test_1() {

    HttpClientErrorException httpClientErrorException =
        assertThrows(HttpClientErrorException.class, () -> {
          client.getForObject(greetingUrl(), String.class);
        });

    assertEquals(401, httpClientErrorException.getRawStatusCode());

  }

  @DisplayName("2.인증 성공")
  @Test
  void test_2() {

    HttpHeaders headers = new HttpHeaders();
    headers.add(
        HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(
            "user1:1111".getBytes()
        )
    );

    HttpEntity entity = new HttpEntity(null, headers);

    ResponseEntity<String> response =
        client.exchange(greetingUrl(), HttpMethod.GET, entity, String.class);

    assertEquals("hello", response.getBody());

  }


  @DisplayName("3. 인증성공2")
  @Test
  void test_3(){
    /**
     *
     * TestRestTemplate를 이용하면 Header에 basic 토큰으로 넣어주는 편리함이 있다.
     */
    TestRestTemplate testClient = new TestRestTemplate("user1", "1111");
    String resp = testClient.getForObject(greetingUrl(), String.class);
    assertEquals("hello", resp);
  }


  @DisplayName("4. POST 인증")
  @Test
  void test_4(){

    TestRestTemplate testClient = new TestRestTemplate("user1", "1111");
    ResponseEntity<String> response = testClient.postForEntity(greetingUrl(), "sunwoo", String.class);
    assertEquals("hello sunwoo", response.getBody());

  }


}
