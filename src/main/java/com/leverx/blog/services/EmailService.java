package com.leverx.blog.services;

import javax.mail.MessagingException;

/** @author Andrey Egorov */
public interface EmailService {

  void sendMessageForSendResetCodeToMail(String email, String message) throws MessagingException;
}
