package com.curso.cleancode.service;

import com.curso.cleancode.dao.AccountAssetDao;
import com.curso.cleancode.model.account.Deposit;
import com.curso.cleancode.model.account.Withdraw;
import com.curso.cleancode.service.signup.validators.CpfValidator;
import com.curso.cleancode.service.signup.validators.EmailValidator;
import com.curso.cleancode.service.signup.validators.NameValidator;
import com.curso.cleancode.service.signup.validators.PasswordValidator;
import com.curso.cleancode.dao.AccountDao;
import com.curso.cleancode.model.account.Account;
import com.curso.cleancode.model.account.Balance;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountDao accountDao;
  private final AccountAssetDao accountAssetDao;

  public AccountService(AccountDao accountDao, AccountAssetDao accountAssetDao) {

    this.accountDao = accountDao;
    this.accountAssetDao = accountAssetDao;
  }

  public UUID signup(String name, String email, String document, String password) {
    var cpfValidator = new CpfValidator();
    cpfValidator.validate(document);

    var nameValidator = new NameValidator();
    nameValidator.validate(name);

    var emailValidator = new EmailValidator();
    emailValidator.validate(email);

    var passwordValidator = new PasswordValidator();
   passwordValidator.validate(password);

    UUID accountId = UUID.randomUUID();
   accountDao.save(new Account(accountId, name, email, document, password));

   return accountId;
  }

  public Optional<Map<String,Object>> getAccount(UUID accountId) {
    return accountDao.getById(accountId).map(acc -> {
      List<Balance> balances = accountAssetDao != null ? accountAssetDao.findByAccountId(accountId) : List.of();
      return Map.of(
          "account_id", acc.getId(),
          "name", acc.getName(),
          "email", acc.getEmail(),
          "document", acc.getDocument(),
          "password", acc.getPassword(),
          "balances", balances
      );
    });
  }

  public void deposit(Deposit deposit) {
    var accountOpt = getAccount(deposit.getAccountId());
    if (accountOpt.isEmpty()) {
      throw new IllegalArgumentException("Account not found");
    }

    var asset = deposit.getAssetId();
    if (asset == null || !(asset.equals("BTC") || asset.equals("USD"))) {
      throw new IllegalArgumentException("Invalid assetId. Allowed: BTC, USD");
    }
    var quantity = deposit.getQuantity();
    if (quantity == null || quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }

    var existing = accountAssetDao.findByAccountIdAndAssetId(deposit.getAccountId(), deposit.getAssetId());
    if (existing.isPresent()) {
      BigDecimal current = existing.get().getQuantity();
      BigDecimal newTotal = current.add(BigDecimal.valueOf(deposit.getQuantity()));
      accountAssetDao.update(deposit.getAccountId(), deposit.getAssetId(), newTotal);
    } else {
      accountAssetDao.insert(deposit);
    }
  }

  public void withdraw(Withdraw withdraw) {
    var accountOpt = getAccount(withdraw.getAccountId());
    if (accountOpt.isEmpty()) {
      throw new IllegalArgumentException("Account not found");
    }

    var asset = withdraw.getAssetId();
    if (asset == null || !(asset.equals("BTC") || asset.equals("USD"))) {
      throw new IllegalArgumentException("Invalid assetId. Allowed: BTC, USD");
    }

    var quantity = withdraw.getQuantity();
    if (quantity == null || quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }

    var existing = accountAssetDao.findByAccountIdAndAssetId(withdraw.getAccountId(), withdraw.getAssetId());
    if (existing.isEmpty()) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    BigDecimal current = existing.get().getQuantity();
    BigDecimal toWithdraw = BigDecimal.valueOf(quantity);
    if (current.compareTo(toWithdraw) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    BigDecimal newTotal = current.subtract(toWithdraw);
    accountAssetDao.update(withdraw.getAccountId(), withdraw.getAssetId(), newTotal);
  }
}
