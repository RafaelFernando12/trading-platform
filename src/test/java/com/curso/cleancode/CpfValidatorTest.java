package com.curso.cleancode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class CpfValidatorTest {
  @Test
  void validateCpf() {
    var v = new CpfValidator();
    assertThat(v.validate("935.411.347-80")).isTrue();
    assertThat(v.validate("111.111.111-11")).isFalse();
    assertThat(v.validate("123")).isFalse();
    assertThat(v.validate(null)).isFalse();
  }
}
