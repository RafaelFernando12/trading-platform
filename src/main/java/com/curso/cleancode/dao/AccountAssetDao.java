package com.curso.cleancode.dao;

import com.curso.cleancode.model.account.Deposit;
import com.curso.cleancode.model.account.Balance;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

public interface AccountAssetDao {
  List<Balance> findByAccountId(UUID accountId);
  Optional<Balance> findByAccountIdAndAssetId(UUID accountId, String assetId);
  void insert(Deposit deposit);
  void update(UUID accountId, String assetId, BigDecimal newQuantity);
}
