package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.model.news.display.NewsEntryDisplayBean;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.page.PAGE;
import cmpl.web.service.NewsEntryService;

public class NewsDisplayFactoryImpl extends DisplayFactoryImpl implements NewsDisplayFactory {

  private NewsEntryService newsEntryService;
  private static final String DAY_MONTH_YEAR_FORMAT = "dd/MM/yy";

  private NewsDisplayFactoryImpl(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MetaElementBuilder metaElementBuilder, MessageSource messageSource, NewsEntryService newsEntryService) {
    super(menuBuilder, footerBuilder, metaElementBuilder, messageSource);
    this.newsEntryService = newsEntryService;
  }

  public static NewsDisplayFactoryImpl fromBuildersAndServices(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MetaElementBuilder metaElementBuilder, MessageSource messageSource, NewsEntryService newsEntryService) {
    return new NewsDisplayFactoryImpl(menuBuilder, footerBuilder, metaElementBuilder, messageSource, newsEntryService);
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
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<NewsEntryDisplayBean>();

    List<NewsEntryDTO> newsEntriesFromDB = newsEntryService.getEntities();
    if (CollectionUtils.isEmpty(newsEntriesFromDB)) {
      return newsEntries;
    }

    for (NewsEntryDTO newsEntryFromDB : newsEntriesFromDB) {
      String labelPar = computeI18nLabel("news.entry.by", locale);
      String labelLe = computeI18nLabel("news.entry.the", locale);
      NewsEntryDisplayBean newsEntryDisplayBean = new NewsEntryDisplayBean(newsEntryFromDB, labelPar, labelLe,
          DAY_MONTH_YEAR_FORMAT);
      newsEntries.add(newsEntryDisplayBean);
    }

    return newsEntries;
  }
}
