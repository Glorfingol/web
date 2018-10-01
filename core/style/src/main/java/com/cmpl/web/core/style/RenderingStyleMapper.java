package com.cmpl.web.core.style;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.Style;
import java.util.Objects;
import org.springframework.util.StringUtils;

public class RenderingStyleMapper extends BaseMapper<RenderingStyleDTO, Style> {

  private final MediaService mediaService;

  public RenderingStyleMapper(MediaService mediaService) {
    this.mediaService = Objects.requireNonNull(mediaService);
  }

  @Override
  public RenderingStyleDTO toDTO(Style entity) {
    RenderingStyleDTOBuilder builder = RenderingStyleDTOBuilder.create();

    if (!StringUtils.isEmpty(entity.getMediaId())) {
      MediaDTO media = mediaService.getEntity(Long.valueOf(entity.getMediaId()));

      if (media != null) {
        builder.mediaSrc(media.getSrc());
      }
    }

    builder.id(entity.getId()).modificationUser(entity.getModificationUser())
      .modificationDate(entity.getModificationDate()).creationUser(entity.getCreationUser())
      .creationDate(entity.getCreationDate());
    return builder.build();
  }

  @Override
  public Style toEntity(RenderingStyleDTO dto) {
    return null;
  }
}
