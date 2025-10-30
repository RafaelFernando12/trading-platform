package com.curso.cleancode.service.signup.validators;

import org.apache.commons.lang3.StringUtils;

public class EmailValidator {
  public boolean validate(String email) {
    if(StringUtils.isBlank(email)) throw new IllegalArgumentException("EMAIL should not be blank");
    email = email.trim();
    if (email.contains(" ")) throw new IllegalArgumentException("EMAIL should not contain spaces");
    int at = email.indexOf('@');
    int lastAt = email.lastIndexOf('@');
    if (at <= 0 || at != lastAt || at == email.length() - 1) throw new IllegalArgumentException("EMAIL should contain one @ and not be the last character");
    String domain = email.substring(at + 1);
    return !domain.isEmpty() && domain.contains(".");
  }
}
