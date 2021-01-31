package com.leverx.blog.exceptions;

/** @author Andrey Egorov */
public class CustomUserException extends RuntimeException {

  public CustomUserException() {}

  public CustomUserException(final String message) {
    super(message);
  }

  public CustomUserException(final Throwable err) {
    super(err);
  }

  public CustomUserException(final String message, final Throwable err) {
    super(message, err);
  }
}
