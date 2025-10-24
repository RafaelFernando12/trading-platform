package com.curso.cleancode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EmailValidatorTest {
  @Test
  void validateEmail() {
    var v = new EmailValidator();
    assertThat(v.validate("john@doe.com")).isTrue();
    assertThat(v.validate("user.name+tag@domain.co")).isTrue();

    assertThat(v.validate(null)).isFalse();
    assertThat(v.validate("")).isFalse();
    assertThat(v.validate("john")).isFalse();
    assertThat(v.validate("john@")).isFalse();
    assertThat(v.validate("@doe.com")).isFalse();
    assertThat(v.validate("john@doe")).isFalse();
    assertThat(v.validate("john doe@doe.com")).isFalse();
    assertThat(v.validate("john@@doe.com")).isFalse();
  }
}
