package com.cmpl.web.style;

import java.util.Locale;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.media.MediaDTOBuilder;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

public class StyleDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<Style> implements StyleDisplayFactory {

  private final StyleService styleService;
  private final ContextHolder contextHolder;

  public StyleDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource, StyleService styleService,
      ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry);
    this.styleService = styleService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewStyles(Locale locale) {

    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_VIEW, locale);
    StyleDTO style = styleService.getStyle();

    if (style == null) {
      style = initStyle();
    }

    stylesManager.addObject("style", style);

    return stylesManager;
  }

  StyleDTO initStyle() {
    StyleDTO style = StyleDTOBuilder.create().content("").media(initMedia())
        .id(Math.abs(UUID.randomUUID().getLeastSignificantBits())).build();
    return styleService.createEntity(style);
  }

  MediaDTO initMedia() {
    return MediaDTOBuilder.create().name("styles.css").extension(".css").size(0l).contentType("text/css")
        .src(contextHolder.getMediaDisplayPath() + "styles.css")
        .id(Math.abs(UUID.randomUUID().getLeastSignificantBits())).build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyles(Locale locale) {
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_UPDATE, locale);
    StyleDTO style = styleService.getStyle();
    if (style == null) {
      style = initStyle();
    }
    StyleForm form = new StyleForm(style);
    stylesManager.addObject("styleForm", form);

    return stylesManager;
  }

  @Override
  protected String getBaseUrl() {
    return null;
  }

  @Override
  protected Page<Style> computeEntries(Locale locale, int pageNumber) {
    return null;
  }

}
