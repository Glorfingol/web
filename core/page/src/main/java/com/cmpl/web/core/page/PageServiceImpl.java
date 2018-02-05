package com.cmpl.web.core.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.meta.MetaElementDTO;
import com.cmpl.web.core.meta.MetaElementService;
import com.cmpl.web.core.meta.OpenGraphMetaElementDTO;
import com.cmpl.web.core.meta.OpenGraphMetaElementService;

/**
 * Service des pages
 * 
 * @author Louis
 *
 */
public class PageServiceImpl extends BaseServiceImpl<PageDTO, Page> implements PageService {

  private static final String BODY_SUFFIX = ".html";
  private static final String FOOTER_SUFFIX = "_footer.html";
  private static final String HEADER_SUFFIX = "_header.html";

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

  @Override
  @Transactional
  public PageDTO createEntity(PageDTO dto) {
    PageDTO createdPage = super.createEntity(dto);

    fileService.saveFileOnSystem(dto.getName() + BODY_SUFFIX, dto.getBody());
    fileService.saveFileOnSystem(dto.getName() + FOOTER_SUFFIX, dto.getFooter());
    fileService.saveFileOnSystem(dto.getName() + HEADER_SUFFIX, dto.getHeader());
    return createdPage;

  }

  @Override
  @Transactional
  public PageDTO updateEntity(PageDTO dto) {
    PageDTO updatedPage = super.updateEntity(dto);

    fileService.saveFileOnSystem(dto.getName() + BODY_SUFFIX, dto.getBody());
    fileService.saveFileOnSystem(dto.getName() + FOOTER_SUFFIX, dto.getFooter());
    fileService.saveFileOnSystem(dto.getName() + HEADER_SUFFIX, dto.getHeader());
    return updatedPage;
  }

  @Override
  public PageDTO getEntity(Long pageId) {
    PageDTO fetchedPage = super.getEntity(pageId);
    fetchedPage.setBody(fileService.readFileContentFromSystem(fetchedPage.getName() + BODY_SUFFIX));
    fetchedPage.setFooter(fileService.readFileContentFromSystem(fetchedPage.getName() + FOOTER_SUFFIX));
    fetchedPage.setHeader(fileService.readFileContentFromSystem(fetchedPage.getName() + HEADER_SUFFIX));
    return fetchedPage;
  }

  @Override
  protected PageDTO toDTO(Page entity) {
    PageDTO dto = PageDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Page toEntity(PageDTO dto) {
    Page entity = PageBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public PageDTO getPageByName(String pageName) {
    Page page = pageRepository.findByName(pageName);
    if (page == null) {
      return PageDTOBuilder.create().build();
    }
    return computePageDTOToReturn(page);
  }

  @Override
  public List<PageDTO> getPages() {
    return toListDTO(pageRepository.findAll(new Sort(Direction.ASC, "name")));
  }

  @Override
  public List<PageDTO> toListDTO(List<Page> entities) {
    List<PageDTO> pages = new ArrayList<>();
    entities.forEach(entity -> pages.add(computePageDTOToReturn(entity)));
    return pages;
  }

  PageDTO computePageDTOToReturn(Page page) {
    PageDTO pageDTO = toDTO(page);

    List<MetaElementDTO> metaElements = metaElementService.findMetaElementsByPageId(String.valueOf(page.getId()));
    pageDTO.setMetaElements(metaElements);

    List<OpenGraphMetaElementDTO> openGraphMetaElements = openGraphMetaElementService
        .findOpenGraphMetaElementsByPageId(String.valueOf(page.getId()));
    pageDTO.setOpenGraphMetaElements(openGraphMetaElements);

    return pageDTO;
  }

}
