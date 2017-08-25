package cmpl.web.news;


/**
 * Implementation du Translator pour les requetes de creation/modification de NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryTranslatorImpl implements NewsEntryTranslator {

  private NewsEntryTranslatorImpl() {

  }

  /**
   * Constructeur static pour la configuration
   * 
   * @return
   */
  public static NewsEntryTranslatorImpl fromVoid() {
    return new NewsEntryTranslatorImpl();
  }

  @Override
  public NewsEntryDTO fromRequestToDTO(NewsEntryRequest request) {
    NewsEntryDTO dto = new NewsEntryDTO();
    dto.setAuthor(request.getAuthor());
    dto.setTags(request.getTags());
    dto.setTitle(request.getTitle());
    dto.setId(request.getId());
    dto.setCreationDate(request.getCreationDate());
    dto.setModificationDate(request.getModificationDate());

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
