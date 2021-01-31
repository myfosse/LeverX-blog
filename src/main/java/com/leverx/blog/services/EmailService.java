package com.leverx.blog.services;

import javax.mail.MessagingException;

/** @author Andrey Egorov */
public interface EmailService {

  void sendMessageForConfirmAccount(final String email, final String token) throws MessagingException;

  void sendMessageForResetPassword(final String email, final String token) throws MessagingException;
}
