package com.cmpl.web.core.factory.page;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageCreateForm;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.page.PageUpdateForm;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageCreateForm;
import com.cmpl.web.core.widget.page.WidgetPageCreateFormBuilder;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageService;
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

public class DefaultPageManagerDisplayFactory extends AbstractBackDisplayFactory<PageDTO>
  implements PageManagerDisplayFactory {

  private final PageService pageService;

  private final WidgetService widgetService;

  private final WidgetPageService widgetPageService;

  private final WebsiteService websiteService;

  private static final String CREATE_FORM = "createForm";

  private static final String UPDATE_FORM = "updateForm";

  private static final String LINKABLE_WIDGETS = "linkableWidgets";

  private static final String LINKED_WIDGETS = "linkedWidgets";

  private static final String LINKED_WEBSITES = "linkedWebsites";

  private static final String LOCALES = "locales";

  public DefaultPageManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
    PageService pageService, ContextHolder contextHolder, WidgetService widgetService,
    WidgetPageService widgetPageService, PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService,
    WebsiteService websiteService,
    PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService,
      backPagesRegistry, contextHolder);

    this.pageService = Objects.requireNonNull(pageService);

    this.widgetService = Objects.requireNonNull(widgetService);
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.websiteService = Objects.requireNonNull(websiteService);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllPages(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("PAGE_VIEW");
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des pages pour la page {}", backPage.getPageName());

    PageWrapper<PageDTO> pagedPageDTOWrapped = computePageWrapper(locale, pageNumber, "");

    pagesManager.addObject("wrappedEntities", pagedPageDTOWrapped);

    return pagesManager;
  }

  @Override
  protected Page<PageDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<PageDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<PageDTO> pagedPageDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedPageDTOEntries = pageService.searchEntities(pageRequest, query);
    } else {
      pagedPageDTOEntries = pageService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedPageDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedPageDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedPageDTOEntries.getTotalElements());
  }

  PageCreateForm computeCreateForm() {
    return new PageCreateForm();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePage(Locale locale, String pageId,
    String personalizationLanguageCode) {
    BackPage backPage = computeBackPage("PAGE_UPDATE");
    ModelAndView pageManager = super.computeModelAndViewForBackPage(backPage, locale);

    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(page.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) pageManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreatePage(Locale locale) {
    BackPage backPage = computeBackPage("PAGE_CREATE");
    ModelAndView pageManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des pages ");
    pageManager.addObject(CREATE_FORM, computeCreateForm());
    return pageManager;
  }

  PageUpdateForm createUpdateForm(PageDTO page, String personalizationLanguageCode) {
    return new PageUpdateForm(page, personalizationLanguageCode);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMain(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_main");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageBody(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_body");
    pageManager.addObject(LOCALES, availableLocales);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));

    List<WidgetPageDTO> associatedWidgets = widgetPageService.findByPageId(Long.parseLong(pageId));
    pageManager.addObject(LINKED_WIDGETS, computeLinkedWidgets(associatedWidgets));

    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMeta(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_meta");
    pageManager.addObject(LOCALES, availableLocales);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));

    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageAMP(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_amp");
    pageManager.addObject(LOCALES, availableLocales);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));

    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePagePreview(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_preview");

    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));

    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageHeader(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_header");
    pageManager.addObject(LOCALES, availableLocales);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));

    List<WidgetPageDTO> associatedWidgets = widgetPageService.findByPageId(Long.parseLong(pageId));
    pageManager.addObject(LINKED_WIDGETS, computeLinkedWidgets(associatedWidgets));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageFooter(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_footer");
    pageManager.addObject(LOCALES, availableLocales);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page, personalizationLanguageCode));

    List<WidgetPageDTO> associatedWidgets = widgetPageService.findByPageId(Long.parseLong(pageId));
    pageManager.addObject(LINKED_WIDGETS, computeLinkedWidgets(associatedWidgets));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageWidgets(Locale locale, String pageId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_widgets");
    pageManager.addObject(LOCALES, availableLocales);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId), personalizationLanguageCode);
    pageManager.addObject(CREATE_FORM, computeWidgetPageCreateForm(page));

    return pageManager;
  }

  @Override
  public ModelAndView computeLinkedWidgets(String pageId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<WidgetDTO> linkedWidgets = widgetService
      .searchLinkedEntities(pageRequest, query, Long.parseLong(pageId));
    ModelAndView pageManager = new ModelAndView("back/pages/edit/linked_widgets");
    pageManager.addObject(LINKED_WIDGETS, linkedWidgets);
    return pageManager;
  }


  @Override
  public ModelAndView computeLinkedWebsites(String pageId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<WebsiteDTO> linkedWebsites = websiteService
      .searchLinkedEntities(pageRequest, query, Long.parseLong(pageId));
    ModelAndView pageManager = new ModelAndView("back/pages/edit/linked_websites");
    pageManager.addObject(LINKED_WEBSITES, linkedWebsites);
    return pageManager;
  }

  @Override
  public ModelAndView computeLinkableWidgets(String pageId, String query) {
    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<WidgetDTO> linkableWidgets = widgetService
      .searchNotLinkedEntities(pageRequest, query, Long.parseLong(pageId));
    ModelAndView pageManager = new ModelAndView("back/pages/edit/linkable_widgets");
    pageManager.addObject(LINKABLE_WIDGETS, linkableWidgets);
    return pageManager;
  }


  private List<WidgetDTO> computeLinkedWidgets(List<WidgetPageDTO> associatedWidgets) {
    List<WidgetDTO> linkedWidgets = new ArrayList<>();
    associatedWidgets.forEach(
      associatedWidget -> linkedWidgets
        .add(widgetService.getEntity(associatedWidget.getWidgetId())));
    return linkedWidgets;
  }

  WidgetPageCreateForm computeWidgetPageCreateForm(PageDTO page) {
    return WidgetPageCreateFormBuilder.create().pageId(String.valueOf(page.getId())).build();
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/pages";
  }

  @Override
  protected String getItemLink() {
    return "/manager/pages/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/pages/search";
  }

  @Override
  protected String getSearchPlaceHolder() {
    return "search.pages.placeHolder";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:pages:create";
  }

}
