package com.glofox.platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClassDoesNotExistsException extends RuntimeException {

  public ClassDoesNotExistsException(String msg) {
    super(msg);
  }
}
