package com.cmpl.web.menu;

import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.error.ErrorBuilder;
import com.cmpl.web.core.error.ErrorCauseBuilder;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;

@RunWith(MockitoJUnitRunner.class)
public class MenuValidatorImplTest {

  @Spy
  @InjectMocks
  private MenuValidatorImpl validator;

  @Test
  public void testValidateUpdate_No_Errors() throws Exception {

    MenuUpdateForm form = new MenuUpdateFormBuilder().orderInMenu(1).build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Bad_Order() throws Exception {

    MenuUpdateForm form = new MenuUpdateFormBuilder().orderInMenu(0).build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Empty_Page() throws Exception {

    MenuUpdateForm form = new MenuUpdateFormBuilder().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_PAGE.getCauseKey()).message("empty_page").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Empty_Menu_Title() throws Exception {

    MenuUpdateForm form = new MenuUpdateFormBuilder().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey()).message("empty_title")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Empty_Multiple_Errors() throws Exception {

    MenuUpdateForm form = new MenuUpdateFormBuilder().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    ErrorCause causeTitle = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey())
        .message("empty_title").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause, causeTitle)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_No_Errors() throws Exception {

    MenuCreateForm form = new MenuCreateFormBuilder().orderInMenu(1).build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Bad_Order() throws Exception {

    MenuCreateForm form = new MenuCreateFormBuilder().orderInMenu(0).build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Empty_Page() throws Exception {

    MenuCreateForm form = new MenuCreateFormBuilder().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_PAGE.getCauseKey()).message("empty_page").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Empty_Menu_Title() throws Exception {

    MenuCreateForm form = new MenuCreateFormBuilder().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey()).message("empty_title")
        .build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Multiple_Errors() throws Exception {

    MenuCreateForm form = new MenuCreateFormBuilder().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = new ErrorCauseBuilder().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    ErrorCause causeTitle = new ErrorCauseBuilder().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey())
        .message("empty_title").build();
    Error error = new ErrorBuilder().causes(Lists.newArrayList(cause, causeTitle)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyListOf(ErrorCause.class));

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }
}
