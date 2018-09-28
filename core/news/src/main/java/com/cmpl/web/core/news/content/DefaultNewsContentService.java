package com.cmpl.web.core.news.content;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.NewsContent;
import java.util.Objects;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 *
 * @author Louis
 */
public class DefaultNewsContentService extends
  DefaultBaseService<NewsContentDTO, NewsContent> implements NewsContentService {


  private final FileService fileService;

  private static final String NEWS = "news_";

  private static final String HTML_SUFFIX = ".html";


  public DefaultNewsContentService(NewsContentDAO newsContentDAO,
    NewsContentMapper newsContentMapper, FileService fileService) {
    super(newsContentDAO, newsContentMapper);
    this.fileService = Objects.requireNonNull(fileService);
  }

  @Override
  public NewsContentDTO createEntity(NewsContentDTO newsToCreate) {
    NewsContentDTO createdEntity = super.createEntity(newsToCreate);

    fileService.saveFileOnSystem(NEWS + createdEntity.getId() + HTML_SUFFIX,
      newsToCreate.getContent());

    return createdEntity;
  }

  @Override
  public NewsContentDTO updateEntity(NewsContentDTO newsToUpdate) {
    NewsContentDTO updatedEntity = super.updateEntity(newsToUpdate);

    fileService.saveFileOnSystem(NEWS + updatedEntity.getId() + HTML_SUFFIX,
      newsToUpdate.getContent());

    return updatedEntity;
  }


  @Override
  public NewsContentDTO getEntity(Long id) {
    NewsContentDTO newsContent = super.getEntity(id);
    newsContent
      .setContent(fileService.readFileContentFromSystem(NEWS + newsContent.getId() + HTML_SUFFIX));
    return newsContent;
  }


  @Override
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
    fileService.removeFileFromSystem(NEWS + id + HTML_SUFFIX);
  }


}
