package com.curso.cleancode.service.signup.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PasswordValidatorTest {

  @Test
  void shouldReturnTrueWhenPasswordIsValid() {
    var v = new PasswordValidator();
    assertThat(v.validate("12345678")).isTrue();
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenPasswordIsBlank(){
    var v = new PasswordValidator();
    assertThatThrownBy(() -> v.validate(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("PASSWORD should no be blank");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenPasswordMustHaveAtLeast8Characters(){
    var v = new PasswordValidator();
    assertThatThrownBy(() -> v.validate("1234567"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("PASSWORD should have at least 8 characters");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenPasswordContainsSpaces(){
    var v = new PasswordValidator();
    assertThatThrownBy(() -> v.validate("123  4567"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("PASSWORD should not contain spaces");
  }
}
