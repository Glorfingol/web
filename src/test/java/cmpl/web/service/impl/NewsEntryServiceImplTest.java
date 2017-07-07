package cmpl.web.service.impl;

import java.io.File;
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
import org.springframework.util.CollectionUtils;

import cmpl.web.builder.NewsContentDTOBuilder;
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.builder.NewsImageDTOBuilder;
import cmpl.web.model.BaseException;
import cmpl.web.model.news.dao.NewsEntry;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.repository.NewsEntryRepository;
import cmpl.web.service.FileService;
import cmpl.web.service.ImageConverterService;
import cmpl.web.service.NewsContentService;
import cmpl.web.service.NewsImageService;

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
  private FileService fileService;

  @InjectMocks
  @Spy
  private NewsEntryServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsEntryDTO dto = new NewsEntryDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(dto), Mockito.any(NewsEntry.class));
    NewsEntry result = service.toEntity(dto);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(dto), Mockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsEntry entity = new NewsEntry();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(entity), Mockito.any(NewsEntry.class));
    NewsEntryDTO result = service.toDTO(entity);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(entity), Mockito.eq(result));
  }

  @Test
  public void testComputeNewsEntryDTO_No_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    entry.setId(1L);

    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doReturn(dto).when(service).toDTO(Mockito.eq(entry));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    Mockito.verify(newsImageService, Mockito.times(0)).getEntity(Mockito.eq(1L));
    Mockito.verify(newsContentService, Mockito.times(0)).getEntity(Mockito.eq(1L));
  }

  @Test
  public void testComputeNewsEntryDTO_No_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    entry.setContentId(String.valueOf(1L));
    entry.setId(1L);

    NewsContentDTO contentDTO = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();
    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).newsContent(contentDTO).toNewsEntryDTO();

    BDDMockito.doReturn(dto).when(service).toDTO(Mockito.eq(entry));
    BDDMockito.doReturn(contentDTO).when(newsContentService).getEntity(Mockito.eq(1L));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    Mockito.verify(newsContentService, Mockito.times(1)).getEntity(Mockito.eq(1L));
    Mockito.verify(newsImageService, Mockito.times(0)).getEntity(Mockito.eq(1L));

  }

  @Test
  public void testComputeNewsEntryDTO_Image_No_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    entry.setImageId(String.valueOf(1L));
    entry.setId(1L);

    NewsImageDTO imageDTO = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();
    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).newsImage(imageDTO).toNewsEntryDTO();

    BDDMockito.doReturn(dto).when(service).toDTO(Mockito.eq(entry));
    BDDMockito.doReturn(imageDTO).when(newsImageService).getEntity(Mockito.eq(1L));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    Mockito.verify(newsImageService, Mockito.times(1)).getEntity(Mockito.eq(1L));
    Mockito.verify(newsContentService, Mockito.times(0)).getEntity(Mockito.eq(1L));
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

    NewsImageDTO imageDTO = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();
    NewsContentDTO contentDTO = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();
    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).newsContent(contentDTO).newsImage(imageDTO).toNewsEntryDTO();

    BDDMockito.doReturn(dto).when(service).toDTO(Mockito.eq(entry));
    BDDMockito.doReturn(imageDTO).when(newsImageService).getEntity(Mockito.eq(1L));
    BDDMockito.doReturn(contentDTO).when(newsContentService).getEntity(Mockito.eq(1L));

    NewsEntryDTO result = service.computeNewsEntryDTO(entry);

    Assert.assertEquals(dto, result);

    Mockito.verify(newsImageService, Mockito.times(1)).getEntity(Mockito.eq(1L));
    Mockito.verify(newsContentService, Mockito.times(1)).getEntity(Mockito.eq(1L));
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

    NewsEntryDTO dto1 = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    NewsEntryDTO dto2 = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

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

    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doReturn(dto).when(service).computeNewsEntryDTO(entry);
    BDDMockito.doReturn(entry).when(repository).findOne(1L);

    NewsEntryDTO result = service.getEntity(1L);

    Assert.assertEquals(dto, result);
  }

  @Test
  public void testFormatImage() throws Exception {

    String base64Src = "someBase64Src";
    String imageSrc = "someUrl";
    NewsImageDTO formattingImage = new NewsImageDTOBuilder().base64Src(base64Src).toNewsImageDTO();
    NewsImageDTO formattedImage = new NewsImageDTOBuilder().src(imageSrc).toNewsImageDTO();

    BDDMockito.doReturn(formattedImage).when(imageConverterService).computeNewsImageFromString(Mockito.eq(base64Src));

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
    NewsImageDTO formattingImage = new NewsImageDTOBuilder().toNewsImageDTO();
    NewsImageDTO formattedImage = new NewsImageDTOBuilder().src(imageSrc).toNewsImageDTO();

    BDDMockito.doReturn(formattedImage).when(newsImageService).createEntity(Mockito.eq(formattedImage));
    BDDMockito.doReturn(formattingImage).when(newsImageService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(imageFile).when(service).saveToFileSystem(Mockito.any(NewsImageDTO.class), Mockito.anyString());
    BDDMockito.doReturn(imageSrc).when(service).computeImageSrc(Mockito.eq(imageFile));

    NewsImageDTO result = service.dealWithImageToUpdate(formattingImage, formattedImage);

    Assert.assertEquals(formattedImage, result);

    Mockito.verify(newsImageService, Mockito.times(1)).createEntity(Mockito.eq(formattedImage));
    Mockito.verify(newsImageService, Mockito.times(1)).updateEntity(Mockito.eq(formattedImage));

  }

  @Test
  public void testDealWithImageToUpdate_Update() throws Exception {
    String imageSrc = "someUrl";
    File imageFile = new File("somePath/img");

    NewsImageDTO formattingImage = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();
    NewsImageDTO formattedImage = new NewsImageDTOBuilder().src(imageSrc).toNewsImageDTO();

    BDDMockito.doReturn(formattedImage).when(newsImageService).updateEntity(Mockito.eq(formattedImage));
    BDDMockito.doReturn(formattingImage).when(newsImageService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(imageFile).when(service).saveToFileSystem(Mockito.any(NewsImageDTO.class), Mockito.anyString());
    BDDMockito.doReturn(imageSrc).when(service).computeImageSrc(Mockito.eq(imageFile));

    NewsImageDTO result = service.dealWithImageToUpdate(formattingImage, formattedImage);

    Assert.assertEquals(formattedImage, result);

    Mockito.verify(newsImageService, Mockito.times(0)).createEntity(Mockito.eq(formattedImage));
    Mockito.verify(newsImageService, Mockito.times(2)).updateEntity(Mockito.eq(formattedImage));
  }

  @Test
  public void testDealWithContentToUpdate_Create() throws Exception {
    String content = "content";
    NewsContentDTO contentToDealWith = new NewsContentDTOBuilder().content(content).toNewsContentDTO();

    BDDMockito.doReturn(contentToDealWith).when(newsContentService).createEntity(Mockito.eq(contentToDealWith));

    NewsContentDTO result = service.dealWithContentToUpdate(contentToDealWith);

    Assert.assertEquals(contentToDealWith, result);

    Mockito.verify(newsContentService, Mockito.times(1)).createEntity(Mockito.eq(contentToDealWith));
    Mockito.verify(newsContentService, Mockito.times(0)).updateEntity(Mockito.eq(contentToDealWith));

  }

  @Test
  public void testDealWithContentToUpdate_Update() throws Exception {
    String content = "content";
    NewsContentDTO contentToDealWith = new NewsContentDTOBuilder().id(1L).content(content).toNewsContentDTO();

    BDDMockito.doReturn(contentToDealWith).when(newsContentService).updateEntity(Mockito.eq(contentToDealWith));

    NewsContentDTO result = service.dealWithContentToUpdate(contentToDealWith);

    Assert.assertEquals(contentToDealWith, result);

    Mockito.verify(newsContentService, Mockito.times(0)).createEntity(Mockito.eq(contentToDealWith));
    Mockito.verify(newsContentService, Mockito.times(1)).updateEntity(Mockito.eq(contentToDealWith));
  }

  @Test
  public void testUpdateImage_Null() throws Exception {
    NewsImageDTO result = service.updateImage(null);

    Assert.assertNull(result);

    Mockito.verify(service, Mockito.times(0)).formatImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(service, Mockito.times(0)).dealWithImageToUpdate(Mockito.any(NewsImageDTO.class),
        Mockito.any(NewsImageDTO.class));
  }

  @Test
  public void testUpdateImage_Not_Null() throws Exception {

    NewsImageDTO formattingImage = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();

    BDDMockito.doReturn(formattingImage).when(service).formatImage(formattingImage);
    BDDMockito.doReturn(formattingImage).when(service)
        .dealWithImageToUpdate(Mockito.any(NewsImageDTO.class), Mockito.any(NewsImageDTO.class));

    NewsImageDTO result = service.updateImage(formattingImage);

    Assert.assertEquals(formattingImage, result);

    Mockito.verify(service, Mockito.times(1)).formatImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(service, Mockito.times(1)).dealWithImageToUpdate(Mockito.any(NewsImageDTO.class),
        Mockito.any(NewsImageDTO.class));
  }

  @Test
  public void testUpdateContent_Null() throws Exception {
    NewsContentDTO result = service.updateContent(null);

    Assert.assertNull(result);

    Mockito.verify(service, Mockito.times(0)).dealWithContentToUpdate(Mockito.any(NewsContentDTO.class));
  }

  @Test
  public void testUpdateContent_Not_Null() throws Exception {

    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();

    BDDMockito.doReturn(content).when(service).dealWithContentToUpdate(Mockito.eq(content));

    NewsContentDTO result = service.updateContent(content);

    Assert.assertEquals(content, result);

    Mockito.verify(service, Mockito.times(1)).dealWithContentToUpdate(Mockito.any(NewsContentDTO.class));
  }

  @Test
  public void testCreateImage_Null() throws Exception {
    String result = service.createImage(null);

    Assert.assertEquals("", result);

    Mockito.verify(newsImageService, Mockito.times(0)).createEntity(Mockito.any(NewsImageDTO.class));
  }

  @Test
  public void testCreateImage_Not_Null() throws Exception {
    File imageFile = new File("somePath/img");

    String imageSrc = "somePath";

    NewsImageDTO formattingImage = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();

    BDDMockito.doReturn(formattingImage).when(service).formatImage(formattingImage);
    BDDMockito.doReturn(formattingImage).when(newsImageService).createEntity(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(formattingImage).when(newsImageService).getEntity(Mockito.anyLong());
    BDDMockito.doReturn(imageFile).when(service).saveToFileSystem(Mockito.any(NewsImageDTO.class), Mockito.anyString());
    BDDMockito.doReturn(imageSrc).when(service).computeImageSrc(Mockito.eq(imageFile));

    String result = service.createImage(formattingImage);

    Assert.assertEquals(String.valueOf(1l), result);

    Mockito.verify(service, Mockito.times(1)).formatImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(newsImageService, Mockito.times(1)).createEntity(Mockito.any(NewsImageDTO.class));
  }

  @Test
  public void testCreateContent_Null() throws Exception {
    String result = service.createContent(null);

    Assert.assertEquals("", result);

    Mockito.verify(newsContentService, Mockito.times(0)).createEntity(Mockito.any(NewsContentDTO.class));
  }

  @Test
  public void testCreateContent_Not_Null() throws Exception {

    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();

    BDDMockito.doReturn(content).when(newsContentService).createEntity(Mockito.eq(content));

    String result = service.createContent(content);

    Assert.assertEquals(String.valueOf(1l), result);

    Mockito.verify(newsContentService, Mockito.times(1)).createEntity(Mockito.any(NewsContentDTO.class));
  }

  @Test
  public void testUpdateEntity_No_Image_No_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(null).when(service).updateContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(null).when(service).updateImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).updateContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).updateImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_No_Image_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).newsContent(content).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(content).when(service).updateContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(null).when(service).updateImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNotNull(result.getNewsContent());
    Assert.assertEquals(content.getId(), result.getNewsContent().getId());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).updateContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).updateImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).newsImage(image).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(null).when(service).updateContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(image).when(service).updateImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNotNull(result.getNewsImage());
    Assert.assertEquals(image.getId(), result.getNewsImage().getId());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).updateContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).updateImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).newsImage(image).newsContent(content).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(content).when(service).updateContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(image).when(service).updateImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNotNull(result.getNewsContent());
    Assert.assertEquals(content.getId(), result.getNewsContent().getId());
    Assert.assertNotNull(result.getNewsImage());
    Assert.assertEquals(image.getId(), result.getNewsImage().getId());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).updateContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).updateImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_No_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(null).when(service).createContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(null).when(service).createImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).createContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).createImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = new NewsImageDTOBuilder().id(1L).toNewsImageDTO();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).newsImage(image).newsContent(content).toNewsEntryDTO();
    NewsEntryDTO returnEntry = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn("").when(service).createContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(returnEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).createContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).createImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_No_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsContentDTO content = new NewsContentDTOBuilder().id(1L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).newsContent(content).toNewsEntryDTO();
    NewsEntryDTO returnEntry = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn("").when(service).createImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(returnEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).createContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).createImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(1L).toNewsEntryDTO();

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(newsEntry), Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createContent(Mockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createImage(Mockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(Mockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(Mockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.any(NewsContentDTO.class),
        Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).createContent(Mockito.any(NewsContentDTO.class));
    Mockito.verify(service, Mockito.times(1)).createImage(Mockito.any(NewsImageDTO.class));
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(NewsEntry.class));
    Mockito.verify(service, Mockito.times(1)).toDTO(Mockito.any(NewsEntry.class));
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

    BDDMockito.doReturn(testFile).when(fileService).saveFileOnSystem(Mockito.anyString(), Mockito.anyString());

    File result = service.saveToFileSystem(new NewsImageDTO(), "666");
    Assert.assertEquals(testFile, result);
  }

  @Test
  public void testSaveToFileSystem_With_Exception_Should_Return_Null() throws Exception {
    BDDMockito.doThrow(new BaseException("")).when(fileService)
        .saveFileOnSystem(Mockito.anyString(), Mockito.anyString());

    File result = service.saveToFileSystem(new NewsImageDTO(), "666");
    Assert.assertNull(result);
  }

  @Test
  public void testIsAlreadyImportedFromFacebook_True() throws Exception {
    NewsEntryDTO entry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    BDDMockito.doReturn(Lists.newArrayList(entry)).when(repository).findByFacebookId(Mockito.anyString());

    boolean result = service.isAlreadyImportedFromFacebook("123456789");

    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyImportedFromFacebook_False() throws Exception {

    BDDMockito.doReturn(Lists.newArrayList()).when(repository).findByFacebookId(Mockito.anyString());

    boolean result = service.isAlreadyImportedFromFacebook("123456789");

    Assert.assertFalse(result);
  }

}
