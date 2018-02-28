package com.cmpl.web.core.common.mail;

import java.util.Locale;
import java.util.Map;

public interface MailSender {

  void sendMail(String htmlTemplate, Map<String, Object> context, String mailSubject, Locale locale, String... mailTo)
      throws Exception;
}
