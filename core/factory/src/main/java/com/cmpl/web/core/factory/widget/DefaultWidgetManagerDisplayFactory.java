package com.cmpl.web.core.factory.widget;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetCreateForm;
import com.cmpl.web.core.widget.WidgetCreateFormBuilder;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.WidgetUpdateForm;
import com.cmpl.web.core.widget.WidgetUpdateFormBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class DefaultWidgetManagerDisplayFactory extends AbstractBackDisplayFactory<WidgetDTO>
  implements WidgetManagerDisplayFactory {

  private final WidgetService widgetService;

  private final PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  private static final String CREATE_FORM = "createForm";

  private static final String UPDATE_FORM = "updateForm";

  private static final String LINKABLE_ENTITIES = "linkableEntities";

  private static final String WIDGET_TYPES = "widgetTypes";

  private static final String LOCALES = "locales";

  private static final String TOOLTIP_KEY = "tooltipKey";

  private static final String MACROS_KEY = "macros";

  public DefaultWidgetManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
    ContextHolder contextHolder, WidgetService widgetService,
    PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    PluginRegistry<WidgetProviderPlugin, String> widgetProviders, Set<Locale> availableLocales,
    GroupService groupService, MembershipService membershipService,
    PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService, backPagesRegistry, contextHolder);
    this.widgetService = Objects.requireNonNull(widgetService);

    this.widgetProviders = Objects.requireNonNull(widgetProviders);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllWidgets(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("WIDGET_VIEW");
    ModelAndView widgetsManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des widgets pour la page {} ", backPage.getPageName());

    PageWrapper<WidgetDTO> pagedWidgetDTOWrapped = computePageWrapper(locale, pageNumber, "");

    widgetsManager.addObject("wrappedEntities", pagedWidgetDTOWrapped);

    return widgetsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateWidget(Locale locale) {
    BackPage backPage = computeBackPage("WIDGET_CREATE");
    ModelAndView widgetManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des widgets ");
    widgetManager.addObject(CREATE_FORM, computeCreateForm());
    List<String> types = widgetProviders.getPlugins().stream().map(plugin -> plugin.getWidgetType())
      .collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);
    return widgetManager;
  }

  WidgetCreateForm computeCreateForm() {
    return WidgetCreateFormBuilder.create().build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidget(Locale locale, String widgetId,
    String personalizationLanguageCode) {

    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    BackPage backPage = computeBackPage("WIDGET_UPDATE");
    ModelAndView widgetManager = super
      .computeModelAndViewForBackPage(backPage, locale);

    WidgetDTO widget = widgetService
      .getEntity(Long.parseLong(widgetId), personalizationLanguageCode);
    LOGGER.info("Construction du formulaire de creation des widgets ");
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget, personalizationLanguageCode));
    List<String> types = widgetProviders.getPlugins().stream().map(plugin -> plugin.getWidgetType())
      .collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(widget.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) widgetManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return widgetManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidgetMain(Locale locale, String widgetId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView widgetManager = new ModelAndView("back/widgets/edit/tab_main");
    WidgetDTO widget = widgetService
      .getEntity(Long.parseLong(widgetId), personalizationLanguageCode);
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget, personalizationLanguageCode));
    List<String> types = widgetProviders.getPlugins().stream().map(plugin -> plugin.getWidgetType())
      .collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);
    return widgetManager;
  }

  WidgetUpdateForm computeUpdateForm(WidgetDTO widget, String personalizationLanguageCode) {
    return WidgetUpdateFormBuilder.create().creationDate(widget.getCreationDate())
      .entityId(widget.getEntityId())
      .id(widget.getId()).asynchronous(widget.isAsynchronous())
      .personalization(widget.getPersonalization())
      .modificationDate(widget.getModificationDate()).name(widget.getName())
      .type(widget.getType())
      .localeCode(personalizationLanguageCode).build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidgetPersonalization(Locale locale,
    String widgetId,
    String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView widgetManager = new ModelAndView("back/widgets/edit/tab_personalization");
    widgetManager.addObject(LOCALES, availableLocales);
    WidgetDTO widget = widgetService
      .getEntity(Long.parseLong(widgetId), personalizationLanguageCode);
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget, personalizationLanguageCode));

    WidgetProviderPlugin widgetProviderPlugin = widgetProviders.getPluginFor(widget.getType());
    widgetManager.addObject("withDatasource", widgetProviderPlugin.withDatasource());
    if (widgetProviderPlugin.withDatasource()) {
      widgetManager.addObject("ajaxSearchUrl", widgetProviderPlugin.getAjaxSearchUrl());
      String entityId = widget.getEntityId();
      if (StringUtils.hasText(entityId)) {
        List<? extends BaseDTO> linkableEntities = widgetProviders.getPluginFor(widget.getType())
          .getLinkableEntities();

        Optional<? extends BaseDTO> linkedEntity = linkableEntities.stream()
          .filter(entity -> entity.getId().equals(Long.parseLong(entityId))).findFirst();
        if (linkedEntity.isPresent()) {
          widgetManager.addObject("linkedEntity", linkedEntity.get());
        }
      }

    }

    widgetManager.addObject(TOOLTIP_KEY, computeToolTipKey(widget.getType()));
    widgetManager.addObject(MACROS_KEY, computePersonalizationMacros());
    return widgetManager;
  }

  protected List<String> computePersonalizationMacros() {
    return Arrays.asList("card", "row", "col", "nav", "collapse", "pagination", "button");
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/widgets";
  }

  @Override
  protected String getItemLink() {
    return "/manager/widgets/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/widgets/search";
  }


  @Override
  protected String getSearchPlaceHolder() {
    return "search.widgets.placeHolder";
  }

  @Override
  protected Page<WidgetDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<WidgetDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<WidgetDTO> pagedWidgetDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedWidgetDTOEntries = widgetService
        .searchEntities(pageRequest, query);
    } else {
      pagedWidgetDTOEntries = widgetService
        .getPagedEntities(pageRequest);
    }

    if (CollectionUtils.isEmpty(pagedWidgetDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedWidgetDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedWidgetDTOEntries.getTotalElements());
  }

  String computeToolTipKey(String widgetType) {
    WidgetProviderPlugin widgetProviderPlugin = widgetProviders.getPluginFor(widgetType);
    return widgetProviderPlugin.getTooltipKey();
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:widgets:create";
  }
}
