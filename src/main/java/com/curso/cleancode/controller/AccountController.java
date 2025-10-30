package com.curso.cleancode.controller;

import com.curso.cleancode.service.AccountService;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class AccountController {

  private final AccountService signupService;

  public AccountController(AccountService signupService) {
    this.signupService = signupService;
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> signup(@RequestBody Map<String, Object> body) throws Exception {
    var name = (String) body.get("name");
    var email = (String) body.get("email");
    var document = (String) body.get("document");
    var password = (String) body.get("password");
    var response = signupService.signup(name, email, document, password);
    return ResponseEntity.ok(Map.of("accountId", response));
  }

  @GetMapping("/accounts/{accountId}")
  public ResponseEntity<Map<String,Object>> getAccount(@PathVariable("accountId") UUID accountId) {
    return signupService.getAccount(accountId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
