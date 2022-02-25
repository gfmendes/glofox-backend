package com.glofox.platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MemberAlreadyInClassException extends RuntimeException {

  public MemberAlreadyInClassException(String msg) {
    super(msg);
  }
}
