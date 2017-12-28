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
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

public class MediaManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<MediaDTO> implements
    MediaManagerDisplayFactory {

  private final MediaService mediaService;
  private final ContextHolder contextHolder;

  protected MediaManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      MediaService mediaService, ContextHolder contextHolder) {
    super(menuFactory, messageSource);
    this.mediaService = mediaService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber) {
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(BACK_PAGE.MEDIA_VIEW, locale);
    LOGGER.info("Construction des medias pour la page " + BACK_PAGE.MEDIA_VIEW.name());

    PageWrapper<MediaDTO> pagedMediaDTOWrapped = computePageWrapper(locale, pageNumber);

    pagesManager.addObject("wrappedMedias", pagedMediaDTOWrapped);

    return pagesManager;
  }

  @Override
  protected Page<MediaDTO> computeEntries(Locale locale, int pageNumber) {
    List<MediaDTO> mediaEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
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

  @Override
  protected String getBaseUrl() {
    return "/manager/medias";
  }

}
