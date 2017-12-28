package com.cmpl.web.meta;

public class MetaElementTranslatorImpl implements MetaElementTranslator {

  @Override
  public MetaElementDTO fromCreateFormToDTO(String pageId, MetaElementCreateForm form) {
    return new MetaElementDTOBuilder().pageId(pageId).name(form.getName()).content(form.getContent()).build();
  }

  @Override
  public MetaElementResponse fromDTOToResponse(MetaElementDTO dto) {
    return new MetaElementResponseBuilder().metaElement(dto).build();
  }

  @Override
  public OpenGraphMetaElementDTO fromCreateFormToDTO(String pageId, OpenGraphMetaElementCreateForm form) {
    return new OpenGraphMetaElementDTOBuilder().pageId(pageId).property(form.getProperty()).content(form.getContent())
        .build();
  }

  @Override
  public MetaElementResponse fromDTOToResponse(OpenGraphMetaElementDTO dto) {
    return new MetaElementResponseBuilder().openGraphMetaElement(dto).build();
  }

}
