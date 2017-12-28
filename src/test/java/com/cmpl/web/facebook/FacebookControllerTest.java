package com.cmpl.web.facebook;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.model.BaseException;

;

@RunWith(MockitoJUnitRunner.class)
public class FacebookControllerTest {

  @Mock
  private FacebookDispatcher dispatcher;

  @Mock
  private FacebookDisplayFactory facebookDisplayFactory;

  @InjectMocks
  @Spy
  private FacebookController controller;

  @Test
  public void testPrintFacebookAccess() throws Exception {
    ModelAndView model = new ModelAndView("back/facebook/access");

    BDDMockito.doReturn(model).when(facebookDisplayFactory)
        .computeModelAndViewForFacebookAccessPage(BDDMockito.eq(Locale.FRANCE));

    ModelAndView result = controller.printFacebookAccess();

    Assert.assertEquals(model, result);
  }

  @Test
  public void testPrintFacebookImport() throws Exception {

    ModelAndView model = new ModelAndView("back/facebook/import");

    BDDMockito.doReturn(model).when(facebookDisplayFactory)
        .computeModelAndViewForFacebookImportPage(BDDMockito.eq(Locale.FRANCE));

    ModelAndView result = controller.printFacebookImport();

    Assert.assertEquals(model, result);
  }

  @Test
  public void testCreateNewsEntry_Ok() throws Exception {
    FacebookImportRequest request = new FacebookImportRequest();

    FacebookImportResponse response = new FacebookImportResponse();

    BDDMockito.doReturn(response).when(dispatcher)
        .createEntity(BDDMockito.any(FacebookImportRequest.class), BDDMockito.any(Locale.class));

    ResponseEntity<FacebookImportResponse> result = controller.createNewsEntry(request);

    Assert.assertEquals(response, result.getBody());

  }

  @Test
  public void testCreateNewsEntry_Ko() throws Exception {

    FacebookImportRequest request = new FacebookImportRequest();

    BDDMockito.doThrow(new BaseException()).when(dispatcher)
        .createEntity(BDDMockito.any(FacebookImportRequest.class), BDDMockito.any(Locale.class));

    ResponseEntity<FacebookImportResponse> result = controller.createNewsEntry(request);

    Assert.assertNull(result.getBody());
    Assert.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
  }

}
