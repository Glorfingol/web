package com.cmpl.web.core.page;

import java.util.List;
import java.util.Objects;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Page;

/**
 * Service des pages
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "pages")
public class PageServiceImpl extends BaseServiceImpl<PageDTO, Page> implements PageService {

  private static final String HTML_SUFFIX = ".html";
  private static final String FOOTER_SUFFIX = "_footer";
  private static final String META_SUFFIX = "_meta";
  private static final String AMP_SUFFIX = "_amp";
  private static final String HEADER_SUFFIX = "_header";
  private static final String LOCALE_CODE_PREFIX = "_";

  private final PageDAO pageDAO;
  private final FileService fileService;

  public PageServiceImpl(PageDAO pageDAO, PageMapper pageMapper, FileService fileService) {
    super(pageDAO, pageMapper);
    this.pageDAO = Objects.requireNonNull(pageDAO);

    this.fileService = Objects.requireNonNull(fileService);

  }

  @Override
  @Transactional
  @CacheEvict(value = {"pagedPages", "listedPages"}, allEntries = true)
  public PageDTO createEntity(PageDTO dto, String localeCode) {
    PageDTO createdPage = super.createEntity(dto);

    fileService.saveFileOnSystem(dto.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX, dto.getBody());
    fileService.saveFileOnSystem(dto.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getFooter());
    fileService.saveFileOnSystem(dto.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getHeader());
    fileService.saveFileOnSystem(dto.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getMeta());
    fileService.saveFileOnSystem(dto.getName() + AMP_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getAmp());
    return createdPage;

  }

  @Override
  @Transactional
  @CachePut(key = "#a0.id+'_'+#a1")
  public PageDTO updateEntity(PageDTO dto, String localeCode) {
    PageDTO updatedPage = super.updateEntity(dto);

    fileService.saveFileOnSystem(dto.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX, dto.getBody());
    fileService.saveFileOnSystem(dto.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getFooter());
    fileService.saveFileOnSystem(dto.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getHeader());
    fileService.saveFileOnSystem(dto.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getMeta());
    fileService.saveFileOnSystem(dto.getName() + AMP_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getAmp());

    updatedPage.setHeader(dto.getHeader());
    updatedPage.setFooter(dto.getFooter());
    updatedPage.setBody(dto.getBody());
    updatedPage.setMeta(dto.getMeta());
    updatedPage.setAmp(dto.getAmp());

    return updatedPage;
  }

  @Override
  @Cacheable(key = "#a0+'_'+#a1")
  public PageDTO getEntity(Long pageId, String localeCode) {
    PageDTO fetchedPage = super.getEntity(pageId);
    fetchedPage.setBody(
        fileService.readFileContentFromSystem(fetchedPage.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    fetchedPage.setFooter(fileService.readFileContentFromSystem(
        fetchedPage.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    fetchedPage.setHeader(fileService.readFileContentFromSystem(
        fetchedPage.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    fetchedPage.setMeta(fileService.readFileContentFromSystem(
        fetchedPage.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    fetchedPage.setAmp(fileService
        .readFileContentFromSystem(fetchedPage.getName() + AMP_SUFFIX + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    return fetchedPage;
  }

  @Override
  @Cacheable(value = "pagedPages")
  public org.springframework.data.domain.Page<PageDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  @Cacheable(key = "#a0")
  public PageDTO getPageByName(String pageName, String localeCode) {
    Page page = pageDAO.getPageByName(pageName);
    if (page == null) {
      return PageDTOBuilder.create().build();
    }
    return mapper.toDTO(page);
  }

  @Override
  @Cacheable(value = "listedPages")
  public List<PageDTO> getPages() {
    return mapper.toListDTO(pageDAO.getPages(new Sort(Direction.ASC, "name")));
  }

  @Override
  @CacheEvict(value = {"pagedPages", "listedPages"}, allEntries = true)
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
