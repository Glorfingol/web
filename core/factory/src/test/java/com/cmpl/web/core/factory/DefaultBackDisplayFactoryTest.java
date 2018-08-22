package com.cmpl.web.core.factory;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.common.message.DefaultWebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.page.BackPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class DefaultBackDisplayFactoryTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private MembershipService membershipService;

  @Mock
  private GroupService groupService;

  @Mock
  private DefaultWebMessageSource messageSource;

  @Mock
  private PluginRegistry<BreadCrumb, String> breadCrumbRegistry;

  @Mock
  private PluginRegistry<BackPage, String> backPages;

  @Mock
  private Set<Locale> availableLocales;

  @InjectMocks
  @Spy
  private DefaultBackDisplayFactory displayFactory;

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);
    BDDMockito.given(menuFactory
        .computeBackMenuItems(BDDMockito.any(BackPage.class), BDDMockito.eq(Locale.FRANCE)))
        .willReturn(backMenu);

    List<MenuItem> result = displayFactory.computeBackMenuItems(new BackPage(), Locale.FRANCE);
    Assert.assertEquals(backMenu, result);
  }

  @Test
  public void testComputeModelAndViewForBackPage() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory)
        .computeBreadCrumb(BDDMockito.any(BackPage.class));

    BDDMockito.doReturn(backMenu).when(displayFactory)
        .computeBackMenuItems(BDDMockito.any(BackPage.class),
            BDDMockito.eq(Locale.FRANCE));

    ModelAndView result = displayFactory
        .computeModelAndViewForBackPage(new BackPage(), Locale.FRANCE);

    Assert.assertEquals(backMenu, result.getModel().get("menuItems"));

    BDDMockito.verify(displayFactory, BDDMockito.times(1))
        .computeBackMenuItems(BDDMockito.any(BackPage.class),
            BDDMockito.eq(Locale.FRANCE));

  }

}
