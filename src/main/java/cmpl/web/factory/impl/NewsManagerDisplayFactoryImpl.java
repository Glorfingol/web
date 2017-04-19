package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
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

  protected NewsManagerDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, MetaElementFactory metaElementFactory) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
    this.newsEntryService = newsEntryService;
  }

  public static NewsManagerDisplayFactoryImpl fromFactoriesAndMessageResource(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, MetaElementFactory metaElementFactory) {
    return new NewsManagerDisplayFactoryImpl(menuFactory, footerFactory, messageSource, newsEntryService,
        metaElementFactory);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des entrées de blog pour la page " + backPage.name());
    newsManager.addObject("newsEntries", computeNewsEntries(locale));
    LOGGER.info("Construction du formulaire d'entrées de blog pour la page " + backPage.name());
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
      String labelPar = getI18nValue("news.entry.by", locale);
      String labelLe = getI18nValue("news.entry.the", locale);
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

    formBean.setTitleLabel(getI18nValue("title.label", locale));
    formBean.setTitleHelp(getI18nValue("title.help", locale));

    formBean.setAuthorLabel(getI18nValue("author.label", locale));
    formBean.setAuthorHelp(getI18nValue("author.help", locale));

    formBean.setTagsLabel(getI18nValue("tags.label", locale));
    formBean.setTagsHelp(getI18nValue("tags.help", locale));

    formBean.setContentLabel(getI18nValue("content.label", locale));
    formBean.setContentHelp(getI18nValue("content.help", locale));

    formBean.setImageLabel(getI18nValue("image.label", locale));
    formBean.setImageHelp(getI18nValue("image.help", locale));

    formBean.setLegendLabel(getI18nValue("legend.label", locale));
    formBean.setLegendHelp(getI18nValue("legend.help", locale));

    formBean.setAltLabel(getI18nValue("alt.label", locale));
    formBean.setAltHelp(getI18nValue("alt.help", locale));

    return formBean;
  }
}
