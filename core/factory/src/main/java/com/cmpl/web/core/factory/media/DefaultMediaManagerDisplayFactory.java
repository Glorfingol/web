package com.cmpl.web.core.factory.media;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class DefaultMediaManagerDisplayFactory extends AbstractBackDisplayFactory<MediaDTO>
  implements MediaManagerDisplayFactory {

  private final MediaService mediaService;

  public DefaultMediaManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
    MediaService mediaService, ContextHolder contextHolder,
    PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService, PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService, backPagesRegistry, contextHolder);
    this.mediaService = Objects.requireNonNull(mediaService);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("MEDIA_VIEW");
    ModelAndView pagesManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des medias pour la page {}", backPage.getPageName());

    PageWrapper<MediaDTO> pagedMediaDTOWrapped = computePageWrapper(locale, pageNumber, "");

    pagesManager.addObject("wrappedEntities", pagedMediaDTOWrapped);

    return pagesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForViewMedia(String mediaId, Locale locale) {
    BackPage backPage = computeBackPage("MEDIA_UPDATE");
    ModelAndView mediaManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction de la page de visualisation d'un media ");
    MediaDTO media = mediaService.getEntity(Long.parseLong(mediaId));
    mediaManager.addObject("updateForm", media);
    return mediaManager;
  }

  @Override
  public ModelAndView computeModelAndViewForViewMediaMain(String mediaId) {
    ModelAndView mediaManager = new ModelAndView("back/medias/edit/tab_main");
    LOGGER.info("Construction de la page de visualisation d'un media ");
    MediaDTO media = mediaService.getEntity(Long.parseLong(mediaId));
    mediaManager.addObject("mediaBean", media);
    return mediaManager;
  }

  @Override
  protected Page<MediaDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<MediaDTO> mediaEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<MediaDTO> pagedMediaDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedMediaDTOEntries = mediaService.searchEntities(pageRequest, query);
    } else {
      pagedMediaDTOEntries = mediaService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedMediaDTOEntries.getContent())) {
      return new PageImpl<>(mediaEntries);
    }

    mediaEntries.addAll(pagedMediaDTOEntries.getContent());

    return new PageImpl<>(mediaEntries, pageRequest, pagedMediaDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUploadMedia(Locale locale) {
    BackPage backPage = computeBackPage("MEDIA_CREATE");
    ModelAndView mediaManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire d'upload de media ");
    return mediaManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/medias";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:media:create";
  }

  @Override
  protected String getItemLink() {
    return "/manager/medias/";
  }

  @Override
  protected String getCreateItemLink() {
    return getBaseUrl() + "/_upload";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/medias/search";
  }


  @Override
  protected String getSearchPlaceHolder() {
    return "search.medias.placeHolder";
  }
}
