package com.cmpl.web.core.meta;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;

/**
 * Service pour les balises meta
 * 
 * @author Louis
 *
 */
public class MetaElementServiceImpl extends BaseServiceImpl<MetaElementDTO, MetaElement> implements MetaElementService {

  private final MetaElementRepository metaElementRepository;

  public MetaElementServiceImpl(MetaElementRepository metaElementRepository) {
    super(metaElementRepository);
    this.metaElementRepository = metaElementRepository;
  }

  @Override
  public List<MetaElementDTO> findMetaElementsByPageId(String pageId) {
    return toListDTO(metaElementRepository.findByPageId(pageId));
  }

  @Override
  protected MetaElementDTO toDTO(MetaElement entity) {
    MetaElementDTO dto = new MetaElementDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected MetaElement toEntity(MetaElementDTO dto) {
    MetaElement entity = new MetaElement();
    fillObject(dto, entity);
    return entity;
  }

}
