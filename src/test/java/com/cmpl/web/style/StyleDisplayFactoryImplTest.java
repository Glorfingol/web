package com.cmpl.web.style;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class StyleDisplayFactoryImplTest {

  @Mock
  private StyleService styleService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private MetaElementFactory metaElementFactory;

  @Spy
  @InjectMocks
  private StyleDisplayFactoryImpl displayFactory;

  @Test
  public void testInitMedia() throws Exception {

    BDDMockito.given(contextHolder.getMediaDisplayPath()).willReturn("somePath/");

    MediaDTO result = displayFactory.initMedia();

    Assert.assertEquals("styles.css", result.getName());
    Assert.assertEquals(".css", result.getExtension());
    Assert.assertTrue(0l == result.getSize());
    Assert.assertEquals("text/css", result.getContentType());
    Assert.assertEquals("somePath/styles.css", result.getSrc());
  }

  @Test
  public void testInitStyle() throws Exception {

    MediaDTO media = new MediaDTO();
    media.setId(123456789l);
    BDDMockito.doReturn(media).when(displayFactory).initMedia();

    StyleDTO style = new StyleDTO();
    style.setMedia(media);
    BDDMockito.given(styleService.createEntity(BDDMockito.any(StyleDTO.class))).willReturn(style);

    Assert.assertEquals(style, displayFactory.initStyle());
  }

  @Test
  public void testComputeModelAndViewForViewStyles_Init_Styles() throws Exception {
    StyleDTO style = new StyleDTO();

    ModelAndView stylesManager = new ModelAndView();
    BDDMockito.doReturn(stylesManager).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(Locale.class));
    BDDMockito.given(styleService.getStyle()).willReturn(null);
    BDDMockito.doReturn(style).when(displayFactory).initStyle();

    ModelAndView result = displayFactory.computeModelAndViewForViewStyles(Locale.FRANCE);
    Assert.assertEquals(style, result.getModel().get("style"));
  }

  @Test
  public void testComputeModelAndViewForViewStyles_No_Init_Styles() throws Exception {

    StyleDTO style = new StyleDTO();

    ModelAndView stylesManager = new ModelAndView();
    BDDMockito.doReturn(stylesManager).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(Locale.class));
    BDDMockito.given(styleService.getStyle()).willReturn(style);

    ModelAndView result = displayFactory.computeModelAndViewForViewStyles(Locale.FRANCE);
    Assert.assertEquals(style, result.getModel().get("style"));
  }

  @Test
  public void testComputeModelAndViewForUpdateStyles_Init_Styles() throws Exception {
    StyleDTO style = new StyleDTO();
    MediaDTO media = new MediaDTO();
    style.setMedia(media);
    style.setContent("someContent");
    StyleForm form = new StyleForm(style);

    ModelAndView stylesManager = new ModelAndView();
    BDDMockito.doReturn(stylesManager).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(Locale.class));
    BDDMockito.given(styleService.getStyle()).willReturn(null);
    BDDMockito.doReturn(style).when(displayFactory).initStyle();

    ModelAndView result = displayFactory.computeModelAndViewForUpdateStyles(Locale.FRANCE);
    Assert.assertEquals(form.getContent(), ((StyleForm) result.getModel().get("styleForm")).getContent());
  }

  @Test
  public void testComputeModelAndViewForUpdateStyles_No_Init_Styles() throws Exception {

    StyleDTO style = new StyleDTO();
    MediaDTO media = new MediaDTO();
    style.setMedia(media);
    style.setContent("someContent");
    StyleForm form = new StyleForm(style);

    ModelAndView stylesManager = new ModelAndView();
    BDDMockito.doReturn(stylesManager).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(Locale.class));
    BDDMockito.given(styleService.getStyle()).willReturn(style);

    ModelAndView result = displayFactory.computeModelAndViewForUpdateStyles(Locale.FRANCE);
    Assert.assertEquals(form.getContent(), ((StyleForm) result.getModel().get("styleForm")).getContent());
  }
}
