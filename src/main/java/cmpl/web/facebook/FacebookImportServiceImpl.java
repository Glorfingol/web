package cmpl.web.facebook;

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

import cmpl.web.message.WebMessageSource;
import cmpl.web.news.NewsContentDTO;
import cmpl.web.news.NewsEntryDTO;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsImageDTO;

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

  private FacebookImportServiceImpl(NewsEntryService newsEntryService, Facebook facebookConnector,
      WebMessageSource messageSource) {
    this.newsEntryService = newsEntryService;
    this.facebookConnector = facebookConnector;
    this.messageSource = messageSource;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param newsEntryService
   * @param facebookConnector
   * @param messageSource
   * @return
   */
  public static FacebookImportServiceImpl fromService(NewsEntryService newsEntryService, Facebook facebookConnector,
      WebMessageSource messageSource) {
    return new FacebookImportServiceImpl(newsEntryService, facebookConnector, messageSource);
  }

  @Override
  public List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts, Locale locale) {
    List<NewsEntryDTO> createdEntries = new ArrayList<>();
    for (FacebookImportPost postToImport : facebookPosts) {
      NewsEntryDTO createdPost = newsEntryService.createEntity(convertPostToNewsEntry(postToImport, locale));
      createdEntries.add(createdPost);
    }
    return createdEntries;
  }

  NewsEntryDTO convertPostToNewsEntry(FacebookImportPost facebookPost, Locale locale) {
    NewsEntryDTO convertedPost = new NewsEntryDTO();

    convertedPost.setAuthor(facebookPost.getAuthor());
    convertedPost.setFacebookId(facebookPost.getFacebookId());
    convertedPost.setTags(computeTags(locale));
    convertedPost.setTitle(computeTitle(locale));

    if (hasContent(facebookPost)) {
      NewsContentDTO content = computeNewsContentFromPost(facebookPost);
      convertedPost.setNewsContent(content);
    }

    if (hasImage(facebookPost)) {
      NewsImageDTO image = computeNewsImageFromPost(facebookPost, locale);
      convertedPost.setNewsImage(image);
    }

    return convertedPost;
  }

  NewsImageDTO computeNewsImageFromPost(FacebookImportPost facebookPost, Locale locale) {
    NewsImageDTO image = new NewsImageDTO();
    image.setAlt(computeAlt(facebookPost, locale));
    image.setLegend(computeLegend(locale));
    image.setBase64Src(getFacebookImageBase64Src(facebookPost));
    return image;
  }

  NewsContentDTO computeNewsContentFromPost(FacebookImportPost facebookPost) {
    NewsContentDTO content = new NewsContentDTO();
    content.setContent(facebookPost.getDescription());
    content.setLinkUrl(facebookPost.getLinkUrl());
    content.setVideoUrl(facebookPost.getVideoUrl());
    return content;
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
