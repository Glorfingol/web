package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.CarouselFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.news.display.NewsEntryDisplayBean;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.page.PAGE;
import cmpl.web.service.NewsEntryService;

public class NewsDisplayFactoryImpl extends DisplayFactoryImpl implements NewsDisplayFactory {

  private NewsEntryService newsEntryService;
  private static final String DAY_MONTH_YEAR_FORMAT = "dd/MM/yy";

  private NewsDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource,
      NewsEntryService newsEntryService) {
    super(menuFactory, footerFactory, metaElementFactory, carouselFactory, messageSource);
    this.newsEntryService = newsEntryService;
  }

  public static NewsDisplayFactoryImpl fromFactoriesAndMessageResourceAndServices(MenuFactory menuFactory,
      FooterFactory footerFactory, MetaElementFactory metaElementFactory, CarouselFactory carouselFactory,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    return new NewsDisplayFactoryImpl(menuFactory, footerFactory, metaElementFactory, carouselFactory, messageSource,
        newsEntryService);
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGE page, Locale locale) {

    ModelAndView newsModelAndView = super.computeModelAndViewForPage(page, locale);
    if (PAGE.NEWS.equals(page)) {
      LOGGER.info("Construction des entrées de blog pour la page " + page.name());
      newsModelAndView.addObject("newsEntries", computeNewsEntries(locale));
    }

    return newsModelAndView;

  }

  List<NewsEntryDisplayBean> computeNewsEntries(Locale locale) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    List<NewsEntryDTO> newsEntriesFromDB = newsEntryService.getEntities();
    if (CollectionUtils.isEmpty(newsEntriesFromDB)) {
      return newsEntries;
    }

    for (NewsEntryDTO newsEntryFromDB : newsEntriesFromDB) {
      newsEntries.add(computeNewsEntryDisplayBean(locale, newsEntryFromDB));
    }

    return newsEntries;
  }

  @Override
  public ModelAndView computeModelAndViewForNewsEntry(Locale locale, String newsEntryId) {
    ModelAndView newsModelAndView = super.computeModelAndViewForPage(PAGE.NEWS_ENTRY, locale);
    LOGGER.info("Construction de l'entrée de blog pour la page " + PAGE.NEWS_ENTRY.name());
    newsModelAndView.addObject("newsEntry", computeNewsEntry(locale, newsEntryId));

    return newsModelAndView;
  }

  NewsEntryDisplayBean computeNewsEntry(Locale locale, String newsEntryId) {

    NewsEntryDTO newsEntryFromDB = newsEntryService.getEntity(Long.valueOf(newsEntryId));
    return computeNewsEntryDisplayBean(locale, newsEntryFromDB);
  }

  NewsEntryDisplayBean computeNewsEntryDisplayBean(Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);
    String labelAccroche = getI18nValue("news.entry.call", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, labelPar, labelLe, DAY_MONTH_YEAR_FORMAT, labelAccroche);
  }
}
