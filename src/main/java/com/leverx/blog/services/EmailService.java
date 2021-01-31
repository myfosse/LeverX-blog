package com.leverx.blog.services;

import javax.mail.MessagingException;

/** @author Andrey Egorov */
public interface EmailService {

  void sendMessageForConfirmAccount(String email, String token) throws MessagingException;

  void sendMessageForResetPassword(String email, String token) throws MessagingException;
}
