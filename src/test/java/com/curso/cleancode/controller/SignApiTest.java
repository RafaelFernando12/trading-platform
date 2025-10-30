package com.curso.cleancode.controller;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignApiTest {

  @Autowired
  TestRestTemplate http;
  @Autowired
  JdbcTemplate jdbc;

  @BeforeEach
  void setup() {
    jdbc.update("delete from ccca.account");
  }

  @Test
  void shouldCreateAnAccountSuccesfully() {
    var body = Map.of(
        "name","John Doe",
        "email","john@doe.com",
        "document","935.411.347-80",
        "password","secret123"
    );
    ResponseEntity<Map> res = http.postForEntity("/api/signup", body, Map.class);
    assertThat(res.getStatusCode().value()).isEqualTo(200);
    assertThat(res.getBody().get("accountId")).isNotNull();

    var accountId = res.getBody().get("accountId");
    var got = http.getForEntity("/api/signup/accounts/{id}", Map.class, accountId);
    assertThat(got.getStatusCode().value()).isEqualTo(200);
    assertThat(got.getBody().get("email")).isEqualTo("john@doe.com");
  }

  @Test
  void shouldReturnBadRequestWhenCpfIsInvalid() {
    var body = Map.of(
        "name","John Does",
        "email","john@doe.com",
        "document","65.906.971/0001-40",
        "password","secret123"
    );
    ResponseEntity<Map> res = http.postForEntity("/api/signup", body, Map.class);
    assertThat(res.getStatusCode().value()).isEqualTo(400);
    assertThat(res.getBody().get("error")).isEqualTo("CPF should contain 11 digits");
    assertThat(res.getBody().get("accountId")).isNull();
  }
}
