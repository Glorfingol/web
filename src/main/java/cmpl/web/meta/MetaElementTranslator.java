package cmpl.web.meta;

public interface MetaElementTranslator {

  MetaElementDTO fromCreateFormToDTO(String pageId, MetaElementCreateForm form);

  MetaElementResponse fromDTOToResponse(MetaElementDTO dto);

  OpenGraphMetaElementDTO fromCreateFormToDTO(String pageId, OpenGraphMetaElementCreateForm form);

  MetaElementResponse fromDTOToResponse(OpenGraphMetaElementDTO dto);
}
