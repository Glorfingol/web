package com.cmpl.web.style;

import java.util.Locale;
import java.util.UUID;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.page.BACK_PAGE;

public class StyleDisplayFactoryImpl extends BackDisplayFactoryImpl implements StyleDisplayFactory {

  private final StyleService styleService;
  private final ContextHolder contextHolder;

  public StyleDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory, StyleService styleService, ContextHolder contextHolder) {
    super(menuFactory, messageSource, metaElementFactory);
    this.styleService = styleService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewStyles(BACK_PAGE backPage, Locale locale) {

    ModelAndView stylesManager = super.computeModelAndViewForBackPage(backPage, locale);
    StyleDTO style = styleService.getStyle();

    if (style == null) {
      style = initStyle();
    }

    stylesManager.addObject("style", style);

    return stylesManager;
  }

  private StyleDTO initStyle() {

    StyleDTO style = new StyleDTO();
    style.setContent("");
    style.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));

    MediaDTO media = initMedia();
    style.setMedia(media);

    return styleService.createEntity(style);
  }

  private MediaDTO initMedia() {
    MediaDTO media = new MediaDTO();
    media.setName("styles.css");
    media.setExtension(".css");
    media.setSize(0l);
    media.setContentType("text/css");
    media.setSrc(contextHolder.getMediaDisplayPath() + media.getName());
    media.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
    return media;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyles(BACK_PAGE backPage, Locale locale) {
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(backPage, locale);
    StyleDTO style = styleService.getStyle();
    if (style == null) {
      style = initStyle();
    }
    StyleForm form = new StyleForm(style);
    stylesManager.addObject("styleForm", form);

    return stylesManager;
  }

}
