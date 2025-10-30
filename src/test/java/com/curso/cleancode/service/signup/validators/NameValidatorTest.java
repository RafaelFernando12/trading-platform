package com.curso.cleancode.service.signup.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class NameValidatorTest {
  @Test
  void shouldReturnTrueWhenNameIsValid() {
      var v = new NameValidator();
      assertThat(v.validate("John Doe")).isTrue();
  }

  @Test
  void shouldReturnTrueForValidName(){
    var v = new NameValidator();
    assertThat(v.validate("John Doe")).isTrue();
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenNameIsInvalid(){
    var v = new NameValidator();
    assertThatThrownBy(() -> v.validate("John"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Name should be first name and second name");

  }
}
