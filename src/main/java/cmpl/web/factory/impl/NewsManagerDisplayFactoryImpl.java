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
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.model.news.display.NewsEditBean;
import cmpl.web.model.news.display.NewsEntryDisplayBean;
import cmpl.web.model.news.display.NewsFormDisplayBean;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.model.page.BACK_PAGE;
import cmpl.web.service.NewsEntryService;

public class NewsManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements NewsManagerDisplayFactory {

  private NewsEntryService newsEntryService;
  private static final String DAY_MONTH_YEAR_FORMAT = "dd/MM/yy";

  protected NewsManagerDisplayFactoryImpl(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MessageSource messageSource, NewsEntryService newsEntryService, MetaElementBuilder metaElementBuilder) {
    super(menuBuilder, footerBuilder, messageSource, metaElementBuilder);
    this.newsEntryService = newsEntryService;
  }

  public static NewsManagerDisplayFactoryImpl fromBuilders(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MessageSource messageSource, NewsEntryService newsEntryService, MetaElementBuilder metaElementBuilder) {
    return new NewsManagerDisplayFactoryImpl(menuBuilder, footerBuilder, messageSource, newsEntryService,
        metaElementBuilder);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(backPage, locale);
    newsManager.addObject("newsEntries", computeNewsEntries(locale));
    newsManager.addObject("newsFormLabels", computeForm(locale));

    return newsManager;
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

  @Override
  public ModelAndView computeModelAndViewForOneNewsEntry(BACK_PAGE backPage, Locale locale, String newsEntryId) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(backPage, locale);
    newsManager.addObject("newsEditBean", computeNewsEntry(newsEntryId));
    newsManager.addObject("newsFormLabels", computeForm(locale));

    return newsManager;
  }

  NewsEditBean computeNewsEntry(String newsEntryId) {

    NewsEntryDTO dto = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    if (dto.getNewsImage() == null) {
      dto.setNewsImage(new NewsImageDTO());
    }
    if (dto.getNewsContent() == null) {
      dto.setNewsContent(new NewsContentDTO());
    }
    return new NewsEditBean(dto);

  }

  NewsFormDisplayBean computeForm(Locale locale) {
    NewsFormDisplayBean formBean = new NewsFormDisplayBean();

    formBean.setTitleLabel(computeI18nLabel("title.label", locale));
    formBean.setTitleHelp(computeI18nLabel("title.help", locale));

    formBean.setAuthorLabel(computeI18nLabel("author.label", locale));
    formBean.setAuthorHelp(computeI18nLabel("author.help", locale));

    formBean.setTagsLabel(computeI18nLabel("tags.label", locale));
    formBean.setTagsHelp(computeI18nLabel("tags.help", locale));

    formBean.setContentLabel(computeI18nLabel("content.label", locale));
    formBean.setContentHelp(computeI18nLabel("content.help", locale));

    formBean.setImageLabel(computeI18nLabel("image.label", locale));
    formBean.setImageHelp(computeI18nLabel("image.help", locale));

    formBean.setLegendLabel(computeI18nLabel("legend.label", locale));
    formBean.setLegendHelp(computeI18nLabel("legend.help", locale));

    formBean.setAltLabel(computeI18nLabel("alt.label", locale));
    formBean.setAltHelp(computeI18nLabel("alt.help", locale));

    return formBean;
  }
}
