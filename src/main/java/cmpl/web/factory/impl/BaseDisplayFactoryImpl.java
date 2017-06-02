package cmpl.web.factory.impl;

import java.util.Locale;

import cmpl.web.factory.BaseDisplayFactory;
import cmpl.web.message.WebMessageSource;

public class BaseDisplayFactoryImpl extends BaseFactoryImpl implements BaseDisplayFactory {

  protected BaseDisplayFactoryImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public String computeMainTitle(Locale locale) {
    return getI18nValue("main.title", locale);
  }

  @Override
  public String computeTileName(String tileName, Locale locale) {
    return getI18nValue(tileName, locale);
  }

  @Override
  public String computeHiddenLink(Locale locale) {
    return getI18nValue("back.news.href", locale);
  }

  @Override
  public String computeDecoratorFrontTileName(Locale locale) {
    return getI18nValue("decorator.front", locale);
  }

  @Override
  public String computeDecoratorBackTileName(Locale locale) {
    return getI18nValue("decorator.back", locale);
  }
}
