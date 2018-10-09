package com.cmpl.web.core.factory.website;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.design.DesignCreateForm;
import com.cmpl.web.core.design.DesignCreateFormBuilder;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.sitemap.SitemapCreateForm;
import com.cmpl.web.core.sitemap.SitemapCreateFormBuilder;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.website.WebsiteCreateForm;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.website.WebsiteUpdateForm;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class DefaultWebsiteManagerDisplayFactory extends AbstractBackDisplayFactory<WebsiteDTO>
  implements WebsiteManagerDisplayFactory {

  private final WebsiteService websiteService;


  private final PageService pageService;

  private final StyleService styleService;

  private static final String LINKABLE_STYLES = "linkableStyles";

  private static final String LINKED_STYLES = "linkedStyles";

  private static final String LINKABLE_PAGES = "linkablePages";

  private static final String LINKED_PAGES = "linkedPages";

  public DefaultWebsiteManagerDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource,
    PluginRegistry<BreadCrumb, String> breadCrumbRegistry, Set<Locale> availableLocales,
    GroupService groupService,
    MembershipService membershipService, ContextHolder contextHolder, WebsiteService websiteService,
    PageService pageService,
    StyleService styleService,
    PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService,
      backPagesRegistry, contextHolder);
    this.websiteService = Objects.requireNonNull(websiteService);
    this.pageService = Objects.requireNonNull(pageService);
    this.styleService = Objects.requireNonNull(styleService);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllWebsites(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("WEBSITE_VIEW");
    ModelAndView websitesManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des websites pour la page {} ", backPage.getPageName());

    PageWrapper<WebsiteDTO> pagedWebsiteDTOWrapped = computePageWrapper(locale, pageNumber, "");

    websitesManager.addObject("wrappedEntities", pagedWebsiteDTOWrapped);

    return websitesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateWebsite(Locale locale) {
    BackPage backPage = computeBackPage("WEBSITE_CREATE");
    ModelAndView websiteManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des wesbites");

    WebsiteCreateForm form = new WebsiteCreateForm();

    websiteManager.addObject("createForm", form);

    return websiteManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsite(Locale locale, String websiteId) {
    BackPage backPage = computeBackPage("WEBSITE_UPDATE");
    ModelAndView websiteManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du website pour la page {} ", backPage.getPageName());
    WebsiteDTO website = websiteService.getEntity(Long.parseLong(websiteId));
    WebsiteUpdateForm form = new WebsiteUpdateForm(website);

    websiteManager.addObject("updateForm", form);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(website.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) websiteManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return websiteManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsiteMain(Locale locale, String websiteId) {
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/tab_main");
    WebsiteDTO website = websiteService.getEntity(Long.parseLong(websiteId));
    WebsiteUpdateForm form = new WebsiteUpdateForm(website);

    websiteManager.addObject("updateForm", form);
    return websiteManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsiteSitemap(Locale locale, String websiteId) {
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/tab_sitemap");

    SitemapCreateForm createForm = SitemapCreateFormBuilder.create().websiteId(websiteId).build();
    websiteManager.addObject("createForm", createForm);
    return websiteManager;

  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsiteDesign(Locale locale, String websiteId) {
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/tab_design");

    DesignCreateForm createForm = DesignCreateFormBuilder.create().websiteId(websiteId).build();
    websiteManager.addObject("createForm", createForm);
    return websiteManager;
  }

  @Override
  public ModelAndView computeLinkedStyles(String websiteId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<StyleDTO> linkedStyles = styleService
      .searchLinkedEntities(pageRequest, query, Long.parseLong(websiteId));
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/linked_styles");
    websiteManager.addObject(LINKED_STYLES, linkedStyles);
    return websiteManager;
  }

  @Override
  public ModelAndView computeLinkableStyles(String websiteId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<StyleDTO> linkableStyles = styleService
      .searchNotLinkedEntities(pageRequest, query, Long.parseLong(websiteId));
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/linkable_styles");
    websiteManager.addObject(LINKABLE_STYLES, linkableStyles);
    return websiteManager;
  }

  @Override
  public ModelAndView computeLinkedPages(String websiteId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<PageDTO> linkedPages = pageService
      .searchLinkedEntities(pageRequest, query, Long.parseLong(websiteId));
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/linked_pages");
    websiteManager.addObject(LINKED_PAGES, linkedPages);
    return websiteManager;
  }

  @Override
  public ModelAndView computeLinkablePages(String websiteId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<PageDTO> linkablePages = pageService
      .searchNotLinkedEntities(pageRequest, query, Long.parseLong(websiteId));
    ModelAndView websiteManager = new ModelAndView("back/websites/edit/linkable_pages");
    websiteManager.addObject(LINKABLE_PAGES, linkablePages);
    return websiteManager;
  }

  @Override
  protected Page<WebsiteDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<WebsiteDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Sort.Direction.ASC, "name"));
    Page<WebsiteDTO> pagedWebsiteDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedWebsiteDTOEntries = websiteService.searchEntities(pageRequest, query);
    } else {
      pagedWebsiteDTOEntries = websiteService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedWebsiteDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedWebsiteDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedWebsiteDTOEntries.getTotalElements());
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/websites";
  }

  @Override
  protected String getItemLink() {
    return "/manager/websites/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/websites/search";
  }

  @Override
  protected String getSearchPlaceHolder() {
    return "search.websites.placeHolder";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:websites:create";
  }
}
