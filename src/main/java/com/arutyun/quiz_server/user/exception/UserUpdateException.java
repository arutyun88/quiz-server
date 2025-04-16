package com.arutyun.quiz_server.user.exception;

import com.arutyun.quiz_server.common.exception.BaseBadRequestException;

public class UserUpdateException extends BaseBadRequestException {
  public UserUpdateException(String message) {
    super("USER_NOT_UPDATED", message);
  }
}
