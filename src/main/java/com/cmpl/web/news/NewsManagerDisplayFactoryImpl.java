package com.cmpl.web.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

/**
 * Implementation de l'interface pour la factory des pages d'actualite sur le back
 * 
 * @author Louis
 *
 */
public class NewsManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<NewsEntryDisplayBean> implements
    NewsManagerDisplayFactory {

  private static final String NEWS_TEMPLATE_FILE_NAME = "news_entry.html";
  private static final String RESOURCE_NEWS_TEMPLATE_FILE_NAME = "templates/pages/actualites/news_entry.html";

  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;
  private final FileService fileService;

  public NewsManagerDisplayFactoryImpl(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService, FileService fileService) {
    super(menuFactory, messageSource);
    this.newsEntryService = newsEntryService;
    this.contextHolder = contextHolder;
    this.fileService = fileService;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(Locale locale, int pageNumber) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, locale);
    LOGGER.info("Construction des entrées de blog pour la page " + BACK_PAGE.NEWS_VIEW.name());

    PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = computePageWrapper(locale, pageNumber);

    newsManager.addObject("wrappedNews", pagedNewsWrapped);

    return newsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPageCreateNews(Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_CREATE, locale);
    LOGGER.info("Construction du formulaire d'entrées de blog pour la page " + BACK_PAGE.NEWS_CREATE.name());
    newsManager.addObject("newsFormBean", computeNewsRequestForCreateForm());

    return newsManager;
  }

  @Override
  protected Page<NewsEntryDisplayBean> computeEntries(Locale locale, int pageNumber) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    pagedNewsEntries.getContent().forEach(
        newsEntryFromDB -> newsEntries.add(computeNewsEntryDisplayBean(locale, newsEntryFromDB)));
    return new PageImpl<>(newsEntries, pageRequest, pagedNewsEntries.getTotalElements());
  }

  List<NewsEntryDisplayBean> computeNewsEntryDisplayBeans(Locale locale) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    List<NewsEntryDTO> newsEntriesFromDB = newsEntryService.getEntities();
    if (CollectionUtils.isEmpty(newsEntriesFromDB)) {
      return newsEntries;
    }
    newsEntriesFromDB.forEach(newsEntryFromDB -> newsEntries.add(computeNewsEntryDisplayBean(locale, newsEntryFromDB)));
    return newsEntries;
  }

  NewsEntryDisplayBean computeNewsEntryDisplayBean(Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);
    String labelAccroche = getI18nValue("news.entry.call", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, contextHolder.getImageDisplaySrc(), labelPar, labelLe,
        contextHolder.getDateFormat(), labelAccroche, "");
  }

  @Override
  public ModelAndView computeModelAndViewForOneNewsEntry(Locale locale, String newsEntryId) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_UPDATE, locale);
    newsManager.addObject("newsFormBean", computeNewsRequestForEditForm(newsEntryId));

    return newsManager;
  }

  NewsEntryRequest computeNewsRequestForCreateForm() {
    return new NewsEntryRequestBuilder().content(new NewsContentRequestBuilder().build())
        .image(new NewsImageRequestBuilder().build()).build();
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
    return new NewsEntryRequestBuilder().author(dto.getAuthor()).tags(dto.getTags()).title(dto.getTitle())
        .content(computeNewsContentRequest(dto)).image(computeNewsImageRequest(dto)).id(dto.getId())
        .creationDate(dto.getCreationDate()).modificationDate(dto.getModificationDate()).build();
  }

  NewsImageRequest computeNewsImageRequest(NewsEntryDTO dto) {
    return new NewsImageRequestBuilder().alt(dto.getNewsImage().getAlt()).id(dto.getNewsImage().getId())
        .creationDate(dto.getNewsImage().getCreationDate()).modificationDate(dto.getNewsImage().getModificationDate())
        .legend(dto.getNewsImage().getLegend()).src(computeImageSrc(dto)).build();
  }

  String computeImageSrc(NewsEntryDTO dto) {
    String src = dto.getNewsImage().getSrc();
    if (StringUtils.hasText(src)) {
      return contextHolder.getImageDisplaySrc() + src;
    }
    return null;
  }

  NewsContentRequest computeNewsContentRequest(NewsEntryDTO dto) {
    return new NewsContentRequestBuilder().content(dto.getNewsContent().getContent()).id(dto.getNewsContent().getId())
        .creationDate(dto.getNewsContent().getCreationDate())
        .modificationDate(dto.getNewsContent().getModificationDate()).build();
  }

  @Override
  public ModelAndView computeModelAndViewForNewsTemplate(Locale locale) {
    ModelAndView newsTemplateManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_TEMPLATE, locale);
    String templateBody = fileService.readFileContentFromSystem(NEWS_TEMPLATE_FILE_NAME);
    if (templateBody == null) {
      templateBody = fileService.readDefaultTemplateContent(RESOURCE_NEWS_TEMPLATE_FILE_NAME);
    }
    NewsTemplate template = new NewsTemplateBuilder().body(templateBody).build();
    newsTemplateManager.addObject("newsTemplate", template);
    return newsTemplateManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/news";
  }
}
