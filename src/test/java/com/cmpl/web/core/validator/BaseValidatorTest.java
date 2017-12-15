package com.cmpl.web.core.validator;

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

import com.cmpl.web.core.error.ERROR;
import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.core.validator.BaseValidator;
import com.cmpl.web.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class BaseValidatorTest {

  private Locale locale;

  @Mock
  private WebMessageSource messageSource;

  @InjectMocks
  @Spy
  private BaseValidator validator;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
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
  public void testComputeCause() throws Exception {

    BDDMockito.doReturn("someCause").when(validator).getI18n(Mockito.anyString(), Mockito.eq(locale));

    ErrorCause result = validator.computeCause(ERROR_CAUSE.INVALID_NEWS_FORMAT, locale);

    Assert.assertEquals(ERROR_CAUSE.INVALID_NEWS_FORMAT.toString(), result.getCode());
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
    Assert.assertEquals(ERROR.INVALID_REQUEST.toString(), result.getCode());

  }

}
