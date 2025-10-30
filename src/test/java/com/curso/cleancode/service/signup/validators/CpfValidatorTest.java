package com.curso.cleancode.service.signup.validators;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CpfValidatorTest {
   @Test
  void shouldReturnTrueForValidCpf() {
    var v = new CpfValidator();
    assertThat(v.validate("935.411.347-80")).isTrue();
  }

  @Test
  void shouldReturnFalseForCpfWrongCheckDigits(){
    var v = new CpfValidator();
    assertThat(v.validate("935.411.347-81")).isFalse();
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenCpfIsBlank(){
     var v = new CpfValidator();
     assertThatThrownBy(() -> v.validate(" "))
         .isInstanceOf(IllegalArgumentException.class)
         .hasMessage("CPF should not be blank");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenCpfHasWrongLength(){
    var v = new CpfValidator();
    assertThatThrownBy(() -> v.validate("123"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("CPF should contain 11 digits");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenCpfHasSameSequence(){
    var v = new CpfValidator();
    assertThatThrownBy(() -> v.validate("111.111.111-11"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("CPF should not be the same sequence of digits");
  }
}
