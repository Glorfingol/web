package com.cmpl.web.core.factory;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupDTO;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipCreateFormBuilder;
import com.cmpl.web.core.membership.MembershipDTO;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractBackDisplayFactory<T> extends DefaultBackDisplayFactory implements
  CRUDBackDisplayFactory {

  private final MembershipService membershipService;

  private final GroupService groupService;

  protected final ContextHolder contextHolder;

  public AbstractBackDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
    PluginRegistry<BreadCrumb, String> breadCrumbRegistry, Set<Locale> availableLocales,
    GroupService groupService,
    MembershipService membershipService, PluginRegistry<BackPage, String> backPagesRegistry,
    ContextHolder contextHolder) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, backPagesRegistry);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.groupService = Objects.requireNonNull(groupService);
    this.contextHolder = Objects.requireNonNull(contextHolder);
  }

  public PageWrapper<T> computePageWrapper(Locale locale, int pageNumber, String query) {
    Page<T> pagedDTOEntries = computeEntries(locale, pageNumber, query);

    boolean isFirstPage = pagedDTOEntries.isFirst();
    boolean isLastPage = pagedDTOEntries.isLast();
    int totalPages = pagedDTOEntries.getTotalPages();
    int currentPageNumber = pagedDTOEntries.getNumber();

    return new PageWrapperBuilder<T>().currentPageNumber(currentPageNumber).firstPage(isFirstPage)
      .lastPage(isLastPage)
      .page(pagedDTOEntries).totalPages(totalPages).pageBaseUrl(getBaseUrl())
      .createItemLink(getCreateItemLink())
      .createItemPrivilege(getCreateItemPrivilege()).itemLink(getItemLink())
      .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages))
      .build();
  }

  @Override
  public ModelAndView computeModelAndViewForAllEntitiesTab(Locale locale, int pageNumber,
    String query) {
    ModelAndView model = new ModelAndView(getViewAllTemplate());

    model.addObject("wrappedEntities", computePageWrapper(locale, pageNumber, query));
    model.addObject("searchUrl", getSearchUrl());
    model.addObject("searchPlaceHolder", getI18nValue(getSearchPlaceHolder(), locale));
    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BackPage backPage,
    Locale locale) {

    ModelAndView model = super.computeModelAndViewForBackPage(backPage, locale);
    model.addObject("searchUrl", getSearchUrl());
    model.addObject("searchPlaceHolder", getI18nValue(getSearchPlaceHolder(), locale));

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForMembership(String entityId) {

    ModelAndView model = new ModelAndView("common/back_membership");

    List<MembershipDTO> memberships = membershipService.findByEntityId(Long.parseLong(entityId));
    List<GroupDTO> associatedGroups = memberships.stream()
      .map(membershipDTO -> groupService.getEntity(membershipDTO.getGroupId()))
      .collect(Collectors.toList());
    model.addObject("linkedGroups", associatedGroups);

    List<GroupDTO> linkableGroups = groupService.getEntities().stream()
      .filter(groupDTO -> !associatedGroups.contains(groupDTO)).collect(Collectors.toList());
    model.addObject("linkableGroups", linkableGroups);
    model.addObject("createForm", MembershipCreateFormBuilder.create().entityId(entityId).build());

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForLinkedGroups(String entityId, String query) {
    ModelAndView model = new ModelAndView("common/back_linked_groups");

    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    model.addObject("linkedGroups",
      groupService.searchLinkedEntities(pageRequest, query, Long.parseLong(entityId)));

    model.addObject("createForm", MembershipCreateFormBuilder.create().entityId(entityId).build());
    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForLinkableGroups(String entityId, String query) {
    ModelAndView model = new ModelAndView("common/back_linkable_groups");

    PageRequest pageRequest = PageRequest.of(0, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    model.addObject("linkableGroups",
      groupService.searchNotLinkedEntities(pageRequest, query, Long.parseLong(entityId)));
    model.addObject("createForm", MembershipCreateFormBuilder.create().entityId(entityId).build());
    return model;
  }


  protected abstract String getBaseUrl();

  protected abstract String getItemLink();

  protected String getCreateItemLink() {
    return getBaseUrl() + "/_create";
  }

  protected String getViewAllTemplate() {
    return "common/back_items_table";
  }

  protected abstract String getSearchUrl();


  protected abstract String getSearchPlaceHolder();

  protected abstract String getCreateItemPrivilege();

  protected abstract Page<T> computeEntries(Locale locale, int pageNumber, String query);

  protected boolean canAddBreadCrumbItem(BreadCrumb breadCrumb, BreadCrumbItem item) {
    for (BreadCrumbItem itemFromModel : breadCrumb.getItems()) {
      if (itemFromModel.getText().equals(item.getText())) {
        return false;
      }
    }
    return true;
  }
}
