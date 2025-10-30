package com.curso.cleancode.dao;

import com.curso.cleancode.model.account.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
  private final JdbcTemplate jdbc;

  public AccountDaoImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  private final RowMapper<Account> ACCOUNT_MAPPER = new RowMapper<Account>() {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Account(
          rs.getObject("account_id", UUID.class),
          rs.getString("name"),
          rs.getString("email"),
          rs.getString("document"),
          rs.getString("password")
      );
    }
  };

  @Override
  public void save(Account account) {
    jdbc.update(
        "insert into ccca.account (account_id, name, email, document, password) values (?, ?, ?, ?, ?)",
        account.getId(), account.getName(), account.getEmail(), account.getDocument(), account.getPassword()
    );
  }

  @Override
  public Optional<Account> getById(UUID id) {
    var list = jdbc.query(
        "select account_id, name, email, document, password from ccca.account where account_id = ?",
        ACCOUNT_MAPPER,
        id
    );
    return list.stream().findFirst();
  }
}
