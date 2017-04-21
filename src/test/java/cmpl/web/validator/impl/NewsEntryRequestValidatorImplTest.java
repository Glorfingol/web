package cmpl.web.validator.impl;

import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.builder.NewsContentRequestBuilder;
import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.error.ErrorCause;
import cmpl.web.model.news.error.NEWS_ERROR;
import cmpl.web.model.news.error.NEWS_ERROR_CAUSE;
import cmpl.web.model.news.rest.news.NewsContentRequest;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryRequestValidatorImplTest {

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @InjectMocks
  @Spy
  private NewsEntryRequestValidatorImpl validator;

  @Test
  public void testIsImageFormatValid_true() throws Exception {

    boolean result = validator.isImageFormatValid("png");
    Assert.assertTrue(result);
  }

  @Test
  public void testIsImageFormatValid_false() throws Exception {
    boolean result = validator.isImageFormatValid("jpg");
    Assert.assertFalse(result);
  }

  @Test
  public void testIsStringValid_True() throws Exception {
    boolean result = validator.isStringValid("someString");
    Assert.assertTrue(result);
  }

  @Test
  public void testIsStringValid_False_null() throws Exception {
    boolean result = validator.isStringValid(null);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsStringValid_False_empty() throws Exception {
    boolean result = validator.isStringValid("");
    Assert.assertFalse(result);
  }

  @Test
  public void testIsStringValid_False_blank() throws Exception {
    boolean result = validator.isStringValid(" ");
    Assert.assertFalse(result);
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
  public void testComputeCause() throws Exception {

    BDDMockito.doReturn("someCause").when(validator).getI18n(Mockito.anyString(), Mockito.eq(locale));

    ErrorCause result = validator.computeCause(NEWS_ERROR_CAUSE.INVALID_FORMAT, locale);

    Assert.assertEquals(NEWS_ERROR_CAUSE.INVALID_FORMAT.toString(), result.getCode());
    Assert.assertEquals("someCause", result.getMessage());
  }

  @Test
  public void testComputeError() throws Exception {

    ErrorCause errorCause1 = new ErrorCause();
    errorCause1.setCode("someCode1");
    errorCause1.setMessage("someMessage1");

    ErrorCause errorCause2 = new ErrorCause();
    errorCause2.setCode("someCode2");
    errorCause2.setMessage("someMessage2");

    List<ErrorCause> causes = Lists.newArrayList(errorCause1, errorCause2);

    Error result = validator.computeError(causes);

    Assert.assertEquals(2, result.getCauses().size());
    Assert.assertEquals(NEWS_ERROR.INVALID_REQUEST.toString(), result.getCode());

  }

}
