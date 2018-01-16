package com.cmpl.web.facebook;

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.menu.MenuItem;
import com.cmpl.web.menu.MenuItemBuilder;
import com.cmpl.web.page.BACK_PAGE;

;

@RunWith(MockitoJUnitRunner.class)
public class FacebookDisplayFactoryImplTest {

  @Mock
  private FacebookService facebookService;

  @InjectMocks
  @Spy
  private FacebookDisplayFactoryImpl facebookDisplayFactoryImpl;

  @Test
  public void testIsAlreadyConnected_True() throws Exception {

    boolean result = facebookDisplayFactoryImpl.isAlreadyConnected();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyConnected_False() throws Exception {

    BDDMockito.doThrow(new BaseException()).when(facebookService).getRecentFeed();

    boolean result = facebookDisplayFactoryImpl.isAlreadyConnected();

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeRecentFeeds_Ok() throws Exception {

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Lists.newArrayList(post);

    BDDMockito.doReturn(postsToReturn).when(facebookService).getRecentFeed();

    List<ImportablePost> result = facebookDisplayFactoryImpl.computeRecentFeeds();

    Assert.assertEquals(postsToReturn, result);

  }

  @Test
  public void testComputeRecentFeeds_Exception_Should_Return_Empty_Array() throws Exception {

    BDDMockito.doThrow(new BaseException()).when(facebookService).getRecentFeed();

    List<ImportablePost> result = facebookDisplayFactoryImpl.computeRecentFeeds();

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Not_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookAccessPage(Locale.FRANCE);
    Assert.assertEquals("login", result.getModel().get("content"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeModelAndViewForFacebookImportPage(
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeRecentFeeds();

  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Lists.newArrayList(post);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(postsToReturn).when(facebookDisplayFactoryImpl).computeRecentFeeds();
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookAccessPage(Locale.FRANCE);

    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeRecentFeeds();
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeModelAndViewForFacebookImportPage(
        BDDMockito.eq(Locale.FRANCE));
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Not_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookImportPage(Locale.FRANCE);
    Assert.assertEquals("login", result.getModel().get("content"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeModelAndViewForFacebookAccessPage(
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeRecentFeeds();
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Lists.newArrayList(index, news);

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Lists.newArrayList(post);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(postsToReturn).when(facebookDisplayFactoryImpl).computeRecentFeeds();
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookImportPage(Locale.FRANCE);

    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeRecentFeeds();
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeModelAndViewForFacebookAccessPage(
        BDDMockito.eq(Locale.FRANCE));
  }
}
