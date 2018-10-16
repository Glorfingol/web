package com.cmpl.web.core.factory;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.news.entry.RenderingNewsEntryDTO;
import com.cmpl.web.core.page.RenderingPageDTO;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.style.RenderingStyleDTO;
import com.cmpl.web.core.website.RenderingWebsiteDTO;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import com.cmpl.web.core.widget.RenderingWidgetDTOBuilder;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 *
 * @author Louis
 */
@CacheConfig(cacheNames = "display")
public class DefaultDisplayFactory extends DefaultBaseDisplayFactory implements DisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultDisplayFactory.class);


  private final PluginRegistry<WidgetProviderPlugin, String> widgetProviders;


  private final DisplayFactoryCacheManager displayFactoryCacheManager;

  public DefaultDisplayFactory(WebMessageSource messageSource,
    PluginRegistry<WidgetProviderPlugin, String> widgetProviders,
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    super(messageSource);

    this.widgetProviders = Objects.requireNonNull(widgetProviders);
    this.displayFactoryCacheManager = Objects.requireNonNull(displayFactoryCacheManager);

  }

  @Override
  public ModelAndView computeModelAndViewForBlogEntry(String newsEntryId, String widgetId,
    Locale locale) {

    LOGGER.debug("Construction de l'entree de blog d'id {}", newsEntryId);

    ModelAndView model = new ModelAndView(
      computeWidgetTemplate(RenderingWidgetDTOBuilder.create().type("BLOG_ENTRY").build(), locale));
    RenderingNewsEntryDTO renderingWidgetDTO = displayFactoryCacheManager
      .getNewsEntryById(Long.parseLong(newsEntryId));
    model.addObject("newsBean", renderingWidgetDTO);

    LOGGER.debug("Entree de blog {}  prête", newsEntryId);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForWidget(String widgetName, Locale locale, int pageNumber,
    String query) {

    LOGGER.debug("Construction du wiget {}", widgetName);

    RenderingWidgetDTO widget = displayFactoryCacheManager.findWidgetByName(widgetName);
    ModelAndView model = new ModelAndView(computeWidgetTemplate(widget, locale));

    model.addObject("pageNumber", pageNumber);

    Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale,
      query);
    if (!CollectionUtils.isEmpty(widgetModel)) {
      widgetModel.forEach((key, value) -> model.addObject(key, value));
    }
    model.addObject("widgetName", widget.getName());
    model.addObject("asynchronous", widget.isAsynchronous());

    LOGGER.debug("Widget {} prêt", widgetName);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForWebsitePage(String websiteName, String pageHref,
    Locale locale,
    int pageNumber, String query) {

    LOGGER.debug("Construction de la page  {} pour le site {}", pageHref, websiteName);
    RenderingWebsiteDTO websiteDTO = displayFactoryCacheManager.findWebsiteByName(websiteName);
    if (websiteDTO == null) {
      return new ModelAndView("404");
    }

    Optional<RenderingPageDTO> optionalPage = displayFactoryCacheManager
      .getPageForHrefAndWebsite(websiteDTO.getId(), pageHref);
    if (!optionalPage.isPresent()) {
      return new ModelAndView("404");
    }

    RenderingPageDTO page = optionalPage.get();
    String pageName = page.getName();
    ModelAndView model = new ModelAndView("decorator");

    model.addObject("systemJquery", websiteDTO.isSystemJquery());
    model.addObject("systemBootstrap", websiteDTO.isSystemBootstrap());
    model.addObject("systemFontAwesome", websiteDTO.isSystemFontAwesome());
    List<RenderingStyleDTO> styles = displayFactoryCacheManager
      .getWebsiteStyles(websiteDTO.getId());
    model.addObject("styles", styles);
    model.addObject("content", computePageContent(page, locale));
    LOGGER.debug("Construction du footer pour la page  {}", pageName);
    model.addObject("footerTemplate", computePageFooter(page, locale));
    LOGGER.debug("Construction du header pour la page  {}", pageName);
    model.addObject("header", computePageHeader(page, locale));
    LOGGER.debug("Construction de la meta pour la page  {}", pageName);
    model.addObject("meta", computePageMeta(page, locale));

    LOGGER.debug("Construction des widgets pour la page {}", pageName);
    List<String> widgetIds = displayFactoryCacheManager.getWidgetsIdsForPage(page.getId());
    List<String> widgetAsynchronousNames = displayFactoryCacheManager
      .getAsynchronousWidgetsForPage(page.getId(), widgetIds);

    List<RenderingWidgetDTO> synchronousWidgets = displayFactoryCacheManager
      .getSynchronousWidgetsForPage(page.getId(),
        widgetIds);

    addSynchronousWidgetsTemplatesToModel(model, synchronousWidgets, pageNumber, locale,
      query);

    model.addObject("pageNumber", pageNumber);
    model.addObject("widgetNames", widgetAsynchronousNames);

    LOGGER.debug("Page {} prête", pageName);

    return model;

  }

  @Override
  public ModelAndView computeModelAndViewForWebsiteAMP(String websiteName, String pageHref,
    Locale locale,
    int pageNumber, String query) {
    LOGGER.debug("Construction de la page amp {} pour le site {}", pageHref, websiteName);

    RenderingWebsiteDTO websiteDTO = displayFactoryCacheManager.findWebsiteByName(websiteName);
    if (websiteDTO == null) {
      return new ModelAndView("404");
    }

    Optional<RenderingPageDTO> optionalPage = displayFactoryCacheManager
      .getPageForHrefAndWebsite(websiteDTO.getId(), pageHref);
    if (!optionalPage.isPresent()) {
      return new ModelAndView("404");
    }

    RenderingPageDTO page = optionalPage.get();
    String pageName = page.getName();
    ModelAndView model = new ModelAndView("decorator_amp");
    model.addObject("amp_content", computePageAMPContent(page, locale));
    List<String> widgetIds = displayFactoryCacheManager.getWidgetsIdsForPage(page.getId());
    List<RenderingWidgetDTO> synchronousWidgets = displayFactoryCacheManager
      .getSynchronousWidgetsForPage(page.getId(),
        widgetIds);

    addSynchronousWidgetsTemplatesToModel(model, synchronousWidgets, pageNumber, locale,
      query);

    LOGGER.debug("Page {} prête", pageName);

    return model;
  }

  private void addSynchronousWidgetsTemplatesToModel(ModelAndView model,
    List<RenderingWidgetDTO> synchronousWidgets,
    int pageNumber, Locale locale, String query) {
    synchronousWidgets.forEach(widget -> {

      Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale,
        query);
      if (!CollectionUtils.isEmpty(widgetModel)) {
        widgetModel.forEach((key, value) -> model.addObject(key, value));
      }

      model.addObject("widget_" + widget.getName(), computeWidgetTemplate(widget, locale));

    });

  }

  String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale) {
    WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor(widget.getType());
    if (!StringUtils.hasText(widget.getName())) {
      return widgetProvider.computeDefaultWidgetTemplate();
    }
    return widgetProvider.computeWidgetTemplate(widget, locale);
  }

  String computePageContent(RenderingPageDTO page, Locale locale) {
    return page.getName() + "_" + locale.getLanguage();
  }

  String computePageAMPContent(RenderingPageDTO page, Locale locale) {
    return page.getName() + "_amp_" + locale.getLanguage();
  }

  String computePageHeader(RenderingPageDTO page, Locale locale) {
    return page.getName() + "_header_" + locale.getLanguage();
  }

  String computePageMeta(RenderingPageDTO page, Locale locale) {
    return page.getName() + "_meta_" + locale.getLanguage();
  }

  String computePageFooter(RenderingPageDTO page, Locale locale) {
    return page.getName() + "_footer_" + locale.getLanguage();
  }

  public Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, int pageNumber,
    Locale locale, String query) {

    WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor(widget.getType());
    return widgetProvider.computeWidgetModel(widget, locale, pageNumber, query);

  }


}
