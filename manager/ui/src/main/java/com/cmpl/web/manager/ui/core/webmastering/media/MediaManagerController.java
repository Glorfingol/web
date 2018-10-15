package com.cmpl.web.manager.ui.core.webmastering.media;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@ManagerController
@RequestMapping(value = "/manager/medias")
public class MediaManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MediaManagerController.class);

  private final MediaService mediaService;

  private final MediaManagerDisplayFactory mediaManagerDisplayFactory;

  private final NotificationCenter notificationCenter;

  private final WebMessageSource messageSource;

  private final ContextHolder contextHolder;

  public MediaManagerController(MediaService mediaService,
    MediaManagerDisplayFactory mediaManagerDisplayFactory,
    NotificationCenter notificationCenter, WebMessageSource messageSource,
    ContextHolder contextHolder) {

    this.mediaService = Objects.requireNonNull(mediaService);
    this.mediaManagerDisplayFactory = Objects.requireNonNull(mediaManagerDisplayFactory);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
    this.contextHolder = Objects.requireNonNull(contextHolder);
  }

  @PostMapping(consumes = "multipart/form-data")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('webmastering:media:create')")
  public void upload(@RequestParam("media") MultipartFile uploadedMedia, Locale locale) {
    if (uploadedMedia.isEmpty()) {
      return;
    }
    try {
      mediaService.upload(uploadedMedia);
      notificationCenter
        .sendNotification("success", messageSource.getMessage("create.success", locale));
    } catch (Exception e) {
      LOGGER.error("Cannot save multipart file !", e);
      notificationCenter
        .sendNotification("danger", messageSource.getMessage("create.error", locale));
    }
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public ModelAndView printViewMedias(
    @RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    return mediaManagerDisplayFactory.computeModelAndViewForViewAllMedias(locale, pageNumberToUse);
  }

  @GetMapping(value = "/search")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public ModelAndView printSearchMedias(
    @RequestParam(name = "p", required = false) Integer pageNumber,
    @RequestParam(name = "q") String query, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    return mediaManagerDisplayFactory
      .computeModelAndViewForAllEntitiesTab(locale, pageNumberToUse, query);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_upload")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public ModelAndView printUploadMedia(Locale locale) {
    return mediaManagerDisplayFactory.computeModelAndViewForUploadMedia(locale);
  }

  @GetMapping(value = "/{mediaId}")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public ModelAndView printViewMedia(@PathVariable("mediaId") String mediaId, Locale locale) {
    return mediaManagerDisplayFactory.computeModelAndViewForViewMedia(mediaId, locale);
  }

  @GetMapping("/download/{mediaId}")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public void serve(@PathVariable("mediaId") String mediaId, HttpServletResponse res)
    throws Exception {
    MediaDTO fileDTO = mediaService.getEntity(Long.valueOf(mediaId));
    if (fileDTO != null) {
      res.setHeader(HttpHeaders.CONTENT_TYPE, fileDTO.getContentType());
      res.setHeader(HttpHeaders.CONTENT_LENGTH, fileDTO.getSize() + "");
      res.setHeader(HttpHeaders.CONTENT_DISPOSITION,
        "Content-Disposition: inline; filename=\"" + fileDTO.getName() + "\"");
      StreamUtils.copy(mediaService.download(fileDTO.getName()), res.getOutputStream());
      return;
    }
    res.setStatus(HttpStatus.NOT_FOUND.value());
  }

  @DeleteMapping(value = "/{mediaId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:media:delete')")
  public void deleteMedia(@PathVariable(value = "mediaId") String mediaId, Locale locale) {

    LOGGER.info("Tentative de suppression d'un media");
    try {
      mediaService.deleteEntity(Long.parseLong(mediaId));
      notificationCenter
        .sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Media " + mediaId + " supprimé");

    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du media " + mediaId, e);
      notificationCenter
        .sendNotification("danger", messageSource.getMessage("delete.error", locale));

    }

  }

  @GetMapping(value = "/{mediaId}/_main")
  @PreAuthorize("hasAuthority('webmastering:news:read')")
  public ModelAndView printViewMediaMain(@PathVariable(value = "mediaId") String mediaId) {
    return mediaManagerDisplayFactory.computeModelAndViewForViewMediaMain(mediaId);
  }

  @GetMapping(value = "/{mediaId}/_memberships")
  @PreAuthorize("hasAuthority('webmastering:news:read')")
  public ModelAndView printUpdateMediaMembership(@PathVariable(value = "mediaId") String mediaId) {
    return mediaManagerDisplayFactory.computeModelAndViewForMembership(mediaId);
  }

  @GetMapping(value = "/ajaxSearch")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  @ResponseBody
  public ResponseEntity<Page<MediaDTO>> searchMediasForSelect(
    @RequestParam(name = "page", required = false) Integer pageNumber,
    @RequestParam(name = "q", required = false) String query) {
    if (pageNumber == null) {
      pageNumber = 0;
    }
    if (!StringUtils.hasText(query)) {
      query = "";
    }
    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    return new ResponseEntity<>(mediaService.searchEntities(pageRequest, query), HttpStatus.OK);
  }
}
