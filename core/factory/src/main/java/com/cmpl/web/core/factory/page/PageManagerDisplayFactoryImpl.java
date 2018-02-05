package com.cmpl.web.core.factory.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import com.cmpl.web.core.meta.MetaElementCreateForm;
import com.cmpl.web.core.meta.MetaElementDTO;
import com.cmpl.web.core.meta.MetaElementService;
import com.cmpl.web.core.meta.OpenGraphMetaElementCreateForm;
import com.cmpl.web.core.meta.OpenGraphMetaElementDTO;
import com.cmpl.web.core.meta.OpenGraphMetaElementService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageCreateForm;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.page.PageUpdateForm;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetPageCreateForm;
import com.cmpl.web.core.widget.WidgetPageCreateFormBuilder;
import com.cmpl.web.core.widget.WidgetPageDTO;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

public class PageManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<PageDTO> implements
    PageManagerDisplayFactory {

  private final PageService pageService;
  private final OpenGraphMetaElementService openGraphMetaElementService;
  private final MetaElementService metaElementService;
  private final WidgetService widgetService;
  private final WidgetPageService widgetPageService;
  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String META_ELEMENTS = "metaElements";
  private static final String LINKABLE_WIDGETS = "linkableWidgets";
  private static final String LINKED_WIDGETS = "linkedWidgets";

  public PageManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PageService pageService, ContextHolder contextHolder, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, WidgetService widgetService,
      WidgetPageService widgetPageService, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry);
    this.pageService = pageService;
    this.contextHolder = contextHolder;
    this.metaElementService = metaElementService;
    this.openGraphMetaElementService = openGraphMetaElementService;
    this.widgetService = widgetService;
    this.widgetPageService = widgetPageService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllPages(Locale locale, int pageNumber) {
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(BACK_PAGE.PAGES_VIEW, locale);
    LOGGER.info("Construction des pages pour la page {}", BACK_PAGE.PAGES_VIEW.name());

    PageWrapper<PageDTO> pagedPageDTOWrapped = computePageWrapper(locale, pageNumber);

    pagesManager.addObject("wrappedPages", pagedPageDTOWrapped);

    return pagesManager;
  }

  @Override
  protected Page<PageDTO> computeEntries(Locale locale, int pageNumber) {
    List<PageDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(), new Sort(Direction.ASC,
        "name"));
    Page<PageDTO> pagedPageDTOEntries = pageService.getPagedEntities(pageRequest);
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
  public ModelAndView computeModelAndViewForUpdatePage(Locale locale, String pageId) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(BACK_PAGE.PAGES_UPDATE, locale);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(page.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) pageManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreatePage(Locale locale) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(BACK_PAGE.PAGES_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des pages ");
    pageManager.addObject(CREATE_FORM, computeCreateForm());
    return pageManager;
  }

  PageUpdateForm createUpdateForm(PageDTO page) {
    return new PageUpdateForm(page);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMain(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_main");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageBody(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_body");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMeta(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_meta");
    List<MetaElementDTO> metaElements = metaElementService.findMetaElementsByPageId(pageId);
    pageManager.addObject(META_ELEMENTS, metaElements);
    pageManager.addObject(CREATE_FORM, createMetaElementCreateForm(pageId));
    return pageManager;
  }

  MetaElementCreateForm createMetaElementCreateForm(String pageId) {
    MetaElementCreateForm createForm = new MetaElementCreateForm();
    createForm.setPageId(pageId);
    return createForm;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageOpenGraphMeta(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_open_graph_meta");
    List<OpenGraphMetaElementDTO> metaElements = openGraphMetaElementService.findOpenGraphMetaElementsByPageId(pageId);
    pageManager.addObject(META_ELEMENTS, metaElements);
    pageManager.addObject(CREATE_FORM, createOpenGraphMetaElementCreateForm(pageId));
    return pageManager;
  }

  OpenGraphMetaElementCreateForm createOpenGraphMetaElementCreateForm(String pageId) {
    OpenGraphMetaElementCreateForm createForm = new OpenGraphMetaElementCreateForm();
    createForm.setPageId(pageId);
    return createForm;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageHeader(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_header");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageFooter(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_footer");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(UPDATE_FORM, createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageWidgets(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_widgets");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject(CREATE_FORM, computeWidgetPageCreateForm(page));
    List<WidgetPageDTO> associatedWidgets = widgetPageService.findByPageId(pageId);
    List<WidgetDTO> widgets = widgetService.getEntities();
    List<WidgetDTO> linkableWidgets = computeLinkableWidgets(associatedWidgets, widgets);

    pageManager.addObject(LINKABLE_WIDGETS, linkableWidgets);
    pageManager.addObject(LINKED_WIDGETS, computeLinkedWidgets(associatedWidgets));
    return pageManager;
  }

  private List<WidgetDTO> computeLinkableWidgets(List<WidgetPageDTO> associatedWidgets, List<WidgetDTO> widgets) {
    List<WidgetDTO> linkableWidgets = new ArrayList<>();
    if (CollectionUtils.isEmpty(associatedWidgets)) {
      linkableWidgets.addAll(widgets);
    } else {
      List<String> associatedWidgetsIds = associatedWidgets.stream()
          .map(associatedWidget -> associatedWidget.getWidgetId()).collect(Collectors.toList());
      widgets.forEach(widget -> {
        if (!associatedWidgetsIds.contains(String.valueOf(widget.getId()))) {
          linkableWidgets.add(widget);
        }
      });
    }
    return linkableWidgets;
  }

  private List<WidgetDTO> computeLinkedWidgets(List<WidgetPageDTO> associatedWidgets) {
    List<WidgetDTO> linkedWidgets = new ArrayList<>();
    associatedWidgets.forEach(associatedWidget -> linkedWidgets.add(widgetService.getEntity(Long
        .parseLong(associatedWidget.getWidgetId()))));
    return linkedWidgets;
  }

  WidgetPageCreateForm computeWidgetPageCreateForm(PageDTO page) {
    return WidgetPageCreateFormBuilder.create().pageId(String.valueOf(page.getId())).build();
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/pages";
  }

}
