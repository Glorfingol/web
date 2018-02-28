package com.cmpl.web.core.user;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.cmpl.web.core.common.mail.MailSender;

public class UserMailServiceImpl implements UserMailService {

  private final MailSender mailSender;

  public UserMailServiceImpl(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendAccountCreationEmail(UserDTO user, String token, Locale locale) throws Exception {

    Map<String, Object> context = new HashMap<>();
    context.put("user", user);
    context.put("token", token);

    this.mailSender.sendMail("user-activation", context, "activation.subject", locale, user.getEmail());
  }

  @Override
  public void sendChangePasswordEmail(UserDTO user, String token, Locale locale) throws Exception {

    Map<String, Object> context = new HashMap<>();
    context.put("user", user);
    context.put("token", token);

    this.mailSender.sendMail("user-change-password", context, "change.password.subject", locale, user.getEmail());
  }
}
