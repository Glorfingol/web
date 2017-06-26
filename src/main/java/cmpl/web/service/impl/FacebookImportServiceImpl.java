package cmpl.web.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.util.StringUtils;

import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.service.FacebookImportService;
import cmpl.web.service.NewsEntryService;

public class FacebookImportServiceImpl implements FacebookImportService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookImportServiceImpl.class);

  private final NewsEntryService newsEntryService;
  private final Facebook facebookConnector;

  private FacebookImportServiceImpl(NewsEntryService newsEntryService, Facebook facebookConnector) {
    this.newsEntryService = newsEntryService;
    this.facebookConnector = facebookConnector;
  }

  public static FacebookImportServiceImpl fromService(NewsEntryService newsEntryService, Facebook facebookConnector) {
    return new FacebookImportServiceImpl(newsEntryService, facebookConnector);
  }

  @Override
  public List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts) {
    List<NewsEntryDTO> createdEntries = new ArrayList<>();
    for (FacebookImportPost postToImport : facebookPosts) {
      NewsEntryDTO createdPost = newsEntryService.createEntity(convertPostToNewsEntry(postToImport));
      createdEntries.add(createdPost);
    }
    return createdEntries;
  }

  NewsEntryDTO convertPostToNewsEntry(FacebookImportPost facebookPost) {
    NewsEntryDTO convertedPost = new NewsEntryDTO();

    convertedPost.setAuthor(facebookPost.getAuthor());
    convertedPost.setFacebookId(facebookPost.getFacebookId());
    convertedPost.setTags("facebook");
    convertedPost.setTitle(facebookPost.getTitle());

    if (hasContent(facebookPost)) {
      NewsContentDTO content = new NewsContentDTO();
      content.setContent(facebookPost.getDescription());
      convertedPost.setNewsContent(content);
    }

    if (hasImage(facebookPost)) {
      NewsImageDTO image = new NewsImageDTO();
      image.setAlt("Image from facebook");
      image.setLegend("Image from facebook");
      image.setBase64Src(getFacebookImageBase64Src(facebookPost));
      convertedPost.setNewsImage(image);
    }

    return convertedPost;
  }

  boolean hasContent(FacebookImportPost facebookPost) {
    return !StringUtils.isEmpty(facebookPost.getDescription());
  }

  boolean hasImage(FacebookImportPost facebookPost) {
    return !StringUtils.isEmpty(facebookPost.getPhotoUrl());
  }

  String getFacebookImageBase64Src(FacebookImportPost facebookPost) {

    StringBuilder base64SrcBuilder = new StringBuilder();

    byte[] data = recoverImageBytes(facebookPost);
    if (data.length == 0) {
      return "";
    }
    base64SrcBuilder.append("data:");

    String contentType = computeContentTypeFromBytes(facebookPost, data);
    if (StringUtils.isEmpty(contentType)) {
      return "";
    }

    base64SrcBuilder.append(contentType);
    base64SrcBuilder.append(";base64,");
    base64SrcBuilder.append(Base64.encodeBase64String(data));
    return base64SrcBuilder.toString();
  }

  private byte[] recoverImageBytes(FacebookImportPost facebookPost) {
    byte[] data = null;
    try {
      data = facebookConnector.mediaOperations().getAlbumImage(facebookPost.getObjectId(), ImageType.NORMAL);
    } catch (Exception e) {
      LOGGER.warn("Impossible de retrouver l'image sur facebook pour l'objet ", facebookPost.getObjectId(), e);
      return new byte[]{};
    }
    return data;
  }

  private String computeContentTypeFromBytes(FacebookImportPost facebookPost, byte[] data) {
    String contentType = "";
    try {
      contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
    } catch (IOException e) {
      LOGGER.warn("Impossible de determiner le type de l'image pour l'album ", facebookPost.getObjectId(), e);
    }
    return contentType;
  }

}
