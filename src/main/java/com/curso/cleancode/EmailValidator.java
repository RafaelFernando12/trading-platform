package com.curso.cleancode;

import org.apache.commons.lang3.StringUtils;

public class EmailValidator {
  public boolean validate(String email) {
    if(StringUtils.isBlank(email)) return false;
    email = email.trim();
    if (email.contains(" ")) return false;
    int at = email.indexOf('@');
    int lastAt = email.lastIndexOf('@');
    if (at <= 0 || at != lastAt || at == email.length() - 1) return false;

    String local = email.substring(0, at);
    String domain = email.substring(at + 1);
    if (local.isEmpty() || domain.isEmpty()) return false;

    if (!domain.contains(".")) return false;

    return true;
  }
}
