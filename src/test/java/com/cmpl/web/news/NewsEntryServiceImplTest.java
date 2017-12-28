package com.cmpl.web.news;

import java.io.File;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.file.ImageConverterService;
import com.cmpl.web.file.ImageService;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryServiceImplTest {

  @Mock
  private NewsEntryRepository repository;

  @Mock
  private NewsContentService newsContentService;

  @Mock
  private NewsImageService newsImageService;

  @Mock
  private ImageConverterService imageConverterService;

  @Mock
  private ImageService imageService;

  @InjectMocks
  @Spy
  private NewsEntryServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsEntryDTO dto = new NewsEntryDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(dto), BDDMockito.any(NewsEntry.class));
    NewsEntry result = service.toEntity(dto);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsEntry entity = new NewsEntry();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(entity), BDDMockito.any(NewsEntry.class));
    NewsEntryDTO result = service.toDTO(entity);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }

  @Test
  public void testComputeNewsEntryDTO_No_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    entry.setId(1L);

    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doReturn(dto).when(service).toDTO(BDDMockito.eq(entry));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    BDDMockito.verify(newsImageService, BDDMockito.times(0)).getEntity(BDDMockito.eq(1L));
    BDDMockito.verify(newsContentService, BDDMockito.times(0)).getEntity(BDDMockito.eq(1L));
  }

  @Test
  public void testComputeNewsEntryDTO_No_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    entry.setContentId(String.valueOf(1L));
    entry.setId(1L);

    NewsContentDTO contentDTO = new NewsContentDTOBuilder().id(1L).build();
    NewsEntryDTO dto = new NewsEntryDTOBuilder().newsContent(contentDTO).id(1L).build();

    BDDMockito.doReturn(dto).when(service).toDTO(BDDMockito.eq(entry));
    BDDMockito.doReturn(contentDTO).when(newsContentService).getEntity(BDDMockito.eq(1L));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    BDDMockito.verify(newsContentService, BDDMockito.times(1)).getEntity(BDDMockito.eq(1L));
    BDDMockito.verify(newsImageService, BDDMockito.times(0)).getEntity(BDDMockito.eq(1L));

  }

  @Test
  public void testComputeNewsEntryDTO_Image_No_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    entry.setImageId(String.valueOf(1L));
    entry.setId(1L);

    NewsImageDTO imageDTO = new NewsImageDTOBuilder().id(1L).build();
    NewsEntryDTO dto = new NewsEntryDTOBuilder().newsImage(imageDTO).id(1L).build();

    BDDMockito.doReturn(dto).when(service).toDTO(BDDMockito.eq(entry));
    BDDMockito.doReturn(imageDTO).when(newsImageService).getEntity(BDDMockito.eq(1L));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    BDDMockito.verify(newsImageService, BDDMockito.times(1)).getEntity(BDDMockito.eq(1L));
    BDDMockito.verify(newsContentService, BDDMockito.times(0)).getEntity(BDDMockito.eq(1L));
  }

  @Test
  public void testComputeNewsEntryDTO_Image_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    entry.setContentId(String.valueOf(1L));
    entry.setTags("somTags");
    entry.setTitle("someTitle");
    entry.setAuthor("someAuhtor");
    entry.setImageId(String.valueOf(1L));
    entry.setId(1L);

    NewsImageDTO imageDTO = new NewsImageDTOBuilder().id(1L).build();
    NewsContentDTO contentDTO = new NewsContentDTOBuilder().id(1L).build();
    NewsEntryDTO dto = new NewsEntryDTOBuilder().newsContent(contentDTO).newsImage(imageDTO).id(1L).build();

    BDDMockito.doReturn(dto).when(service).toDTO(BDDMockito.eq(entry));
    BDDMockito.doReturn(imageDTO).when(newsImageService).getEntity(BDDMockito.eq(1L));
    BDDMockito.doReturn(contentDTO).when(newsContentService).getEntity(BDDMockito.eq(1L));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    BDDMockito.verify(newsImageService, BDDMockito.times(1)).getEntity(BDDMockito.eq(1L));
    BDDMockito.verify(newsContentService, BDDMockito.times(1)).getEntity(BDDMockito.eq(1L));
  }

  @Test
  public void testGetEntities_No_Result() throws Exception {

    BDDMockito.doReturn(Lists.newArrayList()).when(repository).findAll();

    List<NewsEntryDTO> result = service.getEntities();

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testGetEntities_With_Result() throws Exception {

    NewsEntry entry1 = new NewsEntry();
    entry1.setId(1L);

    NewsEntry entry2 = new NewsEntry();
    entry2.setId(1L);

    NewsEntryDTO dto1 = new NewsEntryDTOBuilder().id(1L).build();

    NewsEntryDTO dto2 = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doReturn(Lists.newArrayList(entry1, entry2)).when(repository).findAll();
    BDDMockito.doReturn(dto1).when(service).computeNewsEntryDTO(entry1);
    BDDMockito.doReturn(dto2).when(service).computeNewsEntryDTO(entry2);

    List<NewsEntryDTO> result = service.getEntities();

    Assert.assertEquals(dto1, result.get(0));
    Assert.assertEquals(dto2, result.get(1));

  }

  @Test
  public void testGetEntity() throws Exception {

    NewsEntry entry = new NewsEntry();
    entry.setId(1L);

    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doReturn(dto).when(service).computeNewsEntryDTO(entry);
    BDDMockito.doReturn(entry).when(repository).findOne(1L);

    NewsEntryDTO result = service.getEntity(1L);

    Assert.assertEquals(dto, result);
  }

  @Test
  public void testFormatImage() throws Exception {

    String base64Src = "someBase64Src";
    String imageSrc = "someUrl";
    NewsImageDTO formattingImage = new NewsImageDTOBuilder().base64Src(base64Src).build();
    NewsImageDTO formattedImage = new NewsImageDTOBuilder().src(imageSrc).build();

    BDDMockito.doReturn(formattedImage).when(imageConverterService)
        .computeNewsImageFromString(BDDMockito.eq(base64Src));

    NewsImageDTO result = service.formatImage(formattingImage);

    Assert.assertEquals(imageSrc, result.getSrc());
    Assert.assertEquals(formattingImage.getAlt(), result.getAlt());
    Assert.assertEquals(formattingImage.getLegend(), result.getLegend());
    Assert.assertEquals(formattingImage.getModificationDate(), result.getModificationDate());
    Assert.assertEquals(formattingImage.getCreationDate(), result.getCreationDate());
  }

  @Test
  public void testDealWithImageToUpdate_Create() throws Exception {
    String imageSrc = "someUrl";
    File imageFile = new File("somePath/img");
    NewsImageDTO formattingImage = new NewsImageDTOBuilder().build();
    NewsImageDTO formattedImage = new NewsImageDTOBuilder().src(imageSrc).build();

    BDDMockito.doReturn(formattedImage).when(newsImageService).createEntity(BDDMockito.eq(formattedImage));
    BDDMockito.doReturn(formattingImage).when(newsImageService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(imageFile).when(service)
        .saveToFileSystem(BDDMockito.any(NewsImageDTO.class), BDDMockito.anyString());
    BDDMockito.doReturn(imageSrc).when(service).computeImageSrc(BDDMockito.eq(imageFile));

    NewsImageDTO result = service.dealWithImageToUpdate(formattingImage, formattedImage);

    Assert.assertEquals(formattedImage, result);

    BDDMockito.verify(newsImageService, BDDMockito.times(1)).createEntity(BDDMockito.eq(formattedImage));
    BDDMockito.verify(newsImageService, BDDMockito.times(1)).updateEntity(BDDMockito.eq(formattedImage));

  }

  @Test
  public void testDealWithImageToUpdate_Update() throws Exception {
    String imageSrc = "someUrl";
    File imageFile = new File("somePath/img");

    NewsImageDTO formattingImage = new NewsImageDTOBuilder().id(1L).build();
    NewsImageDTO formattedImage = new NewsImageDTOBuilder().src(imageSrc).build();

    BDDMockito.doReturn(formattedImage).when(newsImageService).updateEntity(BDDMockito.eq(formattedImage));
    BDDMockito.doReturn(formattingImage).when(newsImageService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(imageFile).when(service)
        .saveToFileSystem(BDDMockito.any(NewsImageDTO.class), BDDMockito.anyString());
    BDDMockito.doReturn(imageSrc).when(service).computeImageSrc(BDDMockito.eq(imageFile));

    NewsImageDTO result = service.dealWithImageToUpdate(formattingImage, formattedImage);

    Assert.assertEquals(formattedImage, result);

    BDDMockito.verify(newsImageService, BDDMockito.times(0)).createEntity(BDDMockito.eq(formattedImage));
    BDDMockito.verify(newsImageService, BDDMockito.times(2)).updateEntity(BDDMockito.eq(formattedImage));
  }

  @Test
  public void testDealWithContentToUpdate_Create() throws Exception {
    String content = "content";
    NewsContentDTO contentToDealWith = new NewsContentDTOBuilder().content(content).build();

    BDDMockito.doReturn(contentToDealWith).when(newsContentService).createEntity(BDDMockito.eq(contentToDealWith));

    NewsContentDTO result = service.dealWithContentToUpdate(contentToDealWith);

    Assert.assertEquals(contentToDealWith, result);

    BDDMockito.verify(newsContentService, BDDMockito.times(1)).createEntity(BDDMockito.eq(contentToDealWith));
    BDDMockito.verify(newsContentService, BDDMockito.times(0)).updateEntity(BDDMockito.eq(contentToDealWith));

  }

  @Test
  public void testDealWithContentToUpdate_Update() throws Exception {
    String content = "content";
    NewsContentDTO contentToDealWith = new NewsContentDTOBuilder().content(content).id(1L).build();

    BDDMockito.doReturn(contentToDealWith).when(newsContentService).updateEntity(BDDMockito.eq(contentToDealWith));

    NewsContentDTO result = service.dealWithContentToUpdate(contentToDealWith);

    Assert.assertEquals(contentToDealWith, result);

    BDDMockito.verify(newsContentService, BDDMockito.times(0)).createEntity(BDDMockito.eq(contentToDealWith));
    BDDMockito.verify(newsContentService, BDDMockito.times(1)).updateEntity(BDDMockito.eq(contentToDealWith));
  }

  @Test
  public void testUpdateImage_Null() throws Exception {
    NewsImageDTO result = service.updateImage(null);

    Assert.assertNull(result);

    BDDMockito.verify(service, BDDMockito.times(0)).formatImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(service, BDDMockito.times(0)).dealWithImageToUpdate(BDDMockito.any(NewsImageDTO.class),
        BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testUpdateImage_Not_Null() throws Exception {

    NewsImageDTO formattingImage = new NewsImageDTOBuilder().id(1L).build();

    BDDMockito.doReturn(formattingImage).when(service).formatImage(formattingImage);
    BDDMockito.doReturn(formattingImage).when(service)
        .dealWithImageToUpdate(BDDMockito.any(NewsImageDTO.class), BDDMockito.any(NewsImageDTO.class));

    NewsImageDTO result = service.updateImage(formattingImage);

    Assert.assertEquals(formattingImage, result);

    BDDMockito.verify(service, BDDMockito.times(1)).formatImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).dealWithImageToUpdate(BDDMockito.any(NewsImageDTO.class),
        BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testUpdateContent_Null() throws Exception {
    NewsContentDTO result = service.updateContent(null);

    Assert.assertNull(result);

    BDDMockito.verify(service, BDDMockito.times(0)).dealWithContentToUpdate(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testUpdateContent_Not_Null() throws Exception {

    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).build();

    BDDMockito.doReturn(content).when(service).dealWithContentToUpdate(BDDMockito.eq(content));

    NewsContentDTO result = service.updateContent(content);

    Assert.assertEquals(content, result);

    BDDMockito.verify(service, BDDMockito.times(1)).dealWithContentToUpdate(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testCreateImage_Null() throws Exception {
    String result = service.createImage(null);

    Assert.assertEquals("", result);

    BDDMockito.verify(newsImageService, BDDMockito.times(0)).createEntity(BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testCreateImage_Not_Null() throws Exception {
    File imageFile = new File("somePath/img");

    String imageSrc = "somePath";

    NewsImageDTO formattingImage = new NewsImageDTOBuilder().id(1L).build();

    BDDMockito.doReturn(formattingImage).when(service).formatImage(formattingImage);
    BDDMockito.doReturn(formattingImage).when(newsImageService).createEntity(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(formattingImage).when(newsImageService).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(imageFile).when(service)
        .saveToFileSystem(BDDMockito.any(NewsImageDTO.class), BDDMockito.anyString());
    BDDMockito.doReturn(imageSrc).when(service).computeImageSrc(BDDMockito.eq(imageFile));

    String result = service.createImage(formattingImage);

    Assert.assertEquals(String.valueOf(1l), result);

    BDDMockito.verify(service, BDDMockito.times(1)).formatImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(newsImageService, BDDMockito.times(1)).createEntity(BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testCreateContent_Null() throws Exception {
    String result = service.createContent(null);

    Assert.assertEquals("", result);

    BDDMockito.verify(newsContentService, BDDMockito.times(0)).createEntity(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testCreateContent_Not_Null() throws Exception {

    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).build();

    BDDMockito.doReturn(content).when(newsContentService).createEntity(BDDMockito.eq(content));

    String result = service.createContent(content);

    Assert.assertEquals(String.valueOf(1l), result);

    BDDMockito.verify(newsContentService, BDDMockito.times(1)).createEntity(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testUpdateEntity_No_Image_No_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(null).when(service).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(null).when(service).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_No_Image_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).build();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsContent(content).id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(content).when(service).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(null).when(service).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNotNull(result.getNewsContent());
    Assert.assertEquals(content.getId(), result.getNewsContent().getId());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = new NewsImageDTOBuilder().id(1L).build();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsImage(image).id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(null).when(service).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(image).when(service).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNotNull(result.getNewsImage());
    Assert.assertEquals(image.getId(), result.getNewsImage().getId());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = new NewsImageDTOBuilder().id(1L).build();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).build();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsImage(image).newsContent(content).id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(content).when(service).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(image).when(service).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNotNull(result.getNewsContent());
    Assert.assertEquals(content.getId(), result.getNewsContent().getId());
    Assert.assertNotNull(result.getNewsImage());
    Assert.assertEquals(image.getId(), result.getNewsImage().getId());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_No_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(null).when(service).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(null).when(service).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = new NewsImageDTOBuilder().id(1L).build();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).build();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsImage(image).newsContent(content).id(1L).build();
    NewsEntryDTO returnEntry = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn("").when(service).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(returnEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_No_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).build();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().newsContent(content).id(1L).build();
    NewsEntryDTO returnEntry = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn("").when(service).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(returnEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsContentDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testComputeImageSrc() throws Exception {
    String basePath = "test\\img\\";
    String alt = "someFileName";
    String extension = ".png";
    File testFile = new File(basePath + alt + extension);

    String result = service.computeImageSrc(testFile);

    Assert.assertEquals("img\\" + alt + extension, result);
  }

  @Test
  public void testSaveToFileSystem() throws Exception {
    String basePath = "test\\img\\";
    String alt = "someFileName";
    String extension = ".png";
    File testFile = new File(basePath + alt + extension);

    BDDMockito.doReturn(testFile).when(imageService).saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

    File result = service.saveToFileSystem(new NewsImageDTO(), "666");
    Assert.assertEquals(testFile, result);
  }

  @Test
  public void testSaveToFileSystem_With_Exception_Should_Return_Null() throws Exception {
    BDDMockito.doThrow(new BaseException("")).when(imageService)
        .saveFileOnSystem(BDDMockito.anyString(), BDDMockito.anyString());

    File result = service.saveToFileSystem(new NewsImageDTO(), "666");
    Assert.assertNull(result);
  }

  @Test
  public void testIsAlreadyImportedFromFacebook_True() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().build();

    BDDMockito.doReturn(Lists.newArrayList(entry)).when(repository).findByFacebookId(BDDMockito.anyString());

    boolean result = service.isAlreadyImportedFromFacebook("123456789");

    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyImportedFromFacebook_False() throws Exception {

    BDDMockito.doReturn(Lists.newArrayList()).when(repository).findByFacebookId(BDDMockito.anyString());

    boolean result = service.isAlreadyImportedFromFacebook("123456789");

    Assert.assertFalse(result);
  }

}
