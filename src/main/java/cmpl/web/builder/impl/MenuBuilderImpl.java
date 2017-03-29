package cmpl.web.builder.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.builder.MenuBuilder;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.page.PAGE;

public class MenuBuilderImpl implements MenuBuilder {

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  public static MenuBuilderImpl fromResourceBundleMessageSource(ResourceBundleMessageSource resourceBundleMessageSource) {
    return new MenuBuilderImpl(resourceBundleMessageSource);
  }

  private MenuBuilderImpl(ResourceBundleMessageSource resourceBundleMessageSource) {
    this.resourceBundleMessageSource = resourceBundleMessageSource;
  }

  @Override
  public List<MenuItem> computeMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

    for (PAGE page : PAGE.values()) {
      MenuItem menuItem = new MenuItem();
      menuItem.setHref(getI18nValue(page.getHref(), locale));
      menuItem.setLabel(getI18nValue(page.getLabel(), locale));
      menuItem.setTitle(getI18nValue(page.getTitle(), locale));

      menuItems.add(menuItem);
    }

    Collections.sort(menuItems, new Comparator<MenuItem>() {
      @Override
      public int compare(MenuItem item1, MenuItem item2) {
        return item1.getTitle().compareTo(item2.getTitle());
      }
    });

    return menuItems;
  }

  String getI18nValue(String key, Locale locale) {
    return resourceBundleMessageSource.getMessage(key, null, locale);
  }

}
