package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.news.content.NewsContentDTO;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageService;
import org.springframework.util.StringUtils;

public class RenderingNewsEntryMapper extends BaseMapper<RenderingNewsEntryDTO, NewsEntry> {

  private final NewsContentService newsContentService;

  private final NewsImageService newsImageService;


  public RenderingNewsEntryMapper(NewsContentService newsContentService,
    NewsImageService newsImageService) {
    this.newsContentService = newsContentService;
    this.newsImageService = newsImageService;
  }


  @Override
  public RenderingNewsEntryDTO toDTO(NewsEntry entity) {
    RenderingNewsEntryDTOBuilder builder = RenderingNewsEntryDTOBuilder.create();

    builder.author(entity.getAuthor()).name(entity.getTitle()).title(entity.getTitle())
      .tags(entity.getTags());

    if (StringUtils.hasText(entity.getContentId())) {
      NewsContentDTO content = newsContentService.getEntity(Long.parseLong(entity.getContentId()));
      builder.editorialContent(content.getContent()).editorialImageUrl(content.getLinkUrl())
        .editorialVideoUrl(content.getVideoUrl());
    }

    if (StringUtils.hasText(entity.getImageId())) {
      NewsImageDTO image = newsImageService.getEntity(Long.parseLong(entity.getContentId()));
      builder.alt(image.getAlt()).legend(image.getLegend())
        .uploadedImageSrc(image.getMedia().getSrc());
    }

    builder.id(entity.getId()).creationDate(entity.getCreationDate())
      .creationUser(entity.getCreationUser()).modificationDate(entity.getModificationDate())
      .modificationUser(entity.getModificationUser());

    return builder.build();
  }

  @Override
  public NewsEntry toEntity(RenderingNewsEntryDTO dto) {
    return null;
  }
}
