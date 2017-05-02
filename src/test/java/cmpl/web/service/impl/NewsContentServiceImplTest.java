package cmpl.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import cmpl.web.builder.NewsContentDTOBuilder;
import cmpl.web.model.news.dao.NewsContent;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.repository.NewsContentRepository;

@RunWith(MockitoJUnitRunner.class)
public class NewsContentServiceImplTest {

  @Mock
  private NewsContentRepository repository;

  @InjectMocks
  @Spy
  private NewsContentServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsContentDTO dto = new NewsContentDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(dto), Mockito.any(NewsContent.class));
    NewsContent result = service.toEntity(dto);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(dto), Mockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsContent entity = new NewsContent();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(entity), Mockito.any(NewsContentDTO.class));
    NewsContentDTO result = service.toDTO(entity);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(entity), Mockito.eq(result));
  }

  @Test
  public void testFillObject() throws Exception {

    NewsContentDTO dto = new NewsContentDTOBuilder().content("someContent").id(1L).creationDate(new Date())
        .modificationDate(new Date()).toNewsContentDTO();

    NewsContent destination = new NewsContent();

    service.fillObject(dto, destination);

    Assert.assertEquals(dto.getId(), destination.getId());
    Assert.assertEquals(dto.getCreationDate(), destination.getCreationDate());
    Assert.assertEquals(dto.getModificationDate(), destination.getModificationDate());
    Assert.assertEquals(dto.getContent(), destination.getContent());

  }

  @Test
  public void testToListDTO() throws Exception {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    NewsContent content2 = new NewsContent();
    content2.setContent("content2");

    NewsContentDTO contentDTO1 = new NewsContentDTOBuilder().content("content1").id(1L).creationDate(new Date())
        .modificationDate(new Date()).toNewsContentDTO();
    NewsContentDTO contentDTO2 = new NewsContentDTOBuilder().content("content2").id(1L).creationDate(new Date())
        .modificationDate(new Date()).toNewsContentDTO();

    BDDMockito.doReturn(contentDTO1).when(service).toDTO(Mockito.eq(content1));
    BDDMockito.doReturn(contentDTO2).when(service).toDTO(Mockito.eq(content2));

    List<NewsContentDTO> result = service.toListDTO(Lists.newArrayList(content1, content2));

    Assert.assertEquals(contentDTO1, result.get(0));
    Assert.assertEquals(contentDTO2, result.get(1));

  }

  @Test
  public void testDeletEntity() {

    BDDMockito.doNothing().when(repository).delete(Mockito.anyLong());

    service.deleteEntity(1L);

    Mockito.verify(repository, Mockito.times(1)).delete(Mockito.eq(1L));

  }

  @Test
  public void testGetEntities_No_Result() {

    BDDMockito.doReturn(Lists.newArrayList()).when(repository).findAll(Mockito.any(Sort.class));

    List<NewsContentDTO> result = service.getEntities();

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testGetEntities_With_Results() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    NewsContent content2 = new NewsContent();
    content2.setContent("content2");

    List<NewsContent> contents = Lists.newArrayList(content1, content2);

    NewsContentDTO contentDTO1 = new NewsContentDTOBuilder().content("content1").id(1L).creationDate(new Date())
        .modificationDate(new Date()).toNewsContentDTO();
    NewsContentDTO contentDTO2 = new NewsContentDTOBuilder().content("content2").id(1L).creationDate(new Date())
        .modificationDate(new Date()).toNewsContentDTO();

    List<NewsContentDTO> contentsDTO = Lists.newArrayList(contentDTO1, contentDTO2);

    BDDMockito.doReturn(contents).when(repository).findAll(Mockito.any(Sort.class));
    BDDMockito.doReturn(contentsDTO).when(service).toListDTO(Mockito.eq(contents));

    List<NewsContentDTO> result = service.getEntities();

    Assert.assertEquals(content1.getContent(), result.get(0).getContent());
    Assert.assertEquals(content2.getContent(), result.get(1).getContent());

  }

  @Test
  public void testUpdateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    Calendar customCalendar = Calendar.getInstance();
    customCalendar.add(Calendar.DATE, -1);
    NewsContentDTO contentDTO1 = new NewsContentDTOBuilder().content("content1").id(1L)
        .creationDate(customCalendar.getTime()).modificationDate(customCalendar.getTime()).toNewsContentDTO();

    BDDMockito.doReturn(content1).when(service).toEntity(Mockito.eq(contentDTO1));
    BDDMockito.doReturn(contentDTO1).when(service).toDTO(Mockito.eq(content1));
    BDDMockito.doReturn(content1).when(repository).save(Mockito.eq(content1));

    NewsContentDTO result = service.updateEntity(contentDTO1);

    Assert.assertTrue(customCalendar.getTime().before(result.getModificationDate()));

  }

  @Test
  public void testGetEntity_Null() {

    BDDMockito.doReturn(null).when(repository).findOne(Mockito.anyLong());

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertNull(result);
  }

  @Test
  public void testGetEntity_Not_Null() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    Calendar customCalendar = Calendar.getInstance();
    customCalendar.add(Calendar.DATE, -1);
    NewsContentDTO contentDTO1 = new NewsContentDTOBuilder().content("content1").id(1L)
        .creationDate(customCalendar.getTime()).modificationDate(customCalendar.getTime()).toNewsContentDTO();

    BDDMockito.doReturn(content1).when(repository).findOne(Mockito.anyLong());
    BDDMockito.doReturn(contentDTO1).when(service).toDTO(Mockito.eq(content1));

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertEquals(content1.getContent(), result.getContent());
  }

  @Test
  public void testcreateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    Calendar customCalendar = Calendar.getInstance();
    customCalendar.add(Calendar.DATE, -1);
    NewsContentDTO contentDTO1 = new NewsContentDTOBuilder().content("content1").id(1L)
        .creationDate(customCalendar.getTime()).modificationDate(customCalendar.getTime()).toNewsContentDTO();

    BDDMockito.doReturn(content1).when(service).toEntity(Mockito.eq(contentDTO1));
    BDDMockito.doReturn(contentDTO1).when(service).toDTO(Mockito.eq(content1));
    BDDMockito.doReturn(content1).when(repository).save(Mockito.eq(content1));

    NewsContentDTO result = service.createEntity(contentDTO1);

    Assert.assertTrue(customCalendar.getTime().before(result.getModificationDate()));

  }
}
