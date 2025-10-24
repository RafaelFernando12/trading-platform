package com.curso.cleancode;


import org.apache.commons.lang3.StringUtils;

public class PasswordValidator {
  public boolean validate(String password){
    if(StringUtils.isBlank(password)) return false;
    if(password.length() < 8) return false;
    if(password.contains(" ")) return false;
    return true;
  }
}
