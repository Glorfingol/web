package cmpl.web.news;

import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import cmpl.web.builder.NewsContentRequestBuilder;
import cmpl.web.core.error.ERROR;
import cmpl.web.core.error.ERROR_CAUSE;
import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryRequestValidatorImplTest {

  private Locale locale;

  @Mock
  private WebMessageSource messageSource;

  @InjectMocks
  @Spy
  private NewsEntryRequestValidatorImpl validator;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testIsNewsContentValid_True() throws Exception {

    NewsContentRequest request = new NewsContentRequestBuilder().content("someString").toNewsContentRequest();
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertTrue(result);
  }

  @Test
  public void testIsNewsContentValid_False_null() throws Exception {
    NewsContentRequest request = new NewsContentRequestBuilder().toNewsContentRequest();
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsNewsContentValid_False_empty() throws Exception {
    NewsContentRequest request = new NewsContentRequestBuilder().content("").toNewsContentRequest();
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsNewsContentValid_False_blank() throws Exception {
    NewsContentRequest request = new NewsContentRequestBuilder().content(" ").toNewsContentRequest();
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsNewsImageValid_True_With_Request() {

    NewsImageRequest imageRequest = new NewsImageRequest();

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.anyString());

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsImageValid_True_Without_Request() {

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.anyString());

    List<ErrorCause> result = validator.isNewsImageValid(null, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_Src_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertEquals(errorCauseEmptyAlt.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_No_Src_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    ErrorCause errorCauseEmptySrc = new ErrorCause();
    errorCauseEmptySrc.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptySrc.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), Mockito.eq(locale));
    BDDMockito.doReturn(errorCauseEmptySrc).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_SRC), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertTrue(result.contains(errorCauseEmptySrc));
    Assert.assertTrue(result.contains(errorCauseEmptyAlt));
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), Mockito.eq(locale));
    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertTrue(result.contains(errorCauseEmptyLegend));
    Assert.assertTrue(result.contains(errorCauseEmptyAlt));
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_No_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptySrc = new ErrorCause();
    errorCauseEmptySrc.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptySrc.setMessage("someMessage1");

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptySrc).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_SRC), Mockito.eq(locale));
    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertTrue(result.contains(errorCauseEmptyLegend));
    Assert.assertTrue(result.contains(errorCauseEmptySrc));
  }

  @Test
  public void testIsNewsImageValid_False_Alt_No_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptySrc = new ErrorCause();
    errorCauseEmptySrc.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptySrc.setMessage("someMessage1");

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), Mockito.eq(locale));
    BDDMockito.doReturn(errorCauseEmptySrc).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_SRC), Mockito.eq(locale));
    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertTrue(result.contains(errorCauseEmptyLegend));
    Assert.assertTrue(result.contains(errorCauseEmptySrc));
    Assert.assertTrue(result.contains(errorCauseEmptyAlt));
  }

  @Test
  public void testIsNewsImageValid_False_Alt_No_Src_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptySrc = new ErrorCause();
    errorCauseEmptySrc.setCode(ERROR_CAUSE.EMPTY_NEWS_SRC.toString());
    errorCauseEmptySrc.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptySrc).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_SRC), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertEquals(errorCauseEmptySrc.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsImageValid_False_Alt_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";
    String src = "src";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);
    imageRequest.setSrc(src);

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(legend));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(src));
    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, locale);

    Assert.assertEquals(errorCauseEmptyLegend.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsEntryValid_True_No_Entry() {

    List<ErrorCause> result = validator.isNewsEntryValid(null, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsEntryValid_True() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(title));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(author));

    List<ErrorCause> result = validator.isNewsEntryValid(request, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsEntryValid_False_No_Title() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    ErrorCause errorCauseEmptyTitle = new ErrorCause();
    errorCauseEmptyTitle.setCode(ERROR_CAUSE.EMPTY_NEWS_TITLE.toString());
    errorCauseEmptyTitle.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(title));
    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(author));
    BDDMockito.doReturn(errorCauseEmptyTitle).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_TITLE), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsEntryValid(request, locale);

    Assert.assertEquals(errorCauseEmptyTitle.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsEntryValid_False_No_Author() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    ErrorCause errorCauseEmptyAuhtor = new ErrorCause();
    errorCauseEmptyAuhtor.setCode(ERROR_CAUSE.EMPTY_NEWS_AUTHOR.toString());
    errorCauseEmptyAuhtor.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(title));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(author));
    BDDMockito.doReturn(errorCauseEmptyAuhtor).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_AUTHOR), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsEntryValid(request, locale);

    Assert.assertEquals(errorCauseEmptyAuhtor.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsEntryValid_False_No_Title_No_Author() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    ErrorCause errorCauseEmptyTitle = new ErrorCause();
    errorCauseEmptyTitle.setCode(ERROR_CAUSE.EMPTY_NEWS_TITLE.toString());
    errorCauseEmptyTitle.setMessage("someMessage1");

    ErrorCause errorCauseEmptyAuhtor = new ErrorCause();
    errorCauseEmptyAuhtor.setCode(ERROR_CAUSE.EMPTY_NEWS_AUTHOR.toString());
    errorCauseEmptyAuhtor.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(title));
    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(author));
    BDDMockito.doReturn(errorCauseEmptyTitle).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_TITLE), Mockito.eq(locale));
    BDDMockito.doReturn(errorCauseEmptyAuhtor).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_AUTHOR), Mockito.eq(locale));

    List<ErrorCause> result = validator.isNewsEntryValid(request, locale);

    Assert.assertTrue(result.contains(errorCauseEmptyTitle));
    Assert.assertTrue(result.contains(errorCauseEmptyTitle));
  }

  @Test
  public void testIsContentValid_True_No_Content() {

    List<ErrorCause> result = validator.isContentValid(null, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testIsContentValid_True_Content() {

    String content = "content";
    NewsContentRequest contentRequest = new NewsContentRequest();
    contentRequest.setContent(content);

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(content));

    List<ErrorCause> result = validator.isContentValid(contentRequest, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testIsContentValid_False() {

    String content = "content";
    NewsContentRequest contentRequest = new NewsContentRequest();
    contentRequest.setContent(content);

    ErrorCause errorCauseEmptyContent = new ErrorCause();
    errorCauseEmptyContent.setCode(ERROR_CAUSE.EMPTY_NEWS_CONTENT.toString());
    errorCauseEmptyContent.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(content));
    BDDMockito.doReturn(errorCauseEmptyContent).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_CONTENT), Mockito.eq(locale));

    List<ErrorCause> result = validator.isContentValid(contentRequest, locale);

    Assert.assertEquals(errorCauseEmptyContent.getCode(), result.get(0).getCode());

  }

  @Test
  public void testIsIdValid_True() {

    String id = "id";

    BDDMockito.doReturn(true).when(validator).isStringValid(Mockito.eq(id));

    List<ErrorCause> result = validator.isIdValid(id, locale);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testIsIdValid_False_EmptyId() {

    String id = "";

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(Mockito.eq(id));
    BDDMockito.doReturn(errorCauseEmptyNewsId).when(validator)
        .computeCause(Mockito.eq(ERROR_CAUSE.EMPTY_NEWS_ID), Mockito.eq(locale));

    List<ErrorCause> result = validator.isIdValid(id, locale);

    Assert.assertEquals(errorCauseEmptyNewsId.getCode(), result.get(0).getCode());

  }

  @Test
  public void testValidateGet_Ok() {

    String id = "id";

    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isIdValid(Mockito.eq(id), Mockito.eq(locale));

    Error result = validator.validateGet(id, locale);
    Assert.assertNull(result);

  }

  @Test
  public void testValidateGet_Ko() {

    String id = "id";

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");
    List<ErrorCause> causes = Lists.newArrayList(errorCauseEmptyNewsId);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR.INVALID_REQUEST.toString());

    BDDMockito.doReturn(causes).when(validator).isIdValid(Mockito.eq(id), Mockito.eq(locale));
    BDDMockito.doReturn(error).when(validator).computeError(Mockito.eq(causes));

    Error result = validator.validateGet(id, locale);

    Assert.assertEquals(ERROR.INVALID_REQUEST.toString(), result.getCode());
    Assert.assertEquals(errorCauseEmptyNewsId, result.getCauses().get(0));

  }

  @Test
  public void testValidateDelete_Ok() {

    String id = "id";

    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isIdValid(Mockito.eq(id), Mockito.eq(locale));

    Error result = validator.validateDelete(id, locale);
    Assert.assertNull(result);

  }

  @Test
  public void testValidateDelete_Ko() {

    String id = "id";

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");
    List<ErrorCause> causes = Lists.newArrayList(errorCauseEmptyNewsId);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR.INVALID_REQUEST.toString());

    BDDMockito.doReturn(causes).when(validator).isIdValid(Mockito.eq(id), Mockito.eq(locale));
    BDDMockito.doReturn(error).when(validator).computeError(Mockito.eq(causes));

    Error result = validator.validateDelete(id, locale);

    Assert.assertEquals(ERROR.INVALID_REQUEST.toString(), result.getCode());
    Assert.assertEquals(errorCauseEmptyNewsId, result.getCauses().get(0));

  }

  @Test
  public void testValidateUpdate_Ok() {

    String id = "id";
    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isIdValid(Mockito.eq(id), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));

    Error result = validator.validateUpdate(request, id, locale);

    Assert.assertNull(result);
    Mockito.verify(validator, Mockito.times(1)).isIdValid(Mockito.eq(id), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));

  }

  @Test
  public void testValidateUpdate_Ko_EmptyId() {

    String id = "id";
    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");

    List<ErrorCause> causes = Lists.newArrayList(errorCauseEmptyNewsId);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR.INVALID_REQUEST.toString());

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(causes).when(validator).isIdValid(Mockito.eq(id), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));
    BDDMockito.doReturn(error).when(validator).computeError(Mockito.anyListOf(ErrorCause.class));

    Error result = validator.validateUpdate(request, id, locale);

    Assert.assertNotNull(result);
    Mockito.verify(validator, Mockito.times(1)).isIdValid(Mockito.eq(id), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));

  }

  @Test
  public void testValidateCreate_Ok() {

    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));

    Error result = validator.validateCreate(request, locale);

    Assert.assertNull(result);
    Mockito.verify(validator, Mockito.times(0)).isIdValid(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));

  }

  @Test
  public void testValidateCreate_Ko_EMPTY_NEWS_AUTHOR() {

    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    ErrorCause errorCauseEmptyAuthor = new ErrorCause();
    errorCauseEmptyAuthor.setCode(ERROR_CAUSE.EMPTY_NEWS_AUTHOR.toString());
    errorCauseEmptyAuthor.setMessage("someMessage1");

    List<ErrorCause> causes = Lists.newArrayList(errorCauseEmptyAuthor);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR.INVALID_REQUEST.toString());

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(causes).when(validator).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(validator).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));
    BDDMockito.doReturn(error).when(validator).computeError(Mockito.anyListOf(ErrorCause.class));

    Error result = validator.validateCreate(request, locale);

    Assert.assertNotNull(result);
    Mockito.verify(validator, Mockito.times(0)).isIdValid(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsEntryValid(Mockito.eq(request), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isContentValid(Mockito.eq(content), Mockito.eq(locale));
    Mockito.verify(validator, Mockito.times(1)).isNewsImageValid(Mockito.eq(image), Mockito.eq(locale));

  }

}
