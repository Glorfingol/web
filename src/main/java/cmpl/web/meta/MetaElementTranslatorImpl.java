package cmpl.web.meta;

public class MetaElementTranslatorImpl implements MetaElementTranslator {

  @Override
  public MetaElementDTO fromCreateFormToDTO(String pageId, MetaElementCreateForm form) {
    MetaElementDTO dto = new MetaElementDTO();
    dto.setPageId(pageId);
    dto.setName(form.getName());
    dto.setContent(form.getContent());
    return dto;
  }

  @Override
  public MetaElementResponse fromDTOToResponse(MetaElementDTO dto) {
    MetaElementResponse response = new MetaElementResponse();
    response.setMetaElement(dto);
    return response;
  }

  @Override
  public OpenGraphMetaElementDTO fromCreateFormToDTO(String pageId, OpenGraphMetaElementCreateForm form) {
    OpenGraphMetaElementDTO dto = new OpenGraphMetaElementDTO();
    dto.setPageId(pageId);
    dto.setProperty(form.getProperty());
    dto.setContent(form.getContent());
    return dto;
  }

  @Override
  public MetaElementResponse fromDTOToResponse(OpenGraphMetaElementDTO dto) {
    MetaElementResponse response = new MetaElementResponse();
    response.setOpenGraphMetaElement(dto);
    return response;
  }

}
