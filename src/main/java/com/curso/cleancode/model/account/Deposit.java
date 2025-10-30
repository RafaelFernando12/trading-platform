package com.curso.cleancode.model.account;

import java.util.UUID;

public class Deposit {
  private final UUID accountId;
  private final String assetId;
  private final Integer quantity;

  public Deposit(UUID accountId, String assetId, Integer quantity) {
    this.accountId = accountId;
    this.assetId = assetId;
    this.quantity = quantity;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public String getAssetId() {
    return assetId;
  }

  public Integer getQuantity() {
    return quantity;
  }
}
