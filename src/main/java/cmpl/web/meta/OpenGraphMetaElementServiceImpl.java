package cmpl.web.meta;

import java.util.List;

import cmpl.web.core.service.BaseServiceImpl;

/**
 * Service pour les balises meta open graph
 * 
 * @author Louis
 *
 */
public class OpenGraphMetaElementServiceImpl extends BaseServiceImpl<OpenGraphMetaElementDTO, OpenGraphMetaElement>
    implements OpenGraphMetaElementService {

  private final OpenGraphMetaElementRepository openGraphMetaElementRepository;

  public OpenGraphMetaElementServiceImpl(OpenGraphMetaElementRepository openGraphMetaElementRepository) {
    super(openGraphMetaElementRepository);
    this.openGraphMetaElementRepository = openGraphMetaElementRepository;
  }

  @Override
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

}
