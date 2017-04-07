package cmpl.web.translator.impl;

import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.model.news.rest.news.NewsContentRequest;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.model.news.rest.news.NewsImageRequest;
import cmpl.web.translator.NewsEntryTranslator;

public class NewsEntryTranslatorImpl implements NewsEntryTranslator {

  private NewsEntryTranslatorImpl() {

  }

  public static NewsEntryTranslatorImpl fromVoid() {
    return new NewsEntryTranslatorImpl();
  }

  @Override
  public NewsEntryDTO fromRequestToDTO(NewsEntryRequest request) {
    NewsEntryDTO dto = new NewsEntryDTO();
    dto.setAuthor(request.getAuthor());
    dto.setTags(request.getTags());
    dto.setTitle(request.getTitle());

    NewsContentRequest contentRequest = request.getContent();
    if (contentRequest != null) {
      dto.setNewsContent(fromContentRequestToDTO(contentRequest));
    }

    NewsImageRequest imageRequest = request.getImage();
    if (imageRequest != null) {
      dto.setNewsImage(fromImageRequestToDTO(imageRequest));
    }
    return dto;
  }

  NewsContentDTO fromContentRequestToDTO(NewsContentRequest contentRequest) {
    NewsContentDTO dto = new NewsContentDTO();
    dto.setContent(contentRequest.getContent());
    dto.setCreationDate(contentRequest.getCreationDate());
    dto.setId(contentRequest.getId());
    dto.setModificationDate(contentRequest.getModificationDate());

    return dto;

  }

  NewsImageDTO fromImageRequestToDTO(NewsImageRequest imageRequest) {
    NewsImageDTO dto = new NewsImageDTO();

    dto.setAlt(imageRequest.getAlt());
    dto.setBase64Src(imageRequest.getSrc());
    dto.setLegend(imageRequest.getLegend());
    dto.setCreationDate(imageRequest.getCreationDate());
    dto.setId(imageRequest.getId());
    dto.setModificationDate(imageRequest.getModificationDate());

    return dto;

  }

  @Override
  public NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto) {
    NewsEntryResponse response = new NewsEntryResponse();
    response.setNewsEntry(dto);

    return response;
  }

}
