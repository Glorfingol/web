package com.cmpl.web.core.factory;

import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BACK_PAGE;

public abstract class AbstractBackDisplayFactoryImpl<T> extends BackDisplayFactoryImpl {

  public AbstractBackDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService, membershipService);
  }

  public PageWrapper<T> computePageWrapper(Locale locale, int pageNumber, String query) {
    Page<T> pagedDTOEntries = computeEntries(locale, pageNumber, query);

    boolean isFirstPage = pagedDTOEntries.isFirst();
    boolean isLastPage = pagedDTOEntries.isLast();
    int totalPages = pagedDTOEntries.getTotalPages();
    int currentPageNumber = pagedDTOEntries.getNumber();

    return new PageWrapperBuilder<T>().currentPageNumber(currentPageNumber).firstPage(isFirstPage).lastPage(isLastPage)
        .page(pagedDTOEntries).totalPages(totalPages).pageBaseUrl(getBaseUrl()).createItemLink(getCreateItemLink())
        .createItemPrivilege(getCreateItemPrivilege()).itemLink(getItemLink())
        .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages)).build();
  }

  @Override
  public ModelAndView computeModelAndViewForAllEntitiesTab(Locale locale, int pageNumber, String query) {
    ModelAndView model = new ModelAndView(getViewAllTemplate());

    model.addObject("wrappedEntities", computePageWrapper(locale, pageNumber, query));
    model.addObject("searchUrl", getSearchUrl());
    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {

    ModelAndView model = super.computeModelAndViewForBackPage(backPage, locale);
    model.addObject("searchUrl", getSearchUrl());

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
