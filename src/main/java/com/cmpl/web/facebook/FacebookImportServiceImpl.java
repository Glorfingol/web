package com.cmpl.web.facebook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.util.StringUtils;

import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.news.NewsContentDTO;
import com.cmpl.web.news.NewsContentDTOBuilder;
import com.cmpl.web.news.NewsEntryDTO;
import com.cmpl.web.news.NewsEntryDTOBuilder;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.news.NewsImageDTO;
import com.cmpl.web.news.NewsImageDTOBuilder;

/**
 * Service qui sert a importer des post facebook en tant que NewsEntry
 * 
 * @author Louis
 *
 */
public class FacebookImportServiceImpl implements FacebookImportService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookImportServiceImpl.class);

  private static final String DATA_START = "data:";
  private static final String DATA_END = ";base64,";

  private final NewsEntryService newsEntryService;
  private final Facebook facebookConnector;
  private final WebMessageSource messageSource;

  public FacebookImportServiceImpl(NewsEntryService newsEntryService, Facebook facebookConnector,
      WebMessageSource messageSource) {
    this.newsEntryService = newsEntryService;
    this.facebookConnector = facebookConnector;
    this.messageSource = messageSource;
  }

  @Override
  public List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts, Locale locale) {
    List<NewsEntryDTO> createdEntries = new ArrayList<>();
    facebookPosts.forEach(postToImport -> createdEntries.add(newsEntryService.createEntity(convertPostToNewsEntry(
        postToImport, locale))));
    return createdEntries;
  }

  NewsEntryDTO convertPostToNewsEntry(FacebookImportPost facebookPost, Locale locale) {

    NewsEntryDTOBuilder convertedPostBuilder = NewsEntryDTOBuilder.create().author(facebookPost.getAuthor())
        .facebookId(facebookPost.getFacebookId()).tags(computeTags(locale)).title(computeTitle(locale));

    if (hasContent(facebookPost)) {
      NewsContentDTO content = computeNewsContentFromPost(facebookPost);
      convertedPostBuilder.newsContent(content);
    }

    if (hasImage(facebookPost)) {
      NewsImageDTO image = computeNewsImageFromPost(facebookPost, locale);
      convertedPostBuilder.newsImage(image);
    }

    return convertedPostBuilder.build();
  }

  NewsImageDTO computeNewsImageFromPost(FacebookImportPost facebookPost, Locale locale) {
    return NewsImageDTOBuilder.create().alt(computeAlt(facebookPost, locale)).legend(computeLegend(locale))
        .base64Src(getFacebookImageBase64Src(facebookPost)).build();
  }

  NewsContentDTO computeNewsContentFromPost(FacebookImportPost facebookPost) {
    return NewsContentDTOBuilder.create().content(facebookPost.getDescription()).linkUrl(facebookPost.getLinkUrl())
        .videoUrl(facebookPost.getVideoUrl()).build();
  }

  String computeLegend(Locale locale) {
    return messageSource.getI18n("facebook.image.legend", locale);
  }

  String computeAlt(FacebookImportPost facebookPost, Locale locale) {
    return messageSource.getI18n("facebook.image.alt", locale) + facebookPost.getFacebookId();
  }

  boolean hasContent(FacebookImportPost facebookPost) {
    return StringUtils.hasText(facebookPost.getDescription());
  }

  boolean hasImage(FacebookImportPost facebookPost) {
    return StringUtils.hasText(facebookPost.getPhotoUrl());
  }

  String getFacebookImageBase64Src(FacebookImportPost facebookPost) {

    StringBuilder base64SrcBuilder = new StringBuilder();

    byte[] data = recoverImageBytes(facebookPost);
    if (data.length == 0) {
      return "";
    }
    base64SrcBuilder.append(DATA_START);

    String contentType = computeContentTypeFromBytes(facebookPost, data);
    if (!StringUtils.hasText(contentType)) {
      return "";
    }

    base64SrcBuilder.append(contentType);
    base64SrcBuilder.append(DATA_END);
    base64SrcBuilder.append(Base64.encodeBase64String(data));
    return base64SrcBuilder.toString();
  }

  String computeTags(Locale locale) {
    return messageSource.getI18n("facebook.news.tag", locale);
  }

  String computeTitle(Locale locale) {
    return messageSource.getI18n("facebook.news.title", locale);
  }

  byte[] recoverImageBytes(FacebookImportPost facebookPost) {
    return getMediaOperations().getAlbumImage(facebookPost.getObjectId(), ImageType.NORMAL);
  }

  MediaOperations getMediaOperations() {
    return facebookConnector.mediaOperations();
  }

  String computeContentTypeFromBytes(FacebookImportPost facebookPost, byte[] data) {
    String contentType = "";
    try {
      contentType = URLConnection.guessContentTypeFromStream(prepareInputStream(data));
    } catch (IOException e) {
      LOGGER.warn("Impossible de determiner le type de l'image pour l'album ", facebookPost.getObjectId(), e);
    }
    return contentType;
  }

  ByteArrayInputStream prepareInputStream(byte[] data) {
    return new ByteArrayInputStream(data);
  }

}
