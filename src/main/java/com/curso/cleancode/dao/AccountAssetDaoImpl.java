package com.curso.cleancode.dao;

import com.curso.cleancode.model.account.Balance;
import com.curso.cleancode.model.account.Deposit;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountAssetDaoImpl implements AccountAssetDao{
  private final JdbcTemplate jdbc;

  public AccountAssetDaoImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public List<Balance> findByAccountId(UUID accountId) {
    return jdbc.query(
        "select asset_id, quantity from ccca.account_asset where account_id = ?",
        new Object[]{accountId},
        new RowMapper<Balance>() {
          @Override
          public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
            String assetId = rs.getString("asset_id");
            BigDecimal quantity = rs.getBigDecimal("quantity");
            return new Balance(assetId, quantity);
          }
        }
    );
  }

  @Override
  public Optional<Balance> findByAccountIdAndAssetId(UUID accountId, String assetId) {
    List<Balance> result = jdbc.query(
        "select asset_id, quantity from ccca.account_asset where account_id = ? and asset_id = ?",
        new Object[]{accountId, assetId},
        (rs, rowNum) -> new Balance(rs.getString("asset_id"), rs.getBigDecimal("quantity"))
    );
    return result.stream().findFirst();
  }

  @Override
  public void insert(Deposit deposit) {
    jdbc.update(
        "insert into ccca.account_asset (account_id, asset_id, quantity) values (?, ?, ?)",
        deposit.getAccountId(), deposit.getAssetId(), deposit.getQuantity()
    );
  }

  @Override
  public void update(UUID accountId, String assetId, BigDecimal newQuantity) {
    jdbc.update(
        "update ccca.account_asset set quantity = ? where account_id = ? and asset_id = ?",
        newQuantity, accountId, assetId
    );
  }
}

