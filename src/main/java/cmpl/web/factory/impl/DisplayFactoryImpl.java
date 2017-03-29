package cmpl.web.factory.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.MenuBuilder;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.page.PAGE;

public class DisplayFactoryImpl implements DisplayFactory {

  private final MenuBuilder menuBuilder;
  private final MessageSource messageSource;

  private DisplayFactoryImpl(MenuBuilder menuBuilder, MessageSource messageSource) {
    this.menuBuilder = menuBuilder;
    this.messageSource = messageSource;
  }

  public static DisplayFactoryImpl fromBuilders(MenuBuilder menuBuilder, MessageSource messageSource) {
    return new DisplayFactoryImpl(menuBuilder, messageSource);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGE page, String languageCode) {

    Locale locale = new Locale(languageCode);
    ModelAndView model = new ModelAndView(messageSource.getMessage(page.getTileName(), null, locale));
    List<MenuItem> menuItems = menuBuilder.computeMenuItems(locale);

    model.addObject("menuItems", menuItems);

    return model;

  }

}
