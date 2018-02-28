package com.cmpl.web.core.widget;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.error.*;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class WidgetValidatorImplTest {

  @Mock
  WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private WidgetValidatorImpl validator;

  @Test
  public void testValidate_Ok() {
    Error result = validator.validate("someName", "HTML", Locale.FRANCE);

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(result);
  }

  @Test
  public void testValidate_Ko_No_Name() {

    ErrorCause noNameCause = ErrorCauseBuilder.create().build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(noNameCause)).build();
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());
    BDDMockito.doReturn(noNameCause).when(validator).computeCause(BDDMockito.any(ERROR_CAUSE.class),
        BDDMockito.any(Locale.class));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.anyString());

    Error result = validator.validate("", "HTML", Locale.FRANCE);

    Assert.assertNotNull(result);
    Assert.assertEquals(noNameCause, result.getCauses().get(0));
  }

  @Test
  public void testValidate_Ko_No_Type() {

  }

  @Test
  public void testValidate_Ko_No_Name_No_Type() {

  }
}
