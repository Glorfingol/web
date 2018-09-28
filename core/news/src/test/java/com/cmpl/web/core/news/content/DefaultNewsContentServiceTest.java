package com.cmpl.web.core.news.content;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.NewsContent;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class DefaultNewsContentServiceTest {

  @Mock
  private NewsContentMapper mapper;

  @Mock
  private NewsContentDAO newsContentDAO;


  @Mock
  private FileService fileService;

  @InjectMocks
  @Spy
  private DefaultNewsContentService service;

  @Test
  public void testDeleteEntity() {

    BDDMockito.doNothing().when(newsContentDAO).deleteEntity(BDDMockito.anyLong());

    service.deleteEntity(1L);

    BDDMockito.verify(newsContentDAO, BDDMockito.times(1)).deleteEntity(BDDMockito.eq(1L));

  }

  @Test
  public void testGetEntities_No_Result() {

    BDDMockito.doReturn(Arrays.asList()).when(newsContentDAO).getEntities();

    List<NewsContentDTO> result = service.getEntities();

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testGetEntities_With_Results() {

    NewsContent content1 = new NewsContent();
    content1.setLinkUrl("content1");
    NewsContent content2 = new NewsContent();
    content2.setLinkUrl("content2");

    LocalDateTime date = LocalDateTime.now();

    List<NewsContent> contents = Arrays.asList(content1, content2);

    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().linkUrl("content1").id(1L)
      .creationDate(date)
      .modificationDate(date).build();
    NewsContentDTO contentDTO2 = NewsContentDTOBuilder.create().linkUrl("content2").id(1L)
      .creationDate(date)
      .modificationDate(date).build();

    BDDMockito.doReturn(contents).when(newsContentDAO).getEntities();
    BDDMockito.doReturn(contentDTO1).when(mapper).toDTO(BDDMockito.eq(content1));
    BDDMockito.doReturn(contentDTO2).when(mapper).toDTO(BDDMockito.eq(content2));

    List<NewsContentDTO> result = service.getEntities();

    Assert.assertEquals(content1.getLinkUrl(), result.get(0).getLinkUrl());
    Assert.assertEquals(content2.getLinkUrl(), result.get(1).getLinkUrl());

  }

  @Test
  public void testUpdateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setLinkUrl("content1");

    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().linkUrl("content1").id(1L)
      .creationDate(date)
      .modificationDate(date).build();

    BDDMockito.given(mapper.toDTO(BDDMockito.any(NewsContent.class))).willReturn(contentDTO1);
    BDDMockito.given(mapper.toEntity(BDDMockito.any(NewsContentDTO.class))).willReturn(content1);
    BDDMockito.given(newsContentDAO.updateEntity(BDDMockito.any(NewsContent.class)))
      .willReturn(content1);

    NewsContentDTO result = service.updateEntity(contentDTO1);

    Assert.assertEquals(contentDTO1, result);

  }
  

  @Test
  public void testGetEntity_Not_Null() {

    NewsContent content1 = new NewsContent();
    content1.setLinkUrl("content1");
    NewsContent optional = content1;

    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().linkUrl("content1").id(1L)
      .creationDate(date)
      .modificationDate(date).build();

    BDDMockito.doReturn(optional).when(newsContentDAO).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(contentDTO1).when(mapper).toDTO(BDDMockito.eq(content1));

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertEquals(content1.getLinkUrl(), result.getLinkUrl());
  }

  @Test
  public void testCreateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setLinkUrl("content1");
    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().linkUrl("content1").id(1L)
      .creationDate(date)
      .modificationDate(date).build();

    BDDMockito.given(mapper.toEntity(BDDMockito.any(NewsContentDTO.class))).willReturn(content1);
    BDDMockito.given(mapper.toDTO(BDDMockito.any(NewsContent.class))).willReturn(contentDTO1);
    BDDMockito.given(newsContentDAO.createEntity(BDDMockito.any(NewsContent.class)))
      .willReturn(content1);

    NewsContentDTO result = service.createEntity(contentDTO1);

    Assert.assertEquals(contentDTO1, result);

  }
}
