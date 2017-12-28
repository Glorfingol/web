package com.cmpl.web.news;

/**
 * Implementation du Translator pour les requetes de creation/modification de NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryTranslatorImpl implements NewsEntryTranslator {

  @Override
  public NewsEntryDTO fromRequestToDTO(NewsEntryRequest request) {
    NewsEntryDTOBuilder dtoBuilder = new NewsEntryDTOBuilder().author(request.getAuthor()).tags(request.getTags())
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
    return new NewsContentDTOBuilder().content(contentRequest.getContent())
        .creationDate(contentRequest.getCreationDate()).id(contentRequest.getId())
        .modificationDate(contentRequest.getModificationDate()).build();

  }

  NewsImageDTO fromImageRequestToDTO(NewsImageRequest imageRequest) {
    return new NewsImageDTOBuilder().alt(imageRequest.getAlt()).base64Src(imageRequest.getSrc())
        .legend(imageRequest.getLegend()).creationDate(imageRequest.getCreationDate()).id(imageRequest.getId())
        .modificationDate(imageRequest.getModificationDate()).build();

  }

  @Override
  public NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto) {
    NewsEntryResponse response = new NewsEntryResponse();
    response.setNewsEntry(dto);

    return response;
  }

}
