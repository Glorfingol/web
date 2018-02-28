package com.cmpl.web.core.common.mail;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cmpl.web.core.common.message.WebMessageSource;

public class MailSenderImpl implements MailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);

  private final JavaMailSender javaMailSender;
  private final TemplateEngine emailTemplateEngine;
  private final Set<String> filters;
  private final WebMessageSource messageSource;
  private final String from;
  private final String basePath;
  private final Locale defaultLocale;

  public MailSenderImpl(JavaMailSender javaMailSender, TemplateEngine emailTemplateEngine, Set<String> filters,
      WebMessageSource messageSource, String from, String basePath, Locale defaultLocale) {
    this.javaMailSender = javaMailSender;
    this.emailTemplateEngine = emailTemplateEngine;
    this.filters = filters;
    this.messageSource = messageSource;
    this.from = from;
    this.basePath = basePath;
    this.defaultLocale = defaultLocale;

  }

  @Override
  public void sendMail(String htmlTemplate, Context context, String mailSubject, Locale locale, String... mailTo)
      throws Exception {

    enrichContext(context, locale);

    final String htmlContent = emailTemplateEngine.process(htmlTemplate, context);
    final String subject = messageSource.getMessage(mailSubject, locale, new Object[]{});

    String[] destinations = filterMails(mailTo);
    if (destinations != null && destinations.length > 0) {
      final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      message.setSubject(subject);
      message.setText(htmlContent, true);
      message.setFrom(from);
      message.setTo(destinations);
      javaMailSender.send(mimeMessage);
    } else {

      LOGGER
          .info(
              "A mail with recipient(s) '{}' and subject '{}' was not sent because the recipient adresses were filtered by mailfilters {}",
              Arrays.toString(mailTo), mailSubject, filters);
    }

  }

  private void enrichContext(Context context, Locale locale) {
    context.setVariable("basePath", basePath);
    context.setVariable("lang", locale);
  }

  private String[] filterMails(String[] mailTo) {
    if (mailTo != null && filters != null && !filters.isEmpty()) {
      List<String> matched = Stream.of(mailTo).filter(s -> filters.stream().anyMatch(filter -> s.matches(filter)))
          .collect(Collectors.toList());
      return matched.toArray(new String[matched.size()]);
    } else {
      return mailTo;
    }
  }

}