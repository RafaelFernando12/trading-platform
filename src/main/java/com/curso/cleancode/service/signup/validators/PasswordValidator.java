package com.curso.cleancode.service.signup.validators;


import org.apache.commons.lang3.StringUtils;

public class PasswordValidator {
  public boolean validate(String password){
    if(StringUtils.isBlank(password)) throw new IllegalArgumentException("PASSWORD should no be blank");
    if(password.length() < 8) throw new IllegalArgumentException("PASSWORD should have at least 8 characters");
    if(password.contains(" ")) throw new IllegalArgumentException("PASSWORD should not contain spaces");
    return true;
  }
}
