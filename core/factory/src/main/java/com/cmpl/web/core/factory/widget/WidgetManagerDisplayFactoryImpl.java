package com.cmpl.web.core.factory.widget;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.widget.WIDGET_TYPE;
import com.cmpl.web.core.widget.WidgetCreateForm;
import com.cmpl.web.core.widget.WidgetCreateFormBuilder;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetDataSourceProvider;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.WidgetUpdateForm;
import com.cmpl.web.core.widget.WidgetUpdateFormBuilder;

public class WidgetManagerDisplayFactoryImpl extends
    AbstractBackDisplayFactoryImpl<WidgetDTO> implements WidgetManagerDisplayFactory {

  private final WidgetService widgetService;
  private final ContextHolder contextHolder;
  private final WidgetDataSourceProvider dataSourceProvider;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String LINKABLE_ENTITIES = "linkableEntities";
  private static final String WIDGET_TYPES = "widgetTypes";

  public WidgetManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
       ContextHolder contextHolder, WidgetService widgetService,  PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,WidgetDataSourceProvider widgetDataSourceProvider) {
    super(menuFactory, messageSource, breadCrumbRegistry);
    this.widgetService = widgetService;
    this.contextHolder = contextHolder;
    this.dataSourceProvider = widgetDataSourceProvider;
  }


  @Override
  public ModelAndView computeModelAndViewForViewAllWidgets(Locale locale, int pageNumber) {
    ModelAndView widgetsManager = super.computeModelAndViewForBackPage(BACK_PAGE.WIDGET_VIEW, locale);
    LOGGER.info("Construction des widgets pour la page " + BACK_PAGE.WIDGET_VIEW.name());

    PageWrapper<WidgetDTO> pagedWidgetDTOWrapped = computePageWrapper(locale, pageNumber);

    widgetsManager.addObject("wrappedWidgets", pagedWidgetDTOWrapped);

    return widgetsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateWidget(Locale locale) {
    ModelAndView widgetManager = super.computeModelAndViewForBackPage(BACK_PAGE.WIDGET_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des widgets ");
    widgetManager.addObject(CREATE_FORM, computeCreateForm());
    List<WIDGET_TYPE> types = Arrays.stream(WIDGET_TYPE.values()).collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);
    return widgetManager;
  }


  WidgetCreateForm computeCreateForm() {
    return WidgetCreateFormBuilder.create().build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidget(Locale locale,String widgetId) {
    ModelAndView widgetManager = super.computeModelAndViewForBackPage(BACK_PAGE.WIDGET_UPDATE, locale);

    WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId));
    LOGGER.info("Construction du formulaire de creation des widgets ");
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget));
    List<WIDGET_TYPE> types = Arrays.stream(WIDGET_TYPE.values()).collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(widget.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) widgetManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return widgetManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidgetMain(Locale locale, String widgetId) {
    ModelAndView widgetManager = new ModelAndView("back/widgets/edit/tab_main");
    WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId));
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget));
    List<WIDGET_TYPE> types = Arrays.stream(WIDGET_TYPE.values()).collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);
    return widgetManager;
  }

  WidgetUpdateForm computeUpdateForm(WidgetDTO widget){
    return WidgetUpdateFormBuilder.create().creationDate(widget.getCreationDate()).entityId(widget.getEntityId()).id(widget.getId()).personalization(widget.getPersonalization()).modificationDate(widget.getModificationDate()).name(widget.getName()).type(widget.getType()).build();
  }



  @Override
  public ModelAndView computeModelAndViewForUpdateWidgetPersonalization(Locale locale,String widgetId) {
    ModelAndView widgetManager = new ModelAndView("back/widgets/edit/tab_personalization");
    WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId));
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget));
    List<? extends BaseDTO> linkableEntities = dataSourceProvider.getLinkableEntities(widget.getType());
    widgetManager.addObject(LINKABLE_ENTITIES, linkableEntities);
    return widgetManager;
  }

  @Override
  protected String getBaseUrl() {
    return "manager/widgets";
  }

  @Override
  protected Page<WidgetDTO> computeEntries(Locale locale, int pageNumber) {
    List<WidgetDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(), new Sort(
        Direction.ASC,
        "name"));
    Page<WidgetDTO> pagedWidgetDTOEntries = widgetService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedWidgetDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedWidgetDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedWidgetDTOEntries.getTotalElements());
  }
}
