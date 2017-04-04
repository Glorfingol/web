package cmpl.web.factory.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.page.BACK_PAGE;

public class BackDisplayFactoryImpl implements BackDisplayFactory {

  private final MessageSource messageSource;
  private final MenuBuilder menuBuilder;
  private final FooterBuilder footerBuilder;

  protected BackDisplayFactoryImpl(MenuBuilder menuBuilder, FooterBuilder footerBuilder, MessageSource messageSource) {
    this.menuBuilder = menuBuilder;
    this.messageSource = messageSource;
    this.footerBuilder = footerBuilder;
  }

  public static BackDisplayFactoryImpl fromBuilders(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MessageSource messageSource) {
    return new BackDisplayFactoryImpl(menuBuilder, footerBuilder, messageSource);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, String languageCode) {

    Locale locale = new Locale(languageCode);

    ModelAndView modelAndView = new ModelAndView(computeI18nLabel(backPage.getTile(), locale));
    modelAndView.addObject("menuItems", computeBackMenuItems(locale));
    modelAndView.addObject("footer", computeFooter(locale));

    return modelAndView;
  }

  List<MenuItem> computeBackMenuItems(Locale locale) {
    return menuBuilder.computeBackMenuItems(locale);
  }

  Footer computeFooter(Locale locale) {
    return footerBuilder.computeFooter(locale);
  }

  String computeI18nLabel(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
