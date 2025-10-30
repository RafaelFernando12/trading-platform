package com.curso.cleancode.service.signup.validators;

import org.apache.commons.lang3.StringUtils;

public class NameValidator {

  public boolean validate(String name){
    if(StringUtils.isBlank(name)) throw new IllegalArgumentException("Invalid name");
    String normalized = name.trim().replaceAll("\\s+", " ");
    String[] parts = normalized.split(" ");
    if (parts.length < 2) throw new IllegalArgumentException("Name should be first name and second name");

    for (String p : parts) {
      if (!p.matches("^[\\p{L}]+(?:[-'][\\p{L}]+)*$")) return false;
    }
    return true;
  }
}
