package com.cmpl.web.core.factory;

import java.util.Locale;

import org.springframework.data.domain.Page;

import com.cmpl.web.core.builder.PageWrapperBuilder;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;

public abstract class AbstractBackDisplayFactoryImpl<T> extends BackDisplayFactoryImpl {

  public AbstractBackDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource) {
    super(menuFactory, messageSource);
  }

  public PageWrapper<T> computePageWrapper(Locale locale, int pageNumber) {
    Page<T> pagedDTOEntries = computeEntries(locale, pageNumber);

    boolean isFirstPage = pagedDTOEntries.isFirst();
    boolean isLastPage = pagedDTOEntries.isLast();
    int totalPages = pagedDTOEntries.getTotalPages();
    int currentPageNumber = pagedDTOEntries.getNumber();

    return new PageWrapperBuilder<T>().currentPageNumber(currentPageNumber).firstPage(isFirstPage).lastPage(isLastPage)
        .page(pagedDTOEntries).totalPages(totalPages).pageBaseUrl(getBaseUrl())
        .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages)).build();
  }

  protected abstract String getBaseUrl();

  protected abstract Page<T> computeEntries(Locale locale, int pageNumber);

}
