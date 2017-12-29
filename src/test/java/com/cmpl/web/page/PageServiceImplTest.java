package com.cmpl.web.page;

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import com.cmpl.web.file.FileService;
import com.cmpl.web.meta.MetaElementDTO;
import com.cmpl.web.meta.MetaElementDTOBuilder;
import com.cmpl.web.meta.MetaElementService;
import com.cmpl.web.meta.OpenGraphMetaElementDTO;
import com.cmpl.web.meta.OpenGraphMetaElementDTOBuilder;
import com.cmpl.web.meta.OpenGraphMetaElementService;

@RunWith(MockitoJUnitRunner.class)
public class PageServiceImplTest {

  @Mock
  private PageRepository pageRepository;
  @Mock
  private MetaElementService metaElementService;
  @Mock
  private OpenGraphMetaElementService openGraphMetaElementService;
  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private PageServiceImpl pageService;

  @Test
  public void testCreateEntity() throws Exception {
    PageDTO dtoToCreate = new PageDTOBuilder().body("someBody").footer("someFooter").header("someHeader")
        .name("someName").build();
    Page entityToCreate = new PageBuilder().build();

    BDDMockito.doReturn(dtoToCreate).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.doReturn(entityToCreate).when(pageService).toEntity(BDDMockito.any(PageDTO.class));
    BDDMockito.given(pageRepository.save(BDDMockito.any(Page.class))).willReturn(entityToCreate);
    BDDMockito.doNothing().when(fileService).saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

    pageService.createEntity(dtoToCreate);

    BDDMockito.verify(fileService, BDDMockito.times(3))
        .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

  }

  @Test
  public void testUpdateEntity() throws Exception {

    PageDTO dtoToUpdate = new PageDTOBuilder().body("someBody").footer("someFooter").header("someHeader")
        .name("someName").build();
    Page entityToUpdate = new PageBuilder().build();

    BDDMockito.doReturn(dtoToUpdate).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.doReturn(entityToUpdate).when(pageService).toEntity(BDDMockito.any(PageDTO.class));
    BDDMockito.given(pageRepository.save(BDDMockito.any(Page.class))).willReturn(entityToUpdate);
    BDDMockito.doNothing().when(fileService).saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

    pageService.updateEntity(dtoToUpdate);

    BDDMockito.verify(fileService, BDDMockito.times(3))
        .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());
  }

  @Test
  public void testGetEntity() throws Exception {

    PageDTO dtoToFind = new PageDTOBuilder().name("someName").build();
    Optional<Page> entityToFind = Optional.of(new PageBuilder().build());
    BDDMockito.doReturn(dtoToFind).when(pageService).toDTO(BDDMockito.any(Page.class));
    BDDMockito.given(pageRepository.findById(BDDMockito.anyLong())).willReturn(entityToFind);

    String content = "someContent";
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn(content);

    PageDTO result = pageService.getEntity(123456789l);
    Assert.assertEquals(content, result.getBody());
    Assert.assertEquals(content, result.getHeader());
    Assert.assertEquals(content, result.getFooter());

    BDDMockito.verify(fileService, BDDMockito.times(3)).readFileContentFromSystem(BDDMockito.anyString());

  }

  @Test
  public void testToDTO() throws Exception {
    Page entity = new PageBuilder().build();

    BDDMockito.doNothing().when(pageService).fillObject(BDDMockito.any(Page.class), BDDMockito.any(PageDTO.class));
    pageService.toDTO(entity);

    BDDMockito.verify(pageService, BDDMockito.times(1)).fillObject(BDDMockito.any(Page.class),
        BDDMockito.any(PageDTO.class));
  }

  @Test
  public void testToEntity() throws Exception {
    PageDTO dto = new PageDTOBuilder().build();

    BDDMockito.doNothing().when(pageService).fillObject(BDDMockito.any(PageDTO.class), BDDMockito.any(Page.class));
    pageService.toEntity(dto);

    BDDMockito.verify(pageService, BDDMockito.times(1)).fillObject(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Page.class));
  }

  @Test
  public void testGetPageByName_Found() throws Exception {
    PageDTO result = new PageDTOBuilder().id(123456789l).build();

    Page page = new PageBuilder().build();

    BDDMockito.doReturn(result).when(pageService).computePageDTOToReturn(BDDMockito.any(Page.class));
    BDDMockito.given(pageRepository.findByName(BDDMockito.anyString())).willReturn(page);

    Assert.assertEquals(result, pageService.getPageByName("someName"));
  }

  @Test
  public void testGetPageByName_Not_Found() throws Exception {

    BDDMockito.given(pageRepository.findByName(BDDMockito.anyString())).willReturn(null);

    Assert.assertNull(pageService.getPageByName("someName").getId());
  }

  @Test
  public void testGetPages() throws Exception {
    PageDTO result = new PageDTOBuilder().id(123456789l).build();
    Page page = new PageBuilder().build();

    BDDMockito.given(pageRepository.findAll(BDDMockito.any(Sort.class))).willReturn(Lists.newArrayList(page));
    BDDMockito.doReturn(Lists.newArrayList(result)).when(pageService).toListDTO(BDDMockito.anyList());

    Assert.assertEquals(result, pageService.getPages().get(0));

  }

  @Test
  public void testToListDTO() throws Exception {
    PageDTO result = new PageDTOBuilder().id(123456789l).build();
    BDDMockito.doReturn(result).when(pageService).computePageDTOToReturn(BDDMockito.any(Page.class));

    Page page = new PageBuilder().build();
    Assert.assertEquals(result, pageService.toListDTO(Lists.newArrayList(page)).get(0));

  }

  @Test
  public void testComputePageDTOToReturn() throws Exception {
    PageDTO dto = new PageDTOBuilder().id(123456789l).build();
    BDDMockito.doReturn(dto).when(pageService).toDTO(BDDMockito.any(Page.class));

    MetaElementDTO metaElement = new MetaElementDTOBuilder().build();
    BDDMockito.given(metaElementService.findMetaElementsByPageId(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(metaElement));

    OpenGraphMetaElementDTO openGraphMetaElement = new OpenGraphMetaElementDTOBuilder().build();
    BDDMockito.given(openGraphMetaElementService.findOpenGraphMetaElementsByPageId(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(openGraphMetaElement));

    PageDTO result = pageService.computePageDTOToReturn(new PageBuilder().build());

    Assert.assertEquals(metaElement, result.getMetaElements().get(0));
    Assert.assertEquals(openGraphMetaElement, result.getOpenGraphMetaElements().get(0));

  }
}
