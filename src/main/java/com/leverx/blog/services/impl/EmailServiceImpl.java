package com.leverx.blog.services.impl;

import com.leverx.blog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/** @author Andrey Egorov */
@Service
public class EmailServiceImpl implements EmailService {

  @Value("${app.confirmUser}")
  private String passwordCreateURL;

  @Value("${spring.mail.username}")
  private String setFromMail;

  private final JavaMailSender mailSender;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendMessageForSendResetCodeToMail(String email, String token)
      throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(setFromMail);
    helper.setTo(email);

    String subject = "Here's the link to reset your password";

    String content =
        "<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p><a href=\""
            + passwordCreateURL
            + token
            + "\">Change my password</a></p>"
            + "<br>"
            + "<p>Ignore this email if you do remember your password, "
            + "or you have not made the request.</p>";

    helper.setSubject(subject);

    helper.setText(content, true);
    mailSender.send(message);
  }
}
