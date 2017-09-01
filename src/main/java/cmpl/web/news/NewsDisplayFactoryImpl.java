package cmpl.web.news;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.carousel.CarouselFactory;
import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.factory.DisplayFactoryImpl;
import cmpl.web.core.model.PageWrapper;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.page.PAGES;
import cmpl.web.page.PageService;

/**
 * Implementation de l'interface pour la factory des pages d'actualite sur le front
 * 
 * @author Louis
 *
 */
public class NewsDisplayFactoryImpl extends DisplayFactoryImpl implements NewsDisplayFactory {

  private NewsDisplayFactoryImpl(ContextHolder contextHolder, MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource,
      NewsEntryService newsEntryService, PageService pageService) {
    super(menuFactory, footerFactory, metaElementFactory, carouselFactory, messageSource, pageService,
        newsEntryService, contextHolder);
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param contextHolder
   * @param menuFactory
   * @param footerFactory
   * @param metaElementFactory
   * @param carouselFactory
   * @param messageSource
   * @param newsEntryService
   * @return
   */
  public static NewsDisplayFactoryImpl fromFactoriesAndMessageResourceAndServices(ContextHolder contextHolder,
      MenuFactory menuFactory, FooterFactory footerFactory, MetaElementFactory metaElementFactory,
      CarouselFactory carouselFactory, WebMessageSourceImpl messageSource, NewsEntryService newsEntryService,
      PageService pageService) {
    return new NewsDisplayFactoryImpl(contextHolder, menuFactory, footerFactory, metaElementFactory, carouselFactory,
        messageSource, newsEntryService, pageService);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGES page, Locale locale) {

    ModelAndView newsModelAndView = super.computeModelAndViewForPage(page, locale);
    if (PAGES.NEWS.equals(page)) {
      LOGGER.info("Construction des entrées de blog pour la page " + page.name());
      newsModelAndView.addObject("newsEntries", computeNewsEntries(locale));
    }

    return newsModelAndView;

  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGES page, Locale locale, int pageNumber) {
    ModelAndView newsModelAndView = super.computeModelAndViewForPage(page, locale);
    if (PAGES.NEWS.equals(page)) {
      LOGGER.info("Construction des entrées de blog pour la page " + page.name());
      PageWrapper pagedNewsWrapped = computePageWrapperOfNews(null, locale, pageNumber);

      newsModelAndView.addObject("wrappedNews", pagedNewsWrapped);
      newsModelAndView.addObject("emptyMessage", getI18nValue("actualites.empty", locale));
    }

    return newsModelAndView;
  }

  @Override
  public ModelAndView computeModelAndViewForNewsEntry(Locale locale, String newsEntryId) {
    ModelAndView newsModelAndView = super.computeModelAndViewForPage(PAGES.NEWS_ENTRY, locale);
    LOGGER.info("Surcharge des meta elements pour la page de l'entree " + newsEntryId);
    newsModelAndView.addObject("metaItems", computeMetaElementsForNewsEntry(locale, PAGES.NEWS_ENTRY, newsEntryId));
    LOGGER.info("Surcharge des open graph meta elements pour la page de l'entree " + newsEntryId);
    newsModelAndView.addObject("openGraphMetaItems",
        computeOpenGraphMetaElementsForNewsEntry(locale, PAGES.NEWS_ENTRY, newsEntryId));
    LOGGER.info("Construction de l'entrée de blog pour la page " + PAGES.NEWS_ENTRY.name());
    newsModelAndView.addObject("newsEntry", computeNewsEntry(locale, newsEntryId));

    return newsModelAndView;
  }

}
