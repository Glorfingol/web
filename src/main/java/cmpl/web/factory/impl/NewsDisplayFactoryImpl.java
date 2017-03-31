package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;

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
  public ModelAndView computeModelAndViewForPage(PAGE page, String languageCode) {

    ModelAndView newsModelAndView = super.computeModelAndViewForPage(page, languageCode);
    newsModelAndView.addObject("newsEntries", computeNewsEntries());

    return newsModelAndView;

  }

  List<NewsEntryDisplayBean> computeNewsEntries() {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<NewsEntryDisplayBean>();

    List<NewsEntryDTO> newsEntriesFromDB = newsEntryService.getRecentNews();
    if (CollectionUtils.isEmpty(newsEntriesFromDB)) {
      return newsEntries;
    }

    for (NewsEntryDTO newsEntryFromDB : newsEntriesFromDB) {
      NewsEntryDisplayBean newsEntryDisplayBean = new NewsEntryDisplayBean(newsEntryFromDB);
      newsEntries.add(newsEntryDisplayBean);
    }

    return newsEntries;
  }
}
