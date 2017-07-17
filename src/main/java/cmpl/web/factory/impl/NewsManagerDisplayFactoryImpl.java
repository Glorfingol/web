package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.context.ContextHolder;
import cmpl.web.model.news.display.NewsEntryDisplayBean;
import cmpl.web.model.news.display.NewsFormDisplayBean;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.model.news.rest.news.NewsContentRequest;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsImageRequest;
import cmpl.web.model.page.BACK_PAGE;
import cmpl.web.service.NewsEntryService;

/**
 * Implementation de l'interface pour la factory des pages d'actualite sur le back
 * 
 * @author Louis
 *
 */
public class NewsManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements NewsManagerDisplayFactory {

  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;

  protected NewsManagerDisplayFactoryImpl(ContextHolder contextHolder, MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, NewsEntryService newsEntryService,
      MetaElementFactory metaElementFactory) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
    this.newsEntryService = newsEntryService;
    this.contextHolder = contextHolder;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param contextHolder
   * @param menuFactory
   * @param footerFactory
   * @param messageSource
   * @param newsEntryService
   * @param metaElementFactory
   * @return
   */
  public static NewsManagerDisplayFactoryImpl fromFactoriesAndMessageResource(ContextHolder contextHolder,
      MenuFactory menuFactory, FooterFactory footerFactory, WebMessageSourceImpl messageSource,
      NewsEntryService newsEntryService, MetaElementFactory metaElementFactory) {
    return new NewsManagerDisplayFactoryImpl(contextHolder, menuFactory, footerFactory, messageSource,
        newsEntryService, metaElementFactory);
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des entrées de blog pour la page " + backPage.name());
    newsManager.addObject("newsEntries", computeNewsEntryDisplayBeans(locale));
    LOGGER.info("Construction du formulaire d'entrées de blog pour la page " + backPage.name());
    newsManager.addObject("newsFormLabels", computeForm(locale));

    newsManager.addObject("newsFormBean", computeNewsRequestForCreateForm());

    return newsManager;
  }

  List<NewsEntryDisplayBean> computeNewsEntryDisplayBeans(Locale locale) {
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

  NewsEntryDisplayBean computeNewsEntryDisplayBean(Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);
    String labelAccroche = getI18nValue("news.entry.call", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, contextHolder.getImageDisplaySrc(), labelPar, labelLe,
        contextHolder.getDateFormat(), labelAccroche);
  }

  @Override
  public ModelAndView computeModelAndViewForOneNewsEntry(BACK_PAGE backPage, Locale locale, String newsEntryId) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(backPage, locale);
    newsManager.addObject("newsFormBean", computeNewsRequestForEditForm(newsEntryId));
    newsManager.addObject("newsFormLabels", computeForm(locale));

    return newsManager;
  }

  NewsEntryRequest computeNewsRequestForCreateForm() {
    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest contentRequest = new NewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequest();

    request.setContent(contentRequest);
    request.setImage(imageRequest);

    return request;
  }

  NewsEntryRequest computeNewsRequestForEditForm(String newsEntryId) {

    NewsEntryDTO dto = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    if (dto.getNewsImage() == null) {
      dto.setNewsImage(new NewsImageDTO());
    }
    if (dto.getNewsContent() == null) {
      dto.setNewsContent(new NewsContentDTO());
    }

    return computeNewsEntryRequest(dto);

  }

  NewsEntryRequest computeNewsEntryRequest(NewsEntryDTO dto) {
    NewsEntryRequest request = new NewsEntryRequest();
    request.setAuthor(dto.getAuthor());
    request.setId(dto.getId());
    request.setCreationDate(dto.getCreationDate());
    request.setModificationDate(dto.getModificationDate());
    request.setTags(dto.getTags());
    request.setTitle(dto.getTitle());

    NewsContentRequest contentRequest = computeNewsContentRequest(dto);
    request.setContent(contentRequest);

    NewsImageRequest imageRequest = computeNewsImageRequest(dto);
    request.setImage(imageRequest);
    return request;
  }

  NewsImageRequest computeNewsImageRequest(NewsEntryDTO dto) {
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(dto.getNewsImage().getAlt());
    imageRequest.setId(dto.getNewsImage().getId());
    imageRequest.setCreationDate(dto.getNewsImage().getCreationDate());
    imageRequest.setModificationDate(dto.getNewsImage().getModificationDate());
    imageRequest.setLegend(dto.getNewsImage().getLegend());
    imageRequest.setSrc(computeImageSrc(dto));
    return imageRequest;
  }

  String computeImageSrc(NewsEntryDTO dto) {
    String src = dto.getNewsImage().getSrc();
    if (StringUtils.hasText(src)) {
      return contextHolder.getImageDisplaySrc() + src;
    }
    return null;
  }

  NewsContentRequest computeNewsContentRequest(NewsEntryDTO dto) {
    NewsContentRequest contentRequest = new NewsContentRequest();
    contentRequest.setContent(dto.getNewsContent().getContent());
    contentRequest.setId(dto.getNewsContent().getId());
    contentRequest.setCreationDate(dto.getNewsContent().getCreationDate());
    contentRequest.setModificationDate(dto.getNewsContent().getModificationDate());
    return contentRequest;
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
    formBean.setImageDropLabel(getI18nValue("image.drop.label", locale));

    formBean.setLegendLabel(getI18nValue("legend.label", locale));
    formBean.setLegendHelp(getI18nValue("legend.help", locale));

    formBean.setAltLabel(getI18nValue("alt.label", locale));
    formBean.setAltHelp(getI18nValue("alt.help", locale));

    return formBean;
  }
}
