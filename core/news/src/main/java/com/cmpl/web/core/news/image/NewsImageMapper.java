package com.cmpl.web.core.news.image;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;

public class NewsImageMapper extends BaseMapper<NewsImageDTO, NewsImage> {

  private final MediaService mediaService;

  public NewsImageMapper(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @Override
  public NewsImageDTO toDTO(NewsImage entity) {
    NewsImageDTO dto = new NewsImageDTO();
    fillObject(entity, dto);

    if (StringUtils.hasText(entity.getMediaId())) {
      MediaDTO media = mediaService.getEntity(Long.parseLong(entity.getMediaId()));
      dto.setMedia(media);
    }

    return dto;
  }

  @Override
  public NewsImage toEntity(NewsImageDTO dto) {
    NewsImage entity = new NewsImage();
    fillObject(dto, entity);
    String mediaId = null;
    if (dto.getMedia() != null) {
      mediaId = String.valueOf(dto.getMedia().getId());
    }
    entity.setMediaId(mediaId);

    return entity;

  }
}