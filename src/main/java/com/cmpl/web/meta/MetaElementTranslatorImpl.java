package com.cmpl.web.meta;

public class MetaElementTranslatorImpl implements MetaElementTranslator {

  @Override
  public MetaElementDTO fromCreateFormToDTO(String pageId, MetaElementCreateForm form) {
    return MetaElementDTOBuilder.create().pageId(pageId).name(form.getName()).content(form.getContent()).build();
  }

  @Override
  public MetaElementResponse fromDTOToResponse(MetaElementDTO dto) {
    return MetaElementResponseBuilder.create().metaElement(dto).build();
  }

  @Override
  public OpenGraphMetaElementDTO fromCreateFormToDTO(String pageId, OpenGraphMetaElementCreateForm form) {
    return OpenGraphMetaElementDTOBuilder.create().pageId(pageId).property(form.getProperty())
        .content(form.getContent()).build();
  }

  @Override
  public MetaElementResponse fromDTOToResponse(OpenGraphMetaElementDTO dto) {
    return MetaElementResponseBuilder.create().openGraphMetaElement(dto).build();
  }

}
