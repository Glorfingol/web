package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.news.content.NewsContentRequest;
import com.cmpl.web.core.news.image.NewsImageRequest;
import java.io.IOException;
import java.util.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Dispatcher pour le controller des NewsEntry
 *
 * @author Louis
 */
public interface NewsEntryDispatcher {

  /**
   * Permet la creation d'une NewsEntry a partir d'une requete
   */
  NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, Locale locale)
    throws BaseException;

  /**
   * Permet la modificaiton d'une NewsEntry a partir d'une requete
   */
  NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId,
    Locale locale);

  NewsEntryResponse updateContent(NewsContentRequest newsContentRequest, String newsEntryId,
    Locale locale);

  NewsEntryResponse updateImage(NewsImageRequest newsImageRequest, String newsEntryId,
    Locale locale);

  /**
   * Permet le suppression d'une NewsEntry a partir d'une requete
   */
  NewsEntryResponse deleteEntity(String newsEntryId, Locale locale);

  void saveNewsMedia(String newsEntryId, MultipartFile uploadedMedia) throws IOException;

  Page<NewsEntryDTO> searchEntities(PageRequest pageRequest, String query);

}
