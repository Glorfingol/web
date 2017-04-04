package cmpl.web.factory.impl;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.model.page.BACK_PAGE;

public class BackDisplayFactoryImpl implements BackDisplayFactory {

  private final MessageSource messageSource;

  protected BackDisplayFactoryImpl(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static BackDisplayFactoryImpl fromBuilders(MessageSource messageSource) {
    return new BackDisplayFactoryImpl(messageSource);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, String languageCode) {

    Locale locale = new Locale(languageCode);

    ModelAndView modelAndView = new ModelAndView(computeI18nLabel(backPage.getTile(), locale));

    return modelAndView;
  }

  String computeI18nLabel(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
