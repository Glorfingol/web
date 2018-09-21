package com.cmpl.web.modules.facebook.factory;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.ImportablePost;
import com.cmpl.web.facebook.ImportablePostBuilder;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFacebookDisplayFactoryTest {

  @Mock
  private FacebookAdapter facebookAdapter;

  @Mock
  private PluginRegistry<BreadCrumb, String> breadCrumbRegistry;

  @Mock
  private PluginRegistry<BackPage, String> backPages;

  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private MembershipService membershipService;

  @Mock
  private GroupService groupService;

  @InjectMocks
  @Spy
  private DefaultFacebookDisplayFactory defaultFacebookDisplayFactory;

  @Test
  public void testIsAlreadyConnected_True() throws Exception {

    BDDMockito.doReturn(true).when(facebookAdapter).isAlreadyConnected();
    boolean result = defaultFacebookDisplayFactory.isAlreadyConnected();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyConnected_False() throws Exception {

    BDDMockito.doReturn(false).when(facebookAdapter).isAlreadyConnected();

    boolean result = defaultFacebookDisplayFactory.isAlreadyConnected();

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeRecentFeeds_Ok() throws Exception {

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Arrays.asList(post);

    BDDMockito.doReturn(postsToReturn).when(facebookAdapter).getRecentFeed();

    List<ImportablePost> result = defaultFacebookDisplayFactory.computeRecentFeeds();

    Assert.assertEquals(postsToReturn, result);

  }

  @Test
  public void testComputeRecentFeeds_Exception_Should_Return_Empty_Array() throws Exception {

    BDDMockito.doReturn(Arrays.asList()).when(facebookAdapter).getRecentFeed();

    List<ImportablePost> result = defaultFacebookDisplayFactory.computeRecentFeeds();

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Not_Connected() throws Exception {

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
    BDDMockito.doReturn(breadcrumb).when(defaultFacebookDisplayFactory)
        .computeBreadCrumb(BDDMockito.any(BackPage.class));

    BDDMockito.doReturn(backMenu).when(defaultFacebookDisplayFactory)
        .computeBackMenuItems(BDDMockito.any(BackPage.class),
            BDDMockito.eq(Locale.FRANCE));

    BDDMockito.doReturn(false).when(defaultFacebookDisplayFactory).isAlreadyConnected();
    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath")
        .titleKey("some.key").decorated(true).build();
    BDDMockito.doReturn(backPage).when(defaultFacebookDisplayFactory)
        .computeBackPage(BDDMockito.anyString());

    ModelAndView result = defaultFacebookDisplayFactory
        .computeModelAndViewForFacebookAccessPage(Locale.FRANCE);
    Assert.assertEquals(backPage.getTemplatePath(), result.getModel().get("content"));

    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(0))
        .computeModelAndViewForFacebookImportPage(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(0)).computeRecentFeeds();

  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Connected() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Arrays.asList(post);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(defaultFacebookDisplayFactory)
        .computeBreadCrumb(BDDMockito.any(BackPage.class));
    BDDMockito.doReturn(postsToReturn).when(defaultFacebookDisplayFactory).computeRecentFeeds();

    BDDMockito.doReturn(backMenu).when(defaultFacebookDisplayFactory)
        .computeBackMenuItems(BDDMockito.any(BackPage.class),
            BDDMockito.eq(Locale.FRANCE));

    BDDMockito.doReturn(true).when(defaultFacebookDisplayFactory).isAlreadyConnected();
    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath")
        .titleKey("some.key").decorated(true).build();
    BDDMockito.doReturn(backPage).when(defaultFacebookDisplayFactory)
        .computeBackPage(BDDMockito.anyString());

    ModelAndView result = defaultFacebookDisplayFactory
        .computeModelAndViewForFacebookAccessPage(Locale.FRANCE);

    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(1)).computeRecentFeeds();
    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(1))
        .computeModelAndViewForFacebookImportPage(BDDMockito.eq(Locale.FRANCE));
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Not_Connected() throws Exception {

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
    BDDMockito.doReturn(breadcrumb).when(defaultFacebookDisplayFactory)
        .computeBreadCrumb(BDDMockito.any(BackPage.class));

    BDDMockito.doReturn(backMenu).when(defaultFacebookDisplayFactory)
        .computeBackMenuItems(BDDMockito.any(BackPage.class),
            BDDMockito.eq(Locale.FRANCE));

    BDDMockito.doReturn(false).when(defaultFacebookDisplayFactory).isAlreadyConnected();
    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath")
        .titleKey("some.key").decorated(true).build();
    BDDMockito.doReturn(backPage).when(defaultFacebookDisplayFactory)
        .computeBackPage(BDDMockito.anyString());

    ModelAndView result = defaultFacebookDisplayFactory
        .computeModelAndViewForFacebookImportPage(Locale.FRANCE);

    Assert.assertEquals(backPage.getTemplatePath(), result.getModel().get("content"));

    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(1))
        .computeModelAndViewForFacebookAccessPage(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(0)).computeRecentFeeds();
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Connected() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";

    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title)
        .subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Arrays.asList(post);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(defaultFacebookDisplayFactory)
        .computeBreadCrumb(BDDMockito.any(BackPage.class));
    BDDMockito.doReturn(postsToReturn).when(defaultFacebookDisplayFactory).computeRecentFeeds();

    BDDMockito.doReturn(backMenu).when(defaultFacebookDisplayFactory)
        .computeBackMenuItems(BDDMockito.any(BackPage.class),
            BDDMockito.eq(Locale.FRANCE));

    BackPage backPage = BackPageBuilder.create().pageName("test").templatePath("somePath")
        .titleKey("some.key").decorated(true).build();
    BDDMockito.doReturn(backPage).when(defaultFacebookDisplayFactory)
        .computeBackPage(BDDMockito.anyString());

    BDDMockito.doReturn(true).when(defaultFacebookDisplayFactory).isAlreadyConnected();

    ModelAndView result = defaultFacebookDisplayFactory
        .computeModelAndViewForFacebookImportPage(Locale.FRANCE);

    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(1)).computeRecentFeeds();
    BDDMockito.verify(defaultFacebookDisplayFactory, BDDMockito.times(0))
        .computeModelAndViewForFacebookAccessPage(BDDMockito.eq(Locale.FRANCE));
  }
}
