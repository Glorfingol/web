package com.cmpl.web.core.factory;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.design.DesignDTO;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.RenderingPageDTO;
import com.cmpl.web.core.page.RenderingPageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.sitemap.SitemapDTO;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetDTOBuilder;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 *
 * @author Louis
 */
public class DefaultDisplayFactory extends DefaultBaseDisplayFactory implements DisplayFactory {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultDisplayFactory.class);


    private final RenderingPageService renderingPageService;

    private final NewsEntryService newsEntryService;

    private final WidgetPageService widgetPageService;

    private final WidgetService widgetService;

    private final PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

    private final WebsiteService websiteService;

    private final SitemapService sitemapService;

    private final DesignService designService;

    private final StyleService styleService;

    public DefaultDisplayFactory(WebMessageSource messageSource, RenderingPageService renderingPageService,
                                 NewsEntryService newsEntryService, WidgetPageService widgetPageService,
                                 WidgetService widgetService,
                                 PluginRegistry<WidgetProviderPlugin, String> widgetProviders, WebsiteService websiteService,
                                 SitemapService sitemapService, DesignService designService, StyleService styleService) {
        super(messageSource);

        this.renderingPageService = Objects.requireNonNull(renderingPageService);
        this.newsEntryService = Objects.requireNonNull(newsEntryService);
        this.widgetPageService = Objects.requireNonNull(widgetPageService);
        this.widgetService = Objects.requireNonNull(widgetService);
        this.widgetProviders = Objects.requireNonNull(widgetProviders);
        this.websiteService = Objects.requireNonNull(websiteService);
        this.designService = Objects.requireNonNull(designService);
        this.sitemapService = Objects.requireNonNull(sitemapService);
        this.styleService = Objects.requireNonNull(styleService);

    }

    @Override
    public ModelAndView computeModelAndViewForBlogEntry(String newsEntryId, String widgetId,
                                                        Locale locale) {

        LOGGER.debug("Construction de l'entree de blog d'id {}", newsEntryId);

        WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor("BLOG_ENTRY");
        ModelAndView model = new ModelAndView(
                widgetProvider.computeWidgetTemplate(WidgetDTOBuilder.create().build(), locale));
        NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));
        model.addObject("newsBean", newsEntry);

        LOGGER.debug("Entree de blog {}  prête", newsEntryId);

        return model;
    }

    @Override
    public ModelAndView computeModelAndViewForWidget(String widgetName, Locale locale, int pageNumber,
                                                     String pageName,
                                                     String query) {

        LOGGER.debug("Construction du wiget {}", widgetName);

        WidgetDTO widget = widgetService.findByName(widgetName, locale.getLanguage());
        ModelAndView model = new ModelAndView(computeWidgetTemplate(widget, locale));

        model.addObject("pageNumber", pageNumber);

        Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale, pageName,
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
        WebsiteDTO websiteDTO = websiteService.getWebsiteByName(websiteName);
        if (websiteDTO == null) {
            return new ModelAndView("404");
        }

        List<SitemapDTO> sitemaps = sitemapService.findByWebsiteId(websiteDTO.getId());
        List<RenderingPageDTO> pages = sitemaps.stream()
                .map(sitemap -> renderingPageService.getEntity(sitemap.getPageId()))
                .filter(page -> page.getHref().equals(pageHref)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pages)) {
            return new ModelAndView("404");
        }

        List<DesignDTO> designs = designService.findByWebsiteId(websiteDTO.getId());
        List<StyleDTO> styles = designs.stream()
                .map(design -> styleService.getEntity(design.getStyleId()))
                .collect(Collectors.toList());

        RenderingPageDTO page = pages.get(0);
        String pageName = page.getName();
        ModelAndView model = new ModelAndView("decorator");
        model.addObject("styles", styles);
        model.addObject("content", computePageContent(page, locale));
        LOGGER.debug("Construction du footer pour la page  {}", pageName);
        model.addObject("footerTemplate", computePageFooter(page, locale));
        LOGGER.debug("Construction du header pour la page  {}", pageName);
        model.addObject("header", computePageHeader(page, locale));
        LOGGER.debug("Construction de la meta pour la page  {}", pageName);
        model.addObject("meta", computePageMeta(page, locale));

        LOGGER.debug("Construction des widgets pour la page {}", pageName);
        List<WidgetPageDTO> widgetPageDTOS = widgetPageService
                .findByPageId(String.valueOf(page.getId()));
        List<String> widgetIds = widgetPageDTOS.stream()
                .map(widgetPageDTO -> widgetPageDTO.getWidgetId())
                .collect(Collectors.toList());
        List<String> widgetAsynchronousNames = widgetIds.stream()
                .map(widgetId -> widgetService.getEntity(Long.parseLong(widgetId)))
                .filter(widget -> widget.isAsynchronous())
                .map(widget -> widget.getName()).collect(Collectors.toList());

        List<WidgetDTO> synchronousWidgets = widgetIds.stream()
                .map(widgetId -> widgetService.getEntity(Long.parseLong(widgetId), locale.getLanguage()))
                .filter(widget -> !widget.isAsynchronous()).collect(Collectors.toList());

        addSynchronousWidgetsTemplatesToModel(model, synchronousWidgets, pageNumber, locale, pageName,
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

        WebsiteDTO websiteDTO = websiteService.getWebsiteByName(websiteName);
        if (websiteDTO == null) {
            return new ModelAndView("404");
        }

        List<SitemapDTO> sitemaps = sitemapService.findByWebsiteId(websiteDTO.getId());
        List<RenderingPageDTO> pages = sitemaps.stream()
                .map(sitemap -> renderingPageService.getEntity(sitemap.getPageId()))
                .filter(page -> page.getHref().equals(pageHref)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pages)) {
            return new ModelAndView("404");
        }

        RenderingPageDTO page = pages.get(0);
        String pageName = page.getName();
        ModelAndView model = new ModelAndView("decorator_amp");
        model.addObject("amp_content", computePageAMPContent(page, locale));
        List<WidgetPageDTO> widgetPageDTOS = widgetPageService
                .findByPageId(String.valueOf(page.getId()));
        List<String> widgetIds = widgetPageDTOS.stream()
                .map(widgetPageDTO -> widgetPageDTO.getWidgetId())
                .collect(Collectors.toList());
        List<WidgetDTO> synchronousWidgets = widgetIds.stream()
                .map(widgetId -> widgetService.getEntity(Long.parseLong(widgetId), locale.getLanguage()))
                .filter(widget -> !widget.isAsynchronous()).collect(Collectors.toList());

        addSynchronousWidgetsTemplatesToModel(model, synchronousWidgets, pageNumber, locale, pageName,
                query);

        LOGGER.debug("Page {} prête", pageName);

        return model;
    }

    private void addSynchronousWidgetsTemplatesToModel(ModelAndView model,
                                                       List<WidgetDTO> synchronousWidgets,
                                                       int pageNumber, Locale locale, String pageName, String query) {
        synchronousWidgets.forEach(widget -> {

            Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale, pageName,
                    query);
            if (!CollectionUtils.isEmpty(widgetModel)) {
                widgetModel.forEach((key, value) -> model.addObject(key, value));
            }

            model.addObject("widget_" + widget.getName(), computeWidgetTemplate(widget, locale));

        });

    }

    String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
        WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor(widget.getType());
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

    Map<String, Object> computeWidgetModel(WidgetDTO widget, int pageNumber, Locale locale,
                                           String pageName,
                                           String query) {

        WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor(widget.getType());
        return widgetProvider.computeWidgetModel(widget, locale, pageName, pageNumber, query);

    }

}
