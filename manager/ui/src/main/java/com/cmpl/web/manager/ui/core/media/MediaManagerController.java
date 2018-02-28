package com.cmpl.web.manager.ui.core.media;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/medias")
public class MediaManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MediaManagerController.class);

  private final MediaService mediaService;
  private final MediaManagerDisplayFactory mediaManagerDisplayFactory;

  public MediaManagerController(MediaService mediaService, MediaManagerDisplayFactory mediaManagerDisplayFactory) {
    this.mediaService = mediaService;
    this.mediaManagerDisplayFactory = mediaManagerDisplayFactory;
  }

  @PostMapping(consumes = "multipart/form-data")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('webmastering:media:create')")
  public void upload(@RequestParam("media") MultipartFile uploadedMedia) {
    if (uploadedMedia.isEmpty()) {
      return;
    }
    try {
      mediaService.upload(uploadedMedia);
    } catch (Exception e) {
      LOGGER.error("Cannot save multipart file !", e);
    }
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public ModelAndView printViewMedias(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.MEDIA_VIEW.name());
    return mediaManagerDisplayFactory.computeModelAndViewForViewAllMedias(locale, pageNumberToUse);
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
    LOGGER.info("Accès à la page " + BACK_PAGE.MEDIA_UPLOAD.name());
    return mediaManagerDisplayFactory.computeModelAndViewForUploadMedia(locale);
  }

  @GetMapping(value = "/_view/{mediaId}")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public ModelAndView printViewMedia(@PathVariable("mediaId") String mediaId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.MEDIA_VISUALIZE.name());
    return mediaManagerDisplayFactory.computeModelAndViewForViewMedia(mediaId, locale);
  }

  @GetMapping("/{mediaId}")
  @PreAuthorize("hasAuthority('webmastering:media:read')")
  public void serve(@PathVariable("mediaId") String mediaId, HttpServletResponse res) throws Exception {
    MediaDTO fileDTO = mediaService.getEntity(Long.valueOf(mediaId));
    if (fileDTO != null) {
      res.setHeader(HttpHeaders.CONTENT_TYPE, fileDTO.getContentType());
      res.setHeader(HttpHeaders.CONTENT_LENGTH, fileDTO.getSize() + "");
      res.setHeader(HttpHeaders.CONTENT_DISPOSITION, "Content-Disposition: inline; filename=\"" + fileDTO.getName()
          + "\"");
      StreamUtils.copy(mediaService.download(fileDTO.getName()), res.getOutputStream());
      return;
    }
    res.setStatus(HttpStatus.NOT_FOUND.value());
  }

}
