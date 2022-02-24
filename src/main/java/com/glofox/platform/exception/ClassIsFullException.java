package com.glofox.platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClassIsFullException extends RuntimeException {

  public ClassIsFullException(String msg) {
    super(msg);
  }
}
