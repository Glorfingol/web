package com.cmpl.web.core.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.menu.MenuItem;
import com.cmpl.web.menu.MenuItemBuilder;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class BackDisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private BackDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);
    BDDMockito.given(menuFactory.computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE)))
        .willReturn(backMenu);

    List<MenuItem> result = displayFactory.computeBackMenuItems(BACK_PAGE.LOGIN, Locale.FRANCE);
    Assert.assertEquals(backMenu, result);
  }

  @Test
  public void testComputeModelAndViewForBackPage() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = new MenuItemBuilder().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    BDDMockito.doReturn(tile).when(displayFactory)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(displayFactory).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));

    ModelAndView result = displayFactory.computeModelAndViewForBackPage(BACK_PAGE.LOGIN, Locale.FRANCE);

    Assert.assertEquals(tile, result.getViewName());

    Assert.assertEquals(backMenu, result.getModel().get("menuItems"));
    Assert.assertEquals(href, result.getModel().get("hiddenLink"));

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeTileName(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeBackMenuItems(BDDMockito.any(BACK_PAGE.class),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
  }

}
