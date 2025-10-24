package com.curso.cleancode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PasswordValidatorTest {

  @Test
  void validatePassword() {
    var v = new PasswordValidator();
    assertThat(v.validate("12345678")).isTrue();
    assertThat(v.validate("123")).isFalse();
    assertThat(v.validate(null)).isFalse();
    assertThat(v.validate("")).isFalse();
  }
}
