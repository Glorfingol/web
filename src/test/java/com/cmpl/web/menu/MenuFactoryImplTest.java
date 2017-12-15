package com.cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.springframework.util.StringUtils;

import com.cmpl.web.builder.MenuItemBuilder;
import com.cmpl.web.menu.BACK_MENU;
import com.cmpl.web.menu.MenuFactoryImpl;
import com.cmpl.web.menu.MenuItem;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class MenuFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private MenuFactoryImpl menuFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    BDDMockito.doReturn(index).when(menuFactory)
        .computeMenuItem(Mockito.any(BACK_PAGE.class), Mockito.any(BACK_MENU.class), Mockito.eq(locale));

    List<MenuItem> result = menuFactory.computeBackMenuItems(BACK_PAGE.NEWS_CREATE, locale);
    Assert.assertTrue(BACK_MENU.values().length == result.size());

  }

  @Test
  public void testComputeMenuItemBACK_MENULocale() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";

    BDDMockito.doReturn(href).when(menuFactory)
        .getI18nValue(Mockito.eq(BACK_MENU.BACK_NEWS.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(menuFactory)
        .getI18nValue(Mockito.eq(BACK_MENU.BACK_NEWS.getLabel()), Mockito.eq(locale));
    BDDMockito.doReturn(title).when(menuFactory)
        .getI18nValue(Mockito.eq(BACK_MENU.BACK_NEWS.getTitle()), Mockito.eq(locale));

    MenuItem result = menuFactory.computeMenuItem(BACK_PAGE.NEWS_CREATE, BACK_MENU.BACK_NEWS, locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertEquals("active", result.getCustomCssClass());

  }

  @Test
  public void testComputeCustomCssClass_BACK_PAGE_active() {

    String result = menuFactory.computeCustomCssClass(BACK_PAGE.NEWS_CREATE, BACK_MENU.BACK_NEWS);
    Assert.assertEquals("active", result);
  }

  @Test
  public void testComputeCustomCssClass_BACK_PAGE_empty() {

    String result = menuFactory.computeCustomCssClass(BACK_PAGE.LOGIN, BACK_MENU.BACK_NEWS);
    Assert.assertTrue(!StringUtils.hasText(result));
  }
}
