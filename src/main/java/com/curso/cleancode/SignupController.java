package com.curso.cleancode;

import static io.micrometer.common.util.StringUtils.isBlank;

import java.util.Map;
import java.util.UUID;
import javax.naming.Name;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

  private JdbcTemplate jdbc;

  public SignupController(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> signup(@RequestBody Map<String, Object> body) {
    var name = (String) body.get("name");
    var email = (String) body.get("email");
    var document = (String) body.get("document");
    var password = (String) body.get("password");

    var cpfValidator = new CpfValidator();
    if (!cpfValidator.validate(document)) {
      return ResponseEntity.badRequest().body(Map.of("error", "Invalid cpf"));
    }

    var nameValidator = new NameValidator();
    if(!nameValidator.validate(name)){
      return ResponseEntity.badRequest().body(Map.of("error", "Invalid name"));
    }

    var emailValidator = new EmailValidator();
    if (!emailValidator.validate(email)) {
      return ResponseEntity.badRequest().body(Map.of("error", "Invalid email"));
    }

    var passwordValidator = new PasswordValidator();
    if (!passwordValidator.validate(password)) {
      return ResponseEntity.badRequest().body(Map.of("error", "Invalid password"));
    }

    UUID accountId = UUID.randomUUID();
    jdbc.update(
        "insert into ccca.account (account_id, name, email, document, password) values (?, ?, ?, ?, ?)",
        accountId, name, email, document, password
    );

    return ResponseEntity.ok(Map.of("accountId", accountId));
  }

  @GetMapping("/accounts/{accountId}")
  public ResponseEntity<Map<String, Object>> getAccount(@PathVariable("accountId") UUID accountId) {
    var rows = jdbc.queryForList(
        "select account_id, name, email, document, password from ccca.account where account_id = ?",
        accountId
    );
    if (rows.isEmpty()) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(rows.get(0));
  }
}
