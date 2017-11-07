package cmpl.web.style;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.media.MediaDTO;
import cmpl.web.page.BACK_PAGE;

public class StyleDisplayFactoryImpl implements StyleDisplayFactory {

  private final StyleService styleService;
  private final ContextHolder contextHolder;

  public StyleDisplayFactoryImpl(StyleService styleService, ContextHolder contextHolder) {
    this.styleService = styleService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewStyles(BACK_PAGE backPage, Locale locale) {

    ModelAndView stylesManager = new ModelAndView("back/styles/view_styles");
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
    return media;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyles(BACK_PAGE backPage, Locale locale) {
    ModelAndView stylesManager = new ModelAndView("back/styles/edit_styles");
    StyleDTO style = styleService.getStyle();
    StyleForm form = new StyleForm(style);
    stylesManager.addObject("styleForm", form);

    return stylesManager;
  }

}
