package com.cmpl.web.media;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.page.BACK_PAGE;

public class MediaManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements MediaManagerDisplayFactory {

  private final MediaService mediaService;
  private final ContextHolder contextHolder;

  protected MediaManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      MetaElementFactory metaElementFactory, MediaService mediaService, ContextHolder contextHolder) {
    super(menuFactory, messageSource, metaElementFactory);
    this.mediaService = mediaService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber) {
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(BACK_PAGE.MEDIA_VIEW, locale);
    LOGGER.info("Construction des medias pour la page " + BACK_PAGE.MEDIA_VIEW.name());

    PageWrapper<MediaDTO> pagedMediaDTOWrapped = computePageWrapperOfMedias(locale, pageNumber);

    pagesManager.addObject("wrappedMedias", pagedMediaDTOWrapped);

    return pagesManager;
  }

  PageWrapper<MediaDTO> computePageWrapperOfMedias(Locale locale, int pageNumber) {
    Page<MediaDTO> pagedMediaDTOEntries = computeMediasEntries(pageNumber);

    boolean isFirstPage = pagedMediaDTOEntries.isFirst();
    boolean isLastPage = pagedMediaDTOEntries.isLast();
    int totalPages = pagedMediaDTOEntries.getTotalPages();
    int currentPageNumber = pagedMediaDTOEntries.getNumber();

    PageWrapper<MediaDTO> pagedMediaDTOWrapped = new PageWrapper<>();
    pagedMediaDTOWrapped.setCurrentPageNumber(currentPageNumber);
    pagedMediaDTOWrapped.setFirstPage(isFirstPage);
    pagedMediaDTOWrapped.setLastPage(isLastPage);
    pagedMediaDTOWrapped.setPage(pagedMediaDTOEntries);
    pagedMediaDTOWrapped.setTotalPages(totalPages);
    pagedMediaDTOWrapped.setPageBaseUrl("/manager/medias");
    pagedMediaDTOWrapped.setPageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages));
    return pagedMediaDTOWrapped;
  }

  Page<MediaDTO> computeMediasEntries(int pageNumber) {
    List<MediaDTO> mediaEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<MediaDTO> pagedMediaDTOEntries = mediaService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedMediaDTOEntries.getContent())) {
      return new PageImpl<>(mediaEntries);
    }

    mediaEntries.addAll(pagedMediaDTOEntries.getContent());

    return new PageImpl<>(mediaEntries, pageRequest, pagedMediaDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUploadMedia(Locale locale) {
    ModelAndView mediaManager = super.computeModelAndViewForBackPage(BACK_PAGE.MEDIA_UPLOAD, locale);
    LOGGER.info("Construction du formulaire d'upload de media ");
    return mediaManager;
  }

}
