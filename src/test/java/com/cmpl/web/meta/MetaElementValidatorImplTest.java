package com.cmpl.web.meta;

import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.error.ErrorBuilder;
import com.cmpl.web.core.error.ErrorCauseBuilder;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementValidatorImplTest {

  @Spy
  @InjectMocks
  private MetaElementValidatorImpl validator;

  @Test
  public void testValidateDelete_No_Error() throws Exception {

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateDelete("123456789", Locale.FRANCE));
  }

  @Test
  public void testValidateDelete_Error() throws Exception {

    Error error = new ErrorBuilder().build();
    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_META_CONTENT.getCauseKey()).message("badOrder")
        .build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertEquals(error, validator.validateDelete("123456789", Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_No_Errors() throws Exception {

    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("someName").content("someContent")
        .pageId("somePageId").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateCreate(form.getPageId(), form.getName(), form.getContent(), Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_No_Page() throws Exception {

    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("someName").content("someContent").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getContent()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_META_PAGE_ID.getCauseKey()).message("no_page")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error,
        validator.validateCreate(form.getPageId(), form.getName(), form.getContent(), Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_No_Name() throws Exception {

    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("someName").content("someContent").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getContent()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_META_NAME.getCauseKey()).message("no_name")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error,
        validator.validateCreate(form.getPageId(), form.getName(), form.getContent(), Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_No_Content() throws Exception {

    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("someName").content("someContent").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getContent()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_META_CONTENT.getCauseKey()).message("no_content")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error,
        validator.validateCreate(form.getPageId(), form.getName(), form.getContent(), Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Multiple_Errors() throws Exception {

    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("someName").content("someContent").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getName()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getContent()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_META_NAME.getCauseKey()).message("no_name")
        .build();
    ErrorCause causeTitle = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_META_CONTENT.getCauseKey())
        .message("no_content").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause, causeTitle)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error,
        validator.validateCreate(form.getPageId(), form.getName(), form.getContent(), Locale.FRANCE));
  }

  @Test
  public void testValidateCreateStringOpenGraphMetaElementCreateFormLocale() throws Exception {
    OpenGraphMetaElementCreateForm form = new OpenGraphMetaElementCreateFormBuilder().property("someProperty")
        .content("someContent").build();
    BDDMockito
        .doReturn(null)
        .when(validator)
        .validateCreate(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyString(),
            BDDMockito.any(Locale.class));
    Assert.assertNull(validator.validateCreate("123456789", form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.anyString(), BDDMockito.anyString(),
        BDDMockito.anyString(), BDDMockito.any(Locale.class));

  }

  @Test
  public void testValidateCreateStringMetaElementCreateFormLocale() throws Exception {
    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("someName").content("someContent").build();
    BDDMockito
        .doReturn(null)
        .when(validator)
        .validateCreate(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyString(),
            BDDMockito.any(Locale.class));
    Assert.assertNull(validator.validateCreate("123456789", form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.anyString(), BDDMockito.anyString(),
        BDDMockito.anyString(), BDDMockito.any(Locale.class));
  }

}
