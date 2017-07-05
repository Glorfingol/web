package cmpl.web.service.impl;

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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.util.StringUtils;

import cmpl.web.builder.FacebookImportPostBuilder;
import cmpl.web.builder.NewsContentDTOBuilder;
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.builder.NewsImageDTOBuilder;
import cmpl.web.message.WebMessageSource;
import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.service.NewsEntryService;

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

  private Locale locale;

  @InjectMocks
  @Spy
  private FacebookImportServiceImpl facebookImport;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeContentTypeFromBytes() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    ByteArrayInputStream is = new ByteArrayInputStream(data);

    BDDMockito.doReturn(is).when(facebookImport).prepareInputStream(Mockito.eq(data));
    FacebookImportPost post = new FacebookImportPostBuilder().facebookId("123456789").toFacebookImportPost();
    String result = facebookImport.computeContentTypeFromBytes(post, data);

    Assert.assertEquals("image/jpeg", result);
  }

  @Test
  public void testComputeContentTypeFromBytes_Null() throws Exception {

    byte[] data = new byte[]{1};
    ByteArrayInputStream is = new ByteArrayInputStream(data);

    BDDMockito.doReturn(is).when(facebookImport).prepareInputStream(Mockito.eq(data));
    FacebookImportPost post = new FacebookImportPostBuilder().facebookId("123456789").toFacebookImportPost();
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

    MediaOperations operations = Mockito.mock(MediaOperations.class);

    BDDMockito.doReturn(operations).when(facebookConnector).mediaOperations();

    MediaOperations result = facebookImport.getMediaOperations();

    Assert.assertEquals(operations, result);
  }

  @Test
  public void testRecoverImageBytes_Ok() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    MediaOperations operations = Mockito.mock(MediaOperations.class);

    FacebookImportPost post = new FacebookImportPostBuilder().objectId("123456789").toFacebookImportPost();

    BDDMockito.doReturn(data).when(operations).getAlbumImage(Mockito.anyString(), Mockito.any(ImageType.class));
    BDDMockito.doReturn(operations).when(facebookConnector).mediaOperations();

    byte[] result = facebookImport.recoverImageBytes(post);

    Assert.assertEquals(data, result);

  }

  @Test
  public void testComputeTitle() throws Exception {

    String title = "someTitle";
    BDDMockito.doReturn(title).when(messageSource).getI18n(Mockito.eq("facebook.news.title"), Mockito.eq(locale));

    String result = facebookImport.computeTitle(locale);

    Assert.assertEquals(title, result);
  }

  @Test
  public void testComputeTags() throws Exception {

    String tags = "someTags";
    BDDMockito.doReturn(tags).when(messageSource).getI18n(Mockito.eq("facebook.news.tag"), Mockito.eq(locale));

    String result = facebookImport.computeTags(locale);

    Assert.assertEquals(tags, result);
  }

  @Test
  public void testGetFacebookImageBase64Src_Ok() throws Exception {

    String base64 = "data:image/jpeg;base64,";

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    String contentType = "image/jpeg";

    base64 += Base64.encodeBase64String(data);

    FacebookImportPost post = new FacebookImportPostBuilder().toFacebookImportPost();

    BDDMockito.doReturn(data).when(facebookImport).recoverImageBytes(Mockito.eq(post));
    BDDMockito.doReturn(contentType).when(facebookImport)
        .computeContentTypeFromBytes(Mockito.eq(post), Mockito.eq(data));

    String result = facebookImport.getFacebookImageBase64Src(post);

    Assert.assertEquals(base64, result);
  }

  @Test
  public void testGetFacebookImageBase64Src_Ko_Empty_Data() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().toFacebookImportPost();

    BDDMockito.doReturn(new byte[]{}).when(facebookImport).recoverImageBytes(Mockito.eq(post));

    String result = facebookImport.getFacebookImageBase64Src(post);

    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testGetFacebookImageBase64Src_Ko_No_Content_Type() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    String contentType = "";

    FacebookImportPost post = new FacebookImportPostBuilder().toFacebookImportPost();

    BDDMockito.doReturn(data).when(facebookImport).recoverImageBytes(Mockito.eq(post));
    BDDMockito.doReturn(contentType).when(facebookImport)
        .computeContentTypeFromBytes(Mockito.eq(post), Mockito.eq(data));

    String result = facebookImport.getFacebookImageBase64Src(post);

    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testHasImage_True() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().photoUrl("someUrl").toFacebookImportPost();

    boolean result = facebookImport.hasImage(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testHasImage_False_Null() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().toFacebookImportPost();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasImage_False_Empty() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().photoUrl("").toFacebookImportPost();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasImage_False_Whitespaces() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().photoUrl(" ").toFacebookImportPost();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_True() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().description("someDescription").toFacebookImportPost();

    boolean result = facebookImport.hasContent(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testHasContent_False_Null() throws Exception {

    FacebookImportPost post = new FacebookImportPostBuilder().toFacebookImportPost();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_False_Empty() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().description("").toFacebookImportPost();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_False_Whitespaces() throws Exception {
    FacebookImportPost post = new FacebookImportPostBuilder().description("   ").toFacebookImportPost();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeAlt() throws Exception {
    String alt = "someAlt";
    String facebookId = "123456789";
    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).toFacebookImportPost();
    BDDMockito.doReturn(alt).when(messageSource).getI18n(Mockito.eq("facebook.image.alt"), Mockito.eq(locale));

    String result = facebookImport.computeAlt(post, locale);

    Assert.assertEquals(alt + facebookId, result);
  }

  @Test
  public void testComputeLegend() throws Exception {
    String legend = "someLegend";
    BDDMockito.doReturn(legend).when(messageSource).getI18n(Mockito.eq("facebook.image.legend"), Mockito.eq(locale));

    String result = facebookImport.computeLegend(locale);

    Assert.assertEquals(legend, result);
  }

  @Test
  public void testComputeNewsContentFromPost() throws Exception {
    String content = "someContent";
    String linkUrl = "linkUrl";
    String videoUrl = "videoUrl";
    FacebookImportPost post = new FacebookImportPostBuilder().description(content).videoUrl(videoUrl).linkUrl(linkUrl)
        .toFacebookImportPost();

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
    FacebookImportPost post = new FacebookImportPostBuilder().toFacebookImportPost();

    BDDMockito.doReturn(alt).when(facebookImport).computeAlt(Mockito.eq(post), Mockito.eq(locale));
    BDDMockito.doReturn(legend).when(facebookImport).computeLegend(Mockito.eq(locale));
    BDDMockito.doReturn(base64).when(facebookImport).getFacebookImageBase64Src(Mockito.eq(post));
    NewsImageDTO result = facebookImport.computeNewsImageFromPost(post, locale);

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

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author)
        .toFacebookImportPost();
    NewsContentDTO content = new NewsContentDTOBuilder().toNewsContentDTO();
    NewsImageDTO image = new NewsImageDTOBuilder().toNewsImageDTO();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(Mockito.eq(locale));
    BDDMockito.doReturn(true).when(facebookImport).hasContent(Mockito.eq(post));
    BDDMockito.doReturn(content).when(facebookImport).computeNewsContentFromPost(Mockito.eq(post));
    BDDMockito.doReturn(true).when(facebookImport).hasImage(Mockito.eq(post));
    BDDMockito.doReturn(image).when(facebookImport).computeNewsImageFromPost(Mockito.eq(post), Mockito.eq(locale));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, locale);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(content, result.getNewsContent());
    Assert.assertEquals(image, result.getNewsImage());

    Mockito.verify(facebookImport, Mockito.times(1)).computeTags(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).computeTitle(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).computeNewsContentFromPost(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(1)).computeNewsImageFromPost(Mockito.eq(post), Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).hasImage(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(1)).hasContent(Mockito.eq(post));

  }

  @Test
  public void testConvertPostToNewsEntry_Content_No_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author)
        .toFacebookImportPost();
    NewsContentDTO content = new NewsContentDTOBuilder().toNewsContentDTO();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(Mockito.eq(locale));
    BDDMockito.doReturn(true).when(facebookImport).hasContent(Mockito.eq(post));
    BDDMockito.doReturn(content).when(facebookImport).computeNewsContentFromPost(Mockito.eq(post));
    BDDMockito.doReturn(false).when(facebookImport).hasImage(Mockito.eq(post));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, locale);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(content, result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(facebookImport, Mockito.times(1)).computeTags(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).computeTitle(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).computeNewsContentFromPost(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(0)).computeNewsImageFromPost(Mockito.eq(post), Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).hasImage(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(1)).hasContent(Mockito.eq(post));
  }

  @Test
  public void testConvertPostToNewsEntry_No_Content_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author)
        .toFacebookImportPost();
    NewsImageDTO image = new NewsImageDTOBuilder().toNewsImageDTO();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(Mockito.eq(locale));
    BDDMockito.doReturn(false).when(facebookImport).hasContent(Mockito.eq(post));
    BDDMockito.doReturn(true).when(facebookImport).hasImage(Mockito.eq(post));
    BDDMockito.doReturn(image).when(facebookImport).computeNewsImageFromPost(Mockito.eq(post), Mockito.eq(locale));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, locale);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(image, result.getNewsImage());
    Assert.assertNull(result.getNewsContent());

    Mockito.verify(facebookImport, Mockito.times(1)).computeTags(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).computeTitle(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(0)).computeNewsContentFromPost(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(1)).computeNewsImageFromPost(Mockito.eq(post), Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).hasImage(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(1)).hasContent(Mockito.eq(post));
  }

  @Test
  public void testConvertPostToNewsEntry_No_Content_No_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author)
        .toFacebookImportPost();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(Mockito.eq(locale));
    BDDMockito.doReturn(false).when(facebookImport).hasContent(Mockito.eq(post));
    BDDMockito.doReturn(false).when(facebookImport).hasImage(Mockito.eq(post));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, locale);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    Mockito.verify(facebookImport, Mockito.times(1)).computeTags(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).computeTitle(Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(0)).computeNewsContentFromPost(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(0)).computeNewsImageFromPost(Mockito.eq(post), Mockito.eq(locale));
    Mockito.verify(facebookImport, Mockito.times(1)).hasImage(Mockito.eq(post));
    Mockito.verify(facebookImport, Mockito.times(1)).hasContent(Mockito.eq(post));
  }

  @Test
  public void testImportFacebookPost() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";

    FacebookImportPost post = new FacebookImportPostBuilder().facebookId(facebookId).author(author)
        .toFacebookImportPost();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().toNewsEntryDTO();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).createEntity(Mockito.any(NewsEntryDTO.class));
    BDDMockito.doReturn(newsEntry).when(facebookImport).convertPostToNewsEntry(Mockito.eq(post), Mockito.eq(locale));

    List<NewsEntryDTO> result = facebookImport.importFacebookPost(Lists.newArrayList(post), locale);

    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals(newsEntry, result.get(0));
  }
}
