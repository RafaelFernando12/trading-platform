package com.curso.cleancode;

import static io.micrometer.common.util.StringUtils.isBlank;

import org.apache.commons.lang3.StringUtils;

public class NameValidator {

  public boolean validate(String name){
    if(StringUtils.isBlank(name)) return false;
    String normalized = name.trim().replaceAll("\\s+", " ");
    String[] parts = normalized.split(" ");
    if (parts.length < 2) return false;

    for (String p : parts) {
      if (!p.matches("^[\\p{L}]+(?:[-'][\\p{L}]+)*$")) return false;
    }
    return true;
  }
}
