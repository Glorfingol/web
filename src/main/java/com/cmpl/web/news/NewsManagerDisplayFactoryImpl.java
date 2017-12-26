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
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.page.BACK_PAGE;

/**
 * Implementation de l'interface pour la factory des pages d'actualite sur le back
 * 
 * @author Louis
 *
 */
public class NewsManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements NewsManagerDisplayFactory {

  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;

  public NewsManagerDisplayFactoryImpl(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService, MetaElementFactory metaElementFactory) {
    super(menuFactory, messageSource, metaElementFactory);
    this.newsEntryService = newsEntryService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(Locale locale, int pageNumber) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, locale);
    LOGGER.info("Construction des entrées de blog pour la page " + BACK_PAGE.NEWS_VIEW.name());

    PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = computePageWrapperOfNews(locale, pageNumber);

    newsManager.addObject("wrappedNews", pagedNewsWrapped);

    return newsManager;
  }

  PageWrapper<NewsEntryDisplayBean> computePageWrapperOfNews(Locale locale, int pageNumber) {
    Page<NewsEntryDisplayBean> pagedNewsEntries = computeNewsEntries(locale, pageNumber);

    boolean isFirstPage = pagedNewsEntries.isFirst();
    boolean isLastPage = pagedNewsEntries.isLast();
    int totalPages = pagedNewsEntries.getTotalPages();
    int currentPageNumber = pagedNewsEntries.getNumber();

    PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = new PageWrapper<>();
    pagedNewsWrapped.setCurrentPageNumber(currentPageNumber);
    pagedNewsWrapped.setFirstPage(isFirstPage);
    pagedNewsWrapped.setLastPage(isLastPage);
    pagedNewsWrapped.setPage(pagedNewsEntries);
    pagedNewsWrapped.setTotalPages(totalPages);
    pagedNewsWrapped.setPageBaseUrl("/manager/news");
    pagedNewsWrapped.setPageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages));
    return pagedNewsWrapped;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPageCreateNews(Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_CREATE, locale);
    LOGGER.info("Construction du formulaire d'entrées de blog pour la page " + BACK_PAGE.NEWS_CREATE.name());
    newsManager.addObject("newsFormBean", computeNewsRequestForCreateForm());

    return newsManager;
  }

  Page<NewsEntryDisplayBean> computeNewsEntries(Locale locale, int pageNumber) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    for (NewsEntryDTO newsEntryFromDB : pagedNewsEntries.getContent()) {
      newsEntries.add(computeNewsEntryDisplayBean(locale, newsEntryFromDB));
    }

    return new PageImpl<>(newsEntries, pageRequest, pagedNewsEntries.getTotalElements());
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
        contextHolder.getDateFormat(), labelAccroche, "");
  }

  @Override
  public ModelAndView computeModelAndViewForOneNewsEntry(Locale locale, String newsEntryId) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_UPDATE, locale);
    newsManager.addObject("newsFormBean", computeNewsRequestForEditForm(newsEntryId));

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

}
