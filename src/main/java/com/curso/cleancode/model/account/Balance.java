package com.curso.cleancode.model.account;

import java.math.BigDecimal;

public class Balance {
  private final String assetId;
  private final BigDecimal quantity;

  public Balance(String assetId, BigDecimal quantity) {
    this.assetId = assetId;
    this.quantity = quantity;
  }

  public String getAssetId() {
    return assetId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }
}
