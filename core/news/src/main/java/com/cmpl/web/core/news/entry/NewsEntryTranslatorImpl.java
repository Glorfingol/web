package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.news.content.NewsContentDTO;
import com.cmpl.web.core.news.content.NewsContentDTOBuilder;
import com.cmpl.web.core.news.content.NewsContentRequest;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageDTOBuilder;
import com.cmpl.web.core.news.image.NewsImageRequest;

/**
 * Implementation du Translator pour les requetes de creation/modification de NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryTranslatorImpl implements NewsEntryTranslator {

  @Override
  public NewsEntryDTO fromRequestToDTO(NewsEntryRequest request) {
    NewsEntryDTOBuilder dtoBuilder = NewsEntryDTOBuilder.create().author(request.getAuthor()).tags(request.getTags())
        .title(request.getTitle());

    NewsContentRequest contentRequest = request.getContent();
    if (contentRequest != null) {
      dtoBuilder.newsContent(fromContentRequestToDTO(contentRequest));
    }

    NewsImageRequest imageRequest = request.getImage();
    if (imageRequest != null) {
      dtoBuilder.newsImage(fromImageRequestToDTO(imageRequest));
    }
    dtoBuilder.id(request.getId()).creationDate(request.getCreationDate())
        .modificationDate(request.getModificationDate());
    return dtoBuilder.build();
  }

  NewsContentDTO fromContentRequestToDTO(NewsContentRequest contentRequest) {
    return NewsContentDTOBuilder.create().content(contentRequest.getContent())
        .creationDate(contentRequest.getCreationDate()).id(contentRequest.getId())
        .modificationDate(contentRequest.getModificationDate()).build();

  }

  NewsImageDTO fromImageRequestToDTO(NewsImageRequest imageRequest) {
    return NewsImageDTOBuilder.create().alt(imageRequest.getAlt()).legend(imageRequest.getLegend())
        .creationDate(imageRequest.getCreationDate()).id(imageRequest.getId())
        .modificationDate(imageRequest.getModificationDate()).build();

  }

  @Override
  public NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto) {
    return NewsEntryResponseBuilder.create().newsEntry(dto).createdEntityId(String.valueOf(dto.getId())).build();

  }

}
