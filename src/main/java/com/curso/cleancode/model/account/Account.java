package com.curso.cleancode.model.account;

import java.util.UUID;
import java.util.List;


public class Account {
  private final UUID id;
  private final String name;
  private final String email;
  private final String document;
  private final String password;
  private final List<Balance> balances;

  public Account(UUID id, String name, String email, String document, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.document = document;
    this.password = password;
    this.balances = List.of();
  }

  public UUID getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public String getDocument() { return document; }
  public String getPassword() { return password; }
  public List<Balance> getBalances() { return balances; }

  public Account(UUID id, String name, String email, String document, String password, List<Balance> balances) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.document = document;
    this.password = password;
    this.balances = balances == null ? List.of() : List.copyOf(balances);
  }
}
