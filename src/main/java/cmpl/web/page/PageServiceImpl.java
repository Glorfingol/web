package cmpl.web.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cmpl.web.core.service.BaseServiceImpl;
import cmpl.web.file.FileService;
import cmpl.web.meta.MetaElementDTO;
import cmpl.web.meta.MetaElementService;
import cmpl.web.meta.OpenGraphMetaElementDTO;
import cmpl.web.meta.OpenGraphMetaElementService;

/**
 * Service des pages
 * 
 * @author Louis
 *
 */
public class PageServiceImpl extends BaseServiceImpl<PageDTO, Page> implements PageService {

  private final PageRepository pageRepository;
  private final MetaElementService metaElementService;
  private final OpenGraphMetaElementService openGraphMetaElementService;
  private final FileService fileService;

  public PageServiceImpl(PageRepository pageRepository, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, FileService fileService) {
    super(pageRepository);
    this.pageRepository = pageRepository;
    this.metaElementService = metaElementService;
    this.openGraphMetaElementService = openGraphMetaElementService;
    this.fileService = fileService;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param pageRepository
   * @param metaElementService
   * @param openGraphMetaElementService
   * @return
   */
  public static PageServiceImpl fromRepository(PageRepository pageRepository, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, FileService fileService) {
    return new PageServiceImpl(pageRepository, metaElementService, openGraphMetaElementService, fileService);
  }

  @Override
  @Transactional
  public PageDTO createEntity(PageDTO dto) {
    PageDTO createdPage = super.createEntity(dto);

    fileService.saveFileOnSystem("pages/" + dto.getName(), dto.getBody());
    return createdPage;

  }

  @Override
  protected PageDTO toDTO(Page entity) {
    PageDTO dto = new PageDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Page toEntity(PageDTO dto) {
    Page entity = new Page();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public PageDTO getPageByName(String pageName) {
    Page page = pageRepository.findByName(pageName);
    if (page == null) {
      return new PageDTO();
    }
    return computePageDTOToReturn(page);
  }

  @Override
  public List<PageDTO> getPages() {
    return toListDTO(pageRepository.findAll());
  }

  @Override
  public List<PageDTO> toListDTO(List<Page> entities) {
    List<PageDTO> pages = new ArrayList<>();
    for (Page page : entities) {
      pages.add(computePageDTOToReturn(page));
    }
    return pages;
  }

  PageDTO computePageDTOToReturn(Page page) {
    PageDTO pageDTO = toDTO(page);

    List<MetaElementDTO> metaElements = metaElementService.findMetaElementsByPageId(String.valueOf(page.getId()));
    List<OpenGraphMetaElementDTO> openGraphMetaElements = openGraphMetaElementService
        .findOpenGraphMetaElementsByPageId(String.valueOf(page.getId()));

    pageDTO.setMetaElements(metaElements);
    pageDTO.setOpenGraphMetaElements(openGraphMetaElements);

    return pageDTO;
  }

}
