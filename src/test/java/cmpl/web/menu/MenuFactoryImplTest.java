package cmpl.web.menu;

import java.util.ArrayList;
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
import org.springframework.util.StringUtils;

import cmpl.web.builder.MenuItemBuilder;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.page.BACK_PAGE;
import cmpl.web.page.PAGES;

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

    BDDMockito.doReturn(index).when(menuFactory).computeIndexMenuElement(Mockito.eq(locale));
    BDDMockito.doReturn(index).when(menuFactory)
        .computeMenuItem(Mockito.any(BACK_PAGE.class), Mockito.any(BACK_MENU.class), Mockito.eq(locale));

    List<MenuItem> result = menuFactory.computeBackMenuItems(BACK_PAGE.NEWS_CREATE, locale);
    Assert.assertTrue(BACK_MENU.values().length + 1 == result.size());

  }

  @Test
  public void testComputeIndexMenuElement() {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();

    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    BDDMockito.doReturn(index).when(menuFactory).computeMenuItem(Mockito.eq(MENUS.INDEX), Mockito.eq(locale));

    MenuItem result = menuFactory.computeIndexMenuElement(locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertTrue(CollectionUtils.isEmpty(result.getSubMenuItems()));

  }

  @Test
  public void testComputeMenuItemSUB_MENULocale() {

    String href = "/";
    String label = "label";

    BDDMockito.doReturn(href).when(menuFactory)
        .getI18nValue(Mockito.eq(SUB_MENU.EXILIS_GENITAL.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(menuFactory)
        .getI18nValue(Mockito.eq(SUB_MENU.EXILIS_GENITAL.getLabel()), Mockito.eq(locale));

    MenuItem result = menuFactory.computeMenuItem(SUB_MENU.EXILIS_GENITAL, locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(label, result.getTitle());
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
  public void testComputeMenuItemMENULocale() throws Exception {
    String href = "/";
    String label = "label";
    String title = "title";

    BDDMockito.doReturn(href).when(menuFactory).getI18nValue(Mockito.eq(MENUS.INDEX.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(menuFactory).getI18nValue(Mockito.eq(MENUS.INDEX.getLabel()), Mockito.eq(locale));
    BDDMockito.doReturn(title).when(menuFactory).getI18nValue(Mockito.eq(MENUS.INDEX.getTitle()), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(menuFactory)
        .computeSubMenuItems(Mockito.eq(MENUS.INDEX), Mockito.eq(locale));

    MenuItem result = menuFactory.computeIndexMenuElement(locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertTrue(CollectionUtils.isEmpty(result.getSubMenuItems()));
  }

  @Test
  public void testComputeSubMenuItems_Empty() throws Exception {

    for (MENUS menu : MENUS.values()) {
      if (!MENUS.TECHNICS.equals(menu)) {
        List<MenuItem> result = menuFactory.computeSubMenuItems(MENUS.INDEX, locale);
        Assert.assertTrue(CollectionUtils.isEmpty(result));
      }
    }

  }

  @Test
  public void testComputeSubMenuItems_Not_Empty() throws Exception {

    BDDMockito.doReturn("test").when(menuFactory).getI18nValue(Mockito.anyString(), Mockito.eq(locale));

    List<MenuItem> result = menuFactory.computeSubMenuItems(MENUS.TECHNICS, locale);
    Assert.assertFalse(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testComputeMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    BDDMockito.doReturn(index).when(menuFactory).computeMenuItem(Mockito.any(MENUS.class), Mockito.eq(locale));

    List<MenuItem> result = menuFactory.computeMenuItems(PAGES.INDEX, locale);
    Assert.assertTrue(MENUS.values().length == result.size());
  }

  @Test
  public void testComputeCustomCssClass_PAGE_active() {

    String result = menuFactory.computeCustomCssClass(PAGES.INDEX, MENUS.INDEX);
    Assert.assertEquals("active", result);
  }

  @Test
  public void testComputeCustomCssClass_PAGE_empty() {

    String result = menuFactory.computeCustomCssClass(PAGES.APPOINTMENT, MENUS.INDEX);
    Assert.assertTrue(!StringUtils.hasText(result));
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
