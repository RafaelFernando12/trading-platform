package com.curso.cleancode.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.curso.cleancode.dao.AccountAssetDao;
import com.curso.cleancode.dao.AccountDao;
import com.curso.cleancode.model.account.Account;
import com.curso.cleancode.model.account.Balance;
import com.curso.cleancode.model.account.Deposit;
import com.curso.cleancode.model.account.Withdraw;
import com.curso.cleancode.service.AccountService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

 class AccountServiceTest {

  @Test
  void shouldCreateAccountSucesfully() {
    var dao = mock(AccountDao.class);
    var signupService = new AccountService(dao,null);

    var accountId = signupService.signup("User Teste", "userteste@test.com", "01234567890", "password");

    assertThat(accountId).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenCpfIsInvalid() {
    var dao = mock(AccountDao.class);
    var signupService = new AccountService(dao, null);

    assertThatThrownBy(() -> signupService.signup("User Teste", "userteste@test.com", "123456789", "password"))
    .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("CPF should contain 11 digits");
  }

  @Test
   void shouldCreateDepositSucesfully() {
    var accountDao = mock(AccountDao.class);
    var accountAssetDao = mock(AccountAssetDao.class);
    var accountService = new AccountService(accountDao, accountAssetDao);
    var accountId = accountService.signup("User Teste", "userteste@test.com", "01234567890", "password");

    assertThat(accountId).isNotNull();

    when(accountDao.getById(accountId)).thenReturn(Optional.of(
        new Account(accountId, "User Teste", "userteste@test.com", "01234567890", "password")
    ));
    when(accountAssetDao.findByAccountIdAndAssetId(accountId, "USD")).thenReturn(Optional.empty());
    var deposit = new Deposit(accountId, "USD", 1000);
    accountService.deposit(deposit);

    verify(accountAssetDao).insert(deposit);

    when(accountAssetDao.findByAccountId(accountId)).thenReturn(List.of(
        new Balance("USD", new BigDecimal("1000"))
    ));

    var output = accountService.getAccount(accountId);
    assertThat(output).isPresent();
    var result = output.get();
    assertThat(result).containsEntry("account_id", accountId);

    List<Balance> balances = (List<Balance>) result.get("balances");
    assertThat(balances).isNotEmpty();
    assertThat(balances.get(0).getAssetId()).isEqualTo("USD");
    assertThat(balances.get(0).getQuantity()).isEqualByComparingTo(new BigDecimal("1000"));
  }

  @Test
  void shouldThrowExeceptionWhenDepositIsInvalid() {
   var accountDao = mock(AccountDao.class);
   var accountAssetDao = mock(AccountAssetDao.class);
   var accountService = new AccountService(accountDao, accountAssetDao);
   var accountId = accountService.signup("User Teste", "userteste@test.com", "01234567890", "password");

   assertThat(accountId).isNotNull();

   when(accountDao.getById(accountId)).thenReturn(Optional.of(
       new Account(accountId, "User Teste", "userteste@test.com", "01234567890", "password")
   ));
   when(accountAssetDao.findByAccountIdAndAssetId(accountId, "USD")).thenReturn(Optional.empty());
   var deposit = new Deposit(accountId, "USD", 0);

   assertThatThrownBy(() -> accountService.deposit(deposit))
   .isInstanceOf(IllegalArgumentException.class)
   .hasMessage("Quantity must be greater than zero");
  }

  @Test
  void shouldCreateWithdrawSucesfully() {
    var accountDao = mock(AccountDao.class);
    var accountAssetDao = mock(AccountAssetDao.class);
    var accountService = new AccountService(accountDao, accountAssetDao);
    var accountId = accountService.signup("User Teste", "userteste@test.com", "01234567890", "password");

    assertThat(accountId).isNotNull();

    when(accountDao.getById(accountId)).thenReturn(Optional.of(
        new Account(accountId, "User Teste", "userteste@test.com", "01234567890", "password")
    ));
    when(accountAssetDao.findByAccountIdAndAssetId(accountId, "USD")).thenReturn(Optional.of(
        new Balance("USD", new BigDecimal("1000"))
    ));
    when(accountAssetDao.findByAccountId(accountId)).thenReturn(List.of(
        new Balance("USD", new BigDecimal("500"))
    ));

    var withdraw = new Withdraw(accountId, "USD", 500);
    accountService.withdraw(withdraw);

    verify(accountAssetDao).update(accountId, "USD", new BigDecimal("500"));

    var output = accountService.getAccount(accountId);
    assertThat(output).isPresent();
    var result = output.get();
    assertThat(result).containsEntry("account_id", accountId);

    List<Balance> balances = (List<Balance>) result.get("balances");
    assertThat(balances).isNotEmpty();
    assertThat(balances.get(0).getAssetId()).isEqualTo("USD");
    assertThat(balances.get(0).getQuantity()).isEqualByComparingTo(new BigDecimal("500"));
  }

   @Test
   void shouldThrowExceptionWhenAssetIdIsInvalid() {
     var accountDao = mock(AccountDao.class);
     var accountAssetDao = mock(AccountAssetDao.class);
     var accountService = new AccountService(accountDao, accountAssetDao);
     var accountId = accountService.signup("User Teste", "userteste@test.com", "01234567890", "password");

     assertThat(accountId).isNotNull();

     var input = new Withdraw(accountId, "ETH", 500);

     when(accountDao.getById(accountId)).thenReturn(Optional.of(
         new Account(accountId, "User Teste", "userteste@test.com", "01234567890", "password")
     ));

     assertThatThrownBy(() -> accountService.withdraw(input))
     .isInstanceOf(IllegalArgumentException.class)
     .hasMessage("Invalid assetId. Allowed: BTC, USD");
   }

   @Test
   void shouldThrowExceptionWhenWithdrasHasInsufficientFounds() {
     var accountDao = mock(AccountDao.class);
     var accountAssetDao = mock(AccountAssetDao.class);
     var accountService = new AccountService(accountDao, accountAssetDao);
     var accountId = accountService.signup("User Teste", "userteste@test.com", "01234567890", "password");

     assertThat(accountId).isNotNull();

     when(accountDao.getById(accountId)).thenReturn(Optional.of(
         new Account(accountId, "User Teste", "userteste@test.com", "01234567890", "password")
     ));
     when(accountAssetDao.findByAccountIdAndAssetId(accountId, "USD")).thenReturn(Optional.of(
         new Balance("USD", new BigDecimal("500"))
     ));
     when(accountAssetDao.findByAccountId(accountId)).thenReturn(List.of(
         new Balance("USD", new BigDecimal("1000"))
     ));
      var input = new Withdraw(accountId,"USD", 1000);
     assertThatThrownBy(() -> accountService.withdraw(input))
         .isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Insufficient funds");
   }
}

