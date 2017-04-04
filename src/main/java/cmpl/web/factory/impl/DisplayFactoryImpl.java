package cmpl.web.factory.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.footer.Footer;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

public class DisplayFactoryImpl implements DisplayFactory {

  private final MenuBuilder menuBuilder;
  private final FooterBuilder footerBuilder;
  private final MetaElementBuilder metaElementBuilder;
  private final MessageSource messageSource;

  protected DisplayFactoryImpl(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MetaElementBuilder metaElementBuilder, MessageSource messageSource) {
    this.menuBuilder = menuBuilder;
    this.messageSource = messageSource;
    this.footerBuilder = footerBuilder;
    this.metaElementBuilder = metaElementBuilder;
  }

  public static DisplayFactoryImpl fromBuilders(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MetaElementBuilder metaElementBuilder, MessageSource messageSource) {
    return new DisplayFactoryImpl(menuBuilder, footerBuilder, metaElementBuilder, messageSource);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGE page, String languageCode) {

    Locale locale = new Locale(languageCode);

    ModelAndView model = new ModelAndView(computeI18nLabel(page.getTileName(), locale));

    model.addObject("menuItems", computeMenuItems(locale));
    model.addObject("metaItems", computeMetaElements(locale, page));
    model.addObject("footer", computeFooter(locale));

    return model;

  }

  List<MenuItem> computeMenuItems(Locale locale) {
    return menuBuilder.computeMenuItems(locale);
  }

  Footer computeFooter(Locale locale) {
    return footerBuilder.computeFooter(locale);
  }

  List<MetaElement> computeMetaElements(Locale locale, PAGE page) {
    return metaElementBuilder.computeMetaElementsForPage(locale, page);
  }

  String computeI18nLabel(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }
}
