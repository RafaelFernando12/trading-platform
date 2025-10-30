package com.curso.cleancode.service.signup.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EmailValidatorTest {
  @Test
  void shouldReturnTrueWhenEmailIsValid() {
    var v = new EmailValidator();
    assertThat(v.validate("john@doe.com")).isTrue();
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenEmailIsBlank(){
    var v = new EmailValidator();
    assertThatThrownBy(() -> v.validate(" "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("EMAIL should not be blank");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenEmailIsInvalid(){
    var v = new EmailValidator();
    assertThatThrownBy(() -> v.validate("johndoe.com"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("EMAIL should contain one @ and not be the last character");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenEmailContainsSpaces(){
    var v = new EmailValidator();
    assertThatThrownBy(() -> v.validate("john  @doe.com"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("EMAIL should not contain spaces");
  }
}
