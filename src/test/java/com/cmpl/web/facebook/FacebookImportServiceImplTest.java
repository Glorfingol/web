package com.cmpl.web.facebook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.util.StringUtils;

import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.news.NewsContentDTO;
import com.cmpl.web.news.NewsContentDTOBuilder;
import com.cmpl.web.news.NewsEntryDTO;
import com.cmpl.web.news.NewsEntryDTOBuilder;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.news.NewsImageDTO;
import com.cmpl.web.news.NewsImageDTOBuilder;

;

@RunWith(MockitoJUnitRunner.class)
public class FacebookImportServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private Facebook facebookConnector;

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private FacebookImportServiceImpl facebookImport;

  @Test
  public void testComputeContentTypeFromBytes() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    ByteArrayInputStream is = new ByteArrayInputStream(data);

    BDDMockito.doReturn(is).when(facebookImport).prepareInputStream(BDDMockito.eq(data));
    FacebookImportPost post = new FacebookImportPostBuilder().facebookId("123456789").build();
    String result = facebookImport.computeContentTypeFromBytes(post, data);

    Assert.assertEquals("image/jpeg", result);
  }

  @Test
  public void testComputeContentTypeFromBytes_Null() throws Exception {

    byte[] data = new byte[]{1};
    ByteArrayInputStream is = new ByteArrayInputStream(data);

    BDDMockito.doReturn(is).when(facebookImport).prepareInputStream(BDDMockito.eq(data));
    FacebookImportPost post = new FacebookImportPostBuilder().facebookId("123456789").build();
    String result = facebookImport.computeContentTypeFromBytes(post, data);

    Assert.assertNull(result);
  }

  @Test
  public void testPrepareInputStream() throws Exception {
    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    ByteArrayInputStream is = new ByteArrayInputStream(data);
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    ByteArrayInputStream result = facebookImport.prepareInputStream(data);
    ByteArrayOutputStream resultOs = new ByteArrayOutputStream();

    IOUtils.copy(is, os);
    IOUtils.copy(result, resultOs);

    Assert.assertEquals(os.toString(), resultOs.toString());

  }

  @Test
  public void testGetMediaOperations() throws Exception {

    MediaOperations operations = BDDMockito.mock(MediaOperations.class);

    BDDMockito.doReturn(operations).when(facebookConnector).mediaOperations();

    MediaOperations result = facebookImport.getMediaOperations();

    Assert.assertEquals(operations, result);
  }

  @Test
  public void testRecoverImageBytes_Ok() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    MediaOperations operations = BDDMockito.mock(MediaOperations.class);

    FacebookImportPost post = new FacebookImportPostBuilder().objectId("123456789").build();

    BDDMockito.doReturn(data).when(operations).getAlbumImage(BDDMockito.anyString(), BDDMockito.any(ImageType.class));
    BDDMockito.doReturn(operations).when(facebookConnector).mediaOperations();

    byte[] result = facebookImport.recoverImageBytes(post);

    Assert.assertEquals(data, result);

  }

  @Test
  public void testComputeTitle() throws Exception {

    String title = "someTitle";
    BDDMockito.doReturn(title).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.news.title"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeTitle(Locale.FRANCE);

    Assert.assertEquals(title, result);
  }

  @Test
  public void testComputeTags() throws Exception {

    String tags = "someTags";
    BDDMockito.doReturn(tags).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.news.tag"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeTags(Locale.FRANCE);

    Assert.assertEquals(tags, result);
  }

  @Test
  public void testGetFacebookImageBase64Src_Ok() throws Exception {

    String base64 = "data:image/jpeg;base64,";

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    String contentType = "image/jpeg";

    base64 += Base64.encodeBase64String(data);

    FacebookImportPost post = new FacebookImportPostBuilder().build();

    BDDMockito.doReturn(data).when(facebookImport).recoverImageBytes(BDDMockito.eq(post));
    BDDMockito.doReturn(contentType).when(facebookImport)
        .computeContentTypeFromBytes(BDDMockito.eq(post), BDDMockito.eq(data));

    String result = facebookImport.getFacebookImageBase64Src(post);

    Assert.assertEquals(base64, result);
  }

  @Test
  public void testGetFacebookImageBase64Src_Ko_Empty_Data() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().build();

    BDDMockito.doReturn(new byte[]{}).when(facebookImport).recoverImageBytes(BDDMockito.eq(post));

    String result = facebookImport.getFacebookImageBase64Src(post);

    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testGetFacebookImageBase64Src_Ko_No_Content_Type() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    String contentType = "";

    FacebookImportPost post = new FacebookImportPostBuilder().build();

    BDDMockito.doReturn(data).when(facebookImport).recoverImageBytes(BDDMockito.eq(post));
    BDDMockito.doReturn(contentType).when(facebookImport)
        .computeContentTypeFromBytes(BDDMockito.eq(post), BDDMockito.eq(data));

    String result = facebookImport.getFacebookImageBase64Src(post);

    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testHasImage_True() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().photoUrl("someUrl").build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testHasImage_False_Null() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasImage_False_Empty() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().photoUrl("").build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasImage_False_Whitespaces() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().photoUrl(" ").build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_True() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().description("someDescription").build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testHasContent_False_Null() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_False_Empty() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().description("").build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_False_Whitespaces() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().description("   ").build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeAlt() throws Exception {
    String alt = "someAlt";
    String facebookId = "123456789";
    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).build();
    BDDMockito.doReturn(alt).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.image.alt"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeAlt(post, Locale.FRANCE);

    Assert.assertEquals(alt + facebookId, result);
  }

  @Test
  public void testComputeLegend() throws Exception {
    String legend = "someLegend";
    BDDMockito.doReturn(legend).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.image.legend"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeLegend(Locale.FRANCE);

    Assert.assertEquals(legend, result);
  }

  @Test
  public void testComputeNewsContentFromPost() throws Exception {
    String content = "someContent";
    String linkUrl = "linkUrl";
    String videoUrl = "videoUrl";
    FacebookImportPost post = new FacebookImportPostBuilder().description(content).videoUrl(videoUrl).linkUrl(linkUrl)
        .build();

    NewsContentDTO result = facebookImport.computeNewsContentFromPost(post);

    Assert.assertEquals(content, result.getContent());
    Assert.assertEquals(linkUrl, result.getLinkUrl());
    Assert.assertEquals(videoUrl, result.getVideoUrl());
  }

  @Test
  public void testComputeNewsImageFromPost() throws Exception {
    String alt = "someAlt";
    String legend = "someLegend";
    String base64 = "base64";
    FacebookImportPost post = new FacebookImportPostBuilder().build();

    BDDMockito.doReturn(alt).when(facebookImport).computeAlt(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(legend).when(facebookImport).computeLegend(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(base64).when(facebookImport).getFacebookImageBase64Src(BDDMockito.eq(post));
    NewsImageDTO result = facebookImport.computeNewsImageFromPost(post, Locale.FRANCE);

    Assert.assertEquals(alt, result.getAlt());
    Assert.assertEquals(legend, result.getLegend());
    Assert.assertEquals(base64, result.getBase64Src());
  }

  @Test
  public void testConvertPostToNewsEntry_Content_Image() throws Exception {
    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author).build();
    NewsContentDTO content = new NewsContentDTOBuilder().build();
    NewsImageDTO image = new NewsImageDTOBuilder().build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(content).when(facebookImport).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.doReturn(true).when(facebookImport).hasImage(BDDMockito.eq(post));
    BDDMockito.doReturn(image).when(facebookImport)
        .computeNewsImageFromPost(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(content, result.getNewsContent());
    Assert.assertEquals(image, result.getNewsImage());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));

  }

  @Test
  public void testConvertPostToNewsEntry_Content_No_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author).build();
    NewsContentDTO content = new NewsContentDTOBuilder().build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(content).when(facebookImport).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.doReturn(false).when(facebookImport).hasImage(BDDMockito.eq(post));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(content, result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));
  }

  @Test
  public void testConvertPostToNewsEntry_No_Content_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author).build();
    NewsImageDTO image = new NewsImageDTOBuilder().build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(true).when(facebookImport).hasImage(BDDMockito.eq(post));
    BDDMockito.doReturn(image).when(facebookImport)
        .computeNewsImageFromPost(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(image, result.getNewsImage());
    Assert.assertNull(result.getNewsContent());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));
  }

  @Test
  public void testConvertPostToNewsEntry_No_Content_No_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author).build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(false).when(facebookImport).hasImage(BDDMockito.eq(post));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));
  }

  @Test
  public void testImportFacebookPost() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author).build();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().build();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).createEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.doReturn(newsEntry).when(facebookImport)
        .convertPostToNewsEntry(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));

    List<NewsEntryDTO> result = facebookImport.importFacebookPost(Lists.newArrayList(post), Locale.FRANCE);

    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals(newsEntry, result.get(0));
  }
}
