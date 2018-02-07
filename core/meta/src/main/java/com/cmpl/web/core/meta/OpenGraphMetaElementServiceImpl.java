package com.cmpl.web.core.meta;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cmpl.web.core.common.service.BaseServiceImpl;

/**
 * Service pour les balises meta open graph
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "openGraphMetaElements")
public class OpenGraphMetaElementServiceImpl extends BaseServiceImpl<OpenGraphMetaElementDTO, OpenGraphMetaElement>
    implements OpenGraphMetaElementService {

  private final OpenGraphMetaElementRepository openGraphMetaElementRepository;

  public OpenGraphMetaElementServiceImpl(OpenGraphMetaElementRepository openGraphMetaElementRepository) {
    super(openGraphMetaElementRepository);
    this.openGraphMetaElementRepository = openGraphMetaElementRepository;
  }

  @Override
  @Cacheable(value = "openGraphMetaForPage")
  public List<OpenGraphMetaElementDTO> findOpenGraphMetaElementsByPageId(String pageId) {
    return toListDTO(openGraphMetaElementRepository.findByPageId(pageId));
  }

  @Override
  protected OpenGraphMetaElementDTO toDTO(OpenGraphMetaElement entity) {
    OpenGraphMetaElementDTO dto = new OpenGraphMetaElementDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected OpenGraphMetaElement toEntity(OpenGraphMetaElementDTO dto) {
    OpenGraphMetaElement entity = new OpenGraphMetaElement();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @CacheEvict(value = "openGraphMetaForPage", key = "#a0.pageId")
  public OpenGraphMetaElementDTO createEntity(OpenGraphMetaElementDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  @CacheEvict(value = "openGraphMetaForPage", key = "#a0")
  public void deleteEntityInPage(String pageId, Long openGraphMetaId) {
    deleteEntity(openGraphMetaId);
  }

}
