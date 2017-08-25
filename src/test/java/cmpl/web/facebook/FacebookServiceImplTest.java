package cmpl.web.facebook;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.social.facebook.api.Reference;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.builder.ImportablePostBuilder;
import cmpl.web.builder.PostBuilder;
import cmpl.web.builder.ReferenceBuilder;
import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.model.BaseException;
import cmpl.web.news.NewsEntryService;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private Facebook facebookConnector;

  @Mock
  private SimpleDateFormat dateFormat;

  @Mock
  private ConnectionRepository connectionRepository;

  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private FacebookServiceImpl facebookService;

  @Test
  public void testComputeDescription_From_Message() throws Exception {
    String message = "someMessage";
    Post post = new PostBuilder().message(message).toPost();

    String result = facebookService.computeDescription(post);

    Assert.assertEquals(message, result);

  }

  @Test
  public void testComputeDescription_From_Description() throws Exception {
    String description = "someDescription";
    Post post = new PostBuilder().description(description).toPost();

    String result = facebookService.computeDescription(post);

    Assert.assertEquals(description, result);

  }

  @Test
  public void testComputeOnclick() throws Exception {
    String toggle = "toggleImport('123456789')";

    String id = "123456789";
    Post post = new PostBuilder().id(id).toPost();

    BDDMockito.doReturn("123456789").when(facebookService).computeId(Mockito.eq(post));

    String result = facebookService.computeOnclick(post);

    Assert.assertEquals(toggle, result);
  }

  @Test
  public void testComputeId() throws Exception {
    String id = "123456789";
    Post post = new PostBuilder().id(id).toPost();

    String result = facebookService.computeId(post);

    Assert.assertEquals(id, result);
  }

  @Test
  public void testComputeObjectId() throws Exception {
    String objectId = "123456789";
    Post post = new PostBuilder().objectId(objectId).toPost();

    String result = facebookService.computeObjectId(post);

    Assert.assertEquals(objectId, result);
  }

  @Test
  public void testComputeCreatedTime() throws Exception {
    ZoneId defaultZoneId = ZoneId.systemDefault();
    LocalDate createdTime = LocalDate.now();
    Post post = new PostBuilder().createdTime(Date.from(createdTime.atStartOfDay(defaultZoneId).toInstant())).toPost();

    LocalDate result = facebookService.computeCreatedTime(post);

    Assert.assertEquals(createdTime, result);
  }

  @Test
  public void testComputeType() throws Exception {
    PostType type = PostType.STATUS;
    Post post = new PostBuilder().type(type).toPost();

    PostType result = facebookService.computeType(post);

    Assert.assertEquals(type, result);
  }

  @Test
  public void testComputeLink() throws Exception {
    String link = "someLink";
    Post post = new PostBuilder().link(link).toPost();

    String result = facebookService.computeLink(post);

    Assert.assertEquals(link, result);
  }

  @Test
  public void testComputeAuthor() throws Exception {
    String name = "someName";
    Reference reference = new ReferenceBuilder().name(name).toReference();
    Post post = new PostBuilder().reference(reference).toPost();

    String result = facebookService.computeAuthor(post);

    Assert.assertEquals(name, result);

  }

  @Test
  public void testComputePhotoUrl_Ok() throws Exception {

    String picture = "somePictureUrl";
    Post post = new PostBuilder().picture(picture).type(PostType.PHOTO).toPost();

    String result = facebookService.computePhotoUrl(post);

    Assert.assertEquals(picture, result);
  }

  @Test
  public void testComputePhotoUrl_No_Photo() throws Exception {
    Post post = new PostBuilder().type(PostType.STATUS).toPost();

    String result = facebookService.computePhotoUrl(post);

    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testComputeVideoUrl_Ok() throws Exception {
    String sourceNotAutoplay = "someVideoUrl?autoplay=0";
    String sourceAutoplay = "someVideoUrl?autoplay=1";
    Post post = new PostBuilder().source(sourceAutoplay).toPost();

    BDDMockito.doReturn(sourceNotAutoplay).when(facebookService).makeVideoNotAutoplay(Mockito.eq(sourceAutoplay));
    String result = facebookService.computeVideoUrl(post);

    Assert.assertEquals(sourceNotAutoplay, result);

  }

  @Test
  public void testComputeVideoUrl_No_Video() throws Exception {

    Post post = new PostBuilder().toPost();

    String result = facebookService.computeVideoUrl(post);

    Assert.assertNull(result);
  }

  @Test
  public void testMakeVideoNotAutoplay_Change() throws Exception {
    String sourceNotAutoplay = "someVideoUrl?autoplay=0";
    String sourceAutoplay = "someVideoUrl?autoplay=1";

    String result = facebookService.makeVideoNotAutoplay(sourceAutoplay);

    Assert.assertEquals(sourceNotAutoplay, result);
  }

  @Test
  public void testMakeVideoNotAutoplay_No_Change() throws Exception {

    String source = "someVideoUrl";

    String result = facebookService.makeVideoNotAutoplay(source);

    Assert.assertEquals(source, result);
  }

  @Test
  public void testComputeTitle_From_Name() throws Exception {
    String name = "someName";
    Post post = new PostBuilder().name(name).toPost();

    String result = facebookService.computeTitle(post);

    Assert.assertEquals(name, result);
  }

  @Test
  public void testComputeTitle_From_Caption() throws Exception {
    String caption = "someCaption";
    Post post = new PostBuilder().caption(caption).toPost();

    String result = facebookService.computeTitle(post);

    Assert.assertEquals(caption, result);
  }

  @Test
  public void testComputeTitle_From_Type() throws Exception {
    String type = "Type STATUS";
    Post post = new PostBuilder().type(PostType.STATUS).toPost();

    String result = facebookService.computeTitle(post);

    Assert.assertEquals(type, result);
  }

  @Test
  public void testComputeImportablePost() throws Exception {
    String message = "someMessage";
    String caption = "someCaption";
    String name = "someName";
    String source = "someVideoUrl";
    String picture = "somePictureUrl";
    String link = "someLink";
    PostType type = PostType.STATUS;
    String objectId = "123456789";
    String id = "123456789";
    String description = "someDescription";
    String author = "author";
    LocalDate createdTime = LocalDate.now();
    String formattedDate = "24/10/89";
    String onclick = "toggleImport(" + id + ");";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    ZoneId defaultZoneId = ZoneId.systemDefault();

    Post post = new PostBuilder().id(id).objectId(objectId).name(name).caption(caption).description(description)
        .source(source).message(message).picture(picture).link(link).type(type)
        .createdTime(Date.from(createdTime.atStartOfDay(defaultZoneId).toInstant())).toPost();

    BDDMockito.doReturn(author).when(facebookService).computeAuthor(Mockito.eq(post));
    BDDMockito.doReturn(description).when(facebookService).computeDescription(Mockito.eq(post));
    BDDMockito.doReturn(picture).when(facebookService).computePhotoUrl(Mockito.eq(post));
    BDDMockito.doReturn(link).when(facebookService).computeLink(Mockito.eq(post));
    BDDMockito.doReturn(source).when(facebookService).computeVideoUrl(Mockito.eq(post));
    BDDMockito.doReturn(name).when(facebookService).computeTitle(Mockito.eq(post));
    BDDMockito.doReturn(type).when(facebookService).computeType(Mockito.eq(post));
    BDDMockito.doReturn(id).when(facebookService).computeId(Mockito.eq(post));
    BDDMockito.doReturn(onclick).when(facebookService).computeOnclick(Mockito.eq(post));
    BDDMockito.doReturn(createdTime).when(facebookService).computeCreatedTime(Mockito.eq(post));
    BDDMockito.doReturn(objectId).when(facebookService).computeObjectId(Mockito.eq(post));
    BDDMockito.doReturn(formattedDate).when(facebookService)
        .computeFormattedDate(Mockito.eq(post), Mockito.eq(formatter));

    ImportablePost result = facebookService.computeImportablePost(post, formatter);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(description, result.getDescription());
    Assert.assertEquals(picture, result.getPhotoUrl());
    Assert.assertEquals(link, result.getLinkUrl());
    Assert.assertEquals(source, result.getVideoUrl());
    Assert.assertEquals(name, result.getTitle());
    Assert.assertEquals(type, result.getType());
    Assert.assertEquals(id, result.getFacebookId());
    Assert.assertEquals(onclick, result.getOnclick());
    Assert.assertEquals(createdTime, result.getCreationDate());
    Assert.assertEquals(objectId, result.getObjectId());
    Assert.assertEquals(formattedDate, result.getFormattedDate());

    Mockito.verify(facebookService, Mockito.times(1)).computeAuthor(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeDescription(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computePhotoUrl(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeLink(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeVideoUrl(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeTitle(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeType(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeId(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeOnclick(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeCreatedTime(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeObjectId(Mockito.eq(post));
    Mockito.verify(facebookService, Mockito.times(1)).computeCreatedTime(Mockito.eq(post));

  }

  @Test
  public void testComputeFormattedDate() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, 10, 15);
    Date date = calendar.getTime();

    Post post = new PostBuilder().createdTime(date).toPost();

    String result = facebookService.computeFormattedDate(post, formatter);

    Assert.assertEquals("15/11/17", result);

  }

  @Test
  public void testCanImportPost_Ok() throws Exception {

    ImportablePost post = new ImportablePostBuilder().type(PostType.STATUS).description("someDescription")
        .facebookId("someId").toImportablePost();

    BDDMockito.doReturn(false).when(newsEntryService).isAlreadyImportedFromFacebook(Mockito.anyString());

    boolean result = facebookService.canImportPost(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testCanImportPost_Ko_Status_And_Empty_Description() throws Exception {

    ImportablePost post = new ImportablePostBuilder().type(PostType.STATUS).facebookId("someId").toImportablePost();

    boolean result = facebookService.canImportPost(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testCanImportPost_Ko_Already_Imported() throws Exception {

    ImportablePost post = new ImportablePostBuilder().type(PostType.VIDEO).description("someDescription")
        .facebookId("someId").toImportablePost();

    BDDMockito.doReturn(true).when(newsEntryService).isAlreadyImportedFromFacebook(Mockito.anyString());

    boolean result = facebookService.canImportPost(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testGetFeedOperations() throws Exception {

    FeedOperations operations = Mockito.mock(FeedOperations.class);

    BDDMockito.doReturn(operations).when(facebookConnector).feedOperations();

    FeedOperations result = facebookService.getFeedOperations();

    Assert.assertEquals(operations, result);
  }

  @Test
  public void testComputeImportablePosts_Importable() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    Post postToImport = new PostBuilder().toPost();
    PagingParameters previous = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagingParameters next = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagedList<Post> postsToImport = new PagedList<>(Lists.newArrayList(postToImport), previous, next);

    ImportablePost importable = new ImportablePostBuilder().toImportablePost();

    BDDMockito.doReturn(formatter).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(importable).when(facebookService)
        .computeImportablePost(Mockito.eq(postToImport), Mockito.any(DateTimeFormatter.class));
    BDDMockito.doReturn(true).when(facebookService).canImportPost(Mockito.eq(importable));

    List<ImportablePost> postsImportable = facebookService.computeImportablePosts(postsToImport);

    Assert.assertTrue(!CollectionUtils.isEmpty(postsImportable));
    Assert.assertEquals(importable, postsImportable.get(0));

  }

  @Test
  public void testComputeImportablePosts_Already_Imported() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    Post postToImport = new PostBuilder().toPost();
    PagingParameters previous = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagingParameters next = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagedList<Post> postsToImport = new PagedList<>(Lists.newArrayList(postToImport), previous, next);

    ImportablePost importable = new ImportablePostBuilder().toImportablePost();

    BDDMockito.doReturn(formatter).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(importable).when(facebookService)
        .computeImportablePost(Mockito.eq(postToImport), Mockito.any(DateTimeFormatter.class));
    BDDMockito.doReturn(false).when(facebookService).canImportPost(Mockito.eq(importable));

    List<ImportablePost> postsImportable = facebookService.computeImportablePosts(postsToImport);

    Assert.assertTrue(CollectionUtils.isEmpty(postsImportable));

  }

  @Test
  public void testGetRecentFeed_Ok() throws Exception {

    @SuppressWarnings("unchecked")
    Connection<Facebook> connection = (Connection<Facebook>) Mockito.mock(Connection.class);
    FeedOperations operations = Mockito.mock(FeedOperations.class);

    Post postToImport = new PostBuilder().toPost();
    PagingParameters previous = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagingParameters next = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagedList<Post> postsToImport = new PagedList<>(Lists.newArrayList(postToImport), previous, next);

    ImportablePost importable = new ImportablePostBuilder().toImportablePost();
    List<ImportablePost> importables = Lists.newArrayList(importable);

    BDDMockito.doReturn(connection).when(connectionRepository).findPrimaryConnection(Facebook.class);
    BDDMockito.doReturn(operations).when(facebookConnector).feedOperations();
    BDDMockito.doReturn(postsToImport).when(operations).getPosts();
    BDDMockito.doReturn(importables).when(facebookService).computeImportablePosts(Mockito.eq(postsToImport));

    List<ImportablePost> result = facebookService.getRecentFeed();

    Assert.assertEquals(importables, result);

  }

  @Test
  public void testGetRecentFeed_Exception() throws Exception {
    exception.expect(BaseException.class);
    BDDMockito.doReturn(null).when(connectionRepository).findConnections(Facebook.class);
    facebookService.getRecentFeed();
  }
}
