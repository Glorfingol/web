package com.cmpl.web.core.factory.menu;

import com.cmpl.web.core.common.message.DefaultWebMessageSource;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMenuFactoryTest {

  @Mock
  private DefaultWebMessageSource messageSource;

  @InjectMocks
  @Spy
  private DefaultMenuFactory menuFactory;


  @Mock
  private BackMenu backMenu;

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label).title(title).build();

    BDDMockito.given(backMenu.getItems()).willReturn(Arrays.asList(item));
    BDDMockito.doReturn(index).when(menuFactory).computeMenuItem(BDDMockito.any(BackPage.class),
        BDDMockito.any(BackMenuItem.class), BDDMockito.eq(Locale.FRANCE), BDDMockito.anyList(),
        BDDMockito.anyList());

    List<MenuItem> result = menuFactory.computeBackMenuItems(new BackPage(), Locale.FRANCE);
    Assert.assertTrue(index == result.get(0));

  }

  @Test
  public void testComputeMenuItem() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label)
        .title("title")
        .build();

    BDDMockito.doReturn(label).when(menuFactory).getI18nValue(BDDMockito.eq(item.getLabel()),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(menuFactory).getI18nValue(BDDMockito.eq(item.getTitle()),
        BDDMockito.eq(Locale.FRANCE));

    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath")
        .titleKey("title").decorated(true).build();

    MenuItem result = menuFactory
        .computeMenuItem(backPage, item, Locale.FRANCE);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertEquals("active", result.getCustomCssClass());

  }

  @Test
  public void testComputeCustomCssClass_BackPage_active() {

    String href = "/";
    String label = "label";

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label)
        .title("title")
        .build();

    String result = menuFactory
        .computeCustomCssClass(BackPageBuilder.create().titleKey("title").build(), item);
    Assert.assertEquals("active", result);
  }

  @Test
  public void testComputeCustomCssClass_BackPage_empty() {

    String href = "/";
    String label = "label";

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label)
        .title("some.key")
        .build();

    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath")
        .titleKey("some.other.key").decorated(true).build();

    String result = menuFactory
        .computeCustomCssClass(backPage, item);
    Assert.assertTrue(!StringUtils.hasText(result));
  }


}
