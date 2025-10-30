package com.curso.cleancode.service.signup.validators;


import org.apache.commons.lang3.StringUtils;

public class CpfValidator {
  private static final int CPF_LENGTH = 11;

  public boolean validate(String cpf) {
    if(StringUtils.isBlank(cpf)) throw new IllegalArgumentException("CPF should not be blank");
    cpf = clean(cpf);
    if (cpf.length() != CPF_LENGTH) throw new IllegalArgumentException("CPF should contain 11 digits");
    if (isSameSequence(cpf)) throw new IllegalArgumentException("CPF should not be the same sequence of digits");

    int digit1 = calculateDigit(cpf, 10);
    int digit2 = calculateDigit(cpf, 11);
    String expected = "" + digit1 + digit2;
    return extractDigit(cpf).equals(expected);
  }

  private String clean(String cpf) {
    return cpf.replaceAll("\\D", "");
  }

  private boolean isSameSequence(String cpf) {
    char first = cpf.charAt(0);
    for (int i = 1; i < cpf.length(); i++) {
      if (cpf.charAt(i) != first) return false;
    }
    return true;
  }

  private int calculateDigit(String cpf, int factor) {
    int sum = 0;
    for (int i = 0; i < cpf.length(); i++) {
      if (factor > 1) {
        int num = cpf.charAt(i) - '0';
        sum += num * factor;
        factor--;
      }
    }
    int rest = sum % CPF_LENGTH;
    return (rest < 2) ? 0 : CPF_LENGTH - rest;
  }

  private String extractDigit(String cpf) {
    return cpf.substring(9);
  }
}
