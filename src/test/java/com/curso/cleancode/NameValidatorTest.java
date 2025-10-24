package com.curso.cleancode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NameValidatorTest {
  @Test
  void validateName() {
      var v = new NameValidator();
      assertThat(v.validate("John Doe")).isTrue();

      assertThat(v.validate("John")).isFalse();
      assertThat(v.validate("John   ")).isFalse();
      assertThat(v.validate("")).isFalse();
      assertThat(v.validate("   ")).isFalse();
      assertThat(v.validate(null)).isFalse();
      assertThat(v.validate("@")).isFalse();
  }
}
