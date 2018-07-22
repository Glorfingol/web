package com.cmpl.web.core.factory.website;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.website.WebsiteCreateForm;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.website.WebsiteUpdateForm;

public class WebsiteManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<WebsiteDTO>
    implements WebsiteManagerDisplayFactory {

  private final WebsiteService websiteService;
  private final ContextHolder contextHolder;

  public WebsiteManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService, ContextHolder contextHolder, WebsiteService websiteService) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService, membershipService);
    this.websiteService = Objects.requireNonNull(websiteService);
    this.contextHolder = Objects.requireNonNull(contextHolder);
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllWebsites(Locale locale, int pageNumber) {
    ModelAndView websitesManager = super.computeModelAndViewForBackPage(BACK_PAGE.WEBSITE_VIEW, locale);
    LOGGER.info("Construction des websites pour la page {} ", BACK_PAGE.WEBSITE_VIEW.name());

    PageWrapper<WebsiteDTO> pagedWebsiteDTOWrapped = computePageWrapper(locale, pageNumber);

    websitesManager.addObject("wrappedWebsites", pagedWebsiteDTOWrapped);

    return websitesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateWebsite(Locale locale) {
    ModelAndView websiteManager = super.computeModelAndViewForBackPage(BACK_PAGE.WEBSITE_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des wesbites");

    WebsiteCreateForm form = new WebsiteCreateForm();

    websiteManager.addObject("createForm", form);

    return websiteManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsite(Locale locale, String websiteId) {
    ModelAndView websiteManager = super.computeModelAndViewForBackPage(BACK_PAGE.WEBSITE_UPDATE, locale);
    LOGGER.info("Construction du website pour la page {} ", BACK_PAGE.WEBSITE_UPDATE.name());
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
    LOGGER.info("Construction du website pour la page {} ", BACK_PAGE.WEBSITE_UPDATE.name());
    WebsiteDTO website = websiteService.getEntity(Long.parseLong(websiteId));
    WebsiteUpdateForm form = new WebsiteUpdateForm(website);

    websiteManager.addObject("updateForm", form);
    return websiteManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsiteSitemap(Locale locale, String websiteId) {
    return null;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWebsiteDesign(Locale locale, String websiteId) {
    return null;
  }

  @Override
  protected Page<WebsiteDTO> computeEntries(Locale locale, int pageNumber) {
    List<WebsiteDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
        new Sort(Sort.Direction.ASC, "name"));
    Page<WebsiteDTO> pagedWebsiteDTOEntries = websiteService.getPagedEntities(pageRequest);
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
  protected String getCreateItemPrivilege() {
    return "webmastering:websites:create";
  }
}
