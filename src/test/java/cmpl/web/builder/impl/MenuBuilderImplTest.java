package cmpl.web.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.menu.BACK_MENU;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.menu.SUB_MENU;

@RunWith(MockitoJUnitRunner.class)
public class MenuBuilderImplTest {

  private WebMessageSourceImpl messageBundle;

  private MenuBuilderImpl menuBuilder;

  private Locale locale;

  @Before
  public void setUp() {
    messageBundle = Mockito.mock(WebMessageSourceImpl.class);
    menuBuilder = MenuBuilderImpl.fromMessageSource(messageBundle);
    menuBuilder = Mockito.spy(menuBuilder);
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    BDDMockito.doReturn(index).when(menuBuilder).computeIndexMenuElement(Mockito.eq(locale));
    BDDMockito.doReturn(index).when(menuBuilder).computeMenuItem(Mockito.any(BACK_MENU.class), Mockito.eq(locale));

    List<MenuItem> result = menuBuilder.computeBackMenuItems(locale);
    Assert.assertTrue(BACK_MENU.values().length + 1 == result.size());

  }

  @Test
  public void testComputeIndexMenuElement() {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();

    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    BDDMockito.doReturn(index).when(menuBuilder).computeMenuItem(Mockito.eq(MENU.INDEX), Mockito.eq(locale));

    MenuItem result = menuBuilder.computeIndexMenuElement(locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertTrue(CollectionUtils.isEmpty(result.getSubMenuItems()));

  }

  @Test
  public void testComputeMenuItemSUB_MENULocale() {

    String href = "/";
    String label = "label";

    BDDMockito.doReturn(href).when(menuBuilder)
        .getI18nValue(Mockito.eq(SUB_MENU.EXILIS_GENITAL.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(menuBuilder)
        .getI18nValue(Mockito.eq(SUB_MENU.EXILIS_GENITAL.getLabel()), Mockito.eq(locale));

    MenuItem result = menuBuilder.computeMenuItem(SUB_MENU.EXILIS_GENITAL, locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(label, result.getTitle());
  }

  @Test
  public void testComputeMenuItemBACK_MENULocale() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";

    BDDMockito.doReturn(href).when(menuBuilder)
        .getI18nValue(Mockito.eq(BACK_MENU.BACK_NEWS.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(menuBuilder)
        .getI18nValue(Mockito.eq(BACK_MENU.BACK_NEWS.getLabel()), Mockito.eq(locale));
    BDDMockito.doReturn(title).when(menuBuilder)
        .getI18nValue(Mockito.eq(BACK_MENU.BACK_NEWS.getTitle()), Mockito.eq(locale));

    MenuItem result = menuBuilder.computeMenuItem(BACK_MENU.BACK_NEWS, locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());

  }

  @Test
  public void testComputeMenuItemMENULocale() throws Exception {
    String href = "/";
    String label = "label";
    String title = "title";

    BDDMockito.doReturn(href).when(menuBuilder).getI18nValue(Mockito.eq(MENU.INDEX.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(label).when(menuBuilder).getI18nValue(Mockito.eq(MENU.INDEX.getLabel()), Mockito.eq(locale));
    BDDMockito.doReturn(title).when(menuBuilder).getI18nValue(Mockito.eq(MENU.INDEX.getTitle()), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList()).when(menuBuilder)
        .computeSubMenuItems(Mockito.eq(MENU.INDEX), Mockito.eq(locale));

    MenuItem result = menuBuilder.computeIndexMenuElement(locale);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertTrue(CollectionUtils.isEmpty(result.getSubMenuItems()));
  }

  @Test
  public void testComputeSubMenuItems_Empty() throws Exception {

    for (MENU menu : MENU.values()) {
      if (!MENU.TECHNICS.equals(menu)) {
        List<MenuItem> result = menuBuilder.computeSubMenuItems(MENU.INDEX, locale);
        Assert.assertTrue(CollectionUtils.isEmpty(result));
      }
    }

  }

  @Test
  public void testComputeSubMenuItems_Not_Empty() throws Exception {

    BDDMockito.doReturn("test").when(menuBuilder).getI18nValue(Mockito.anyString(), Mockito.eq(locale));

    List<MenuItem> result = menuBuilder.computeSubMenuItems(MENU.TECHNICS, locale);
    Assert.assertFalse(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testComputeMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).toMenuItem();

    BDDMockito.doReturn(index).when(menuBuilder).computeMenuItem(Mockito.any(MENU.class), Mockito.eq(locale));

    List<MenuItem> result = menuBuilder.computeMenuItems(locale);
    Assert.assertTrue(MENU.values().length == result.size());
  }
}
