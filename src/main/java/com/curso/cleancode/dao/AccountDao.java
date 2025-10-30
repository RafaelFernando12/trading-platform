package com.curso.cleancode.dao;

import com.curso.cleancode.model.account.Account;
import java.util.Optional;
import java.util.UUID;

public interface AccountDao {
  void save(Account account);
  Optional<Account> getById(UUID id);
}
