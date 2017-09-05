package cmpl.web.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.factory.BackDisplayFactoryImpl;
import cmpl.web.core.model.PageWrapper;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;

public class PagesManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements PagesManagerDisplayFactory {

  private final PageService pageService;
  private final ContextHolder contextHolder;

  public PagesManagerDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory, PageService pageService,
      ContextHolder contextHolder) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
    this.pageService = pageService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllPages(BACK_PAGE backPage, Locale locale, int pageNumber) {
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des pages pour la page " + backPage.name());

    PageWrapper<PageDTO> pagedPageDTOWrapped = computePageWrapperOfNews(locale, pageNumber);

    pagesManager.addObject("wrappedPages", pagedPageDTOWrapped);

    return pagesManager;
  }

  PageWrapper<PageDTO> computePageWrapperOfNews(Locale locale, int pageNumber) {
    Page<PageDTO> pagedPageDTOEntries = computePagesEntries(pageNumber);

    boolean isFirstPage = pagedPageDTOEntries.isFirst();
    boolean isLastPage = pagedPageDTOEntries.isLast();
    int totalPages = pagedPageDTOEntries.getTotalPages();
    int currentPageNumber = pagedPageDTOEntries.getNumber();

    PageWrapper<PageDTO> pagedPageDTOWrapped = new PageWrapper<>();
    pagedPageDTOWrapped.setCurrentPageNumber(currentPageNumber);
    pagedPageDTOWrapped.setFirstPage(isFirstPage);
    pagedPageDTOWrapped.setLastPage(isLastPage);
    pagedPageDTOWrapped.setPage(pagedPageDTOEntries);
    pagedPageDTOWrapped.setTotalPages(totalPages);
    pagedPageDTOWrapped.setPageBaseUrl("/manager/pages");
    pagedPageDTOWrapped.setPageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages));
    return pagedPageDTOWrapped;
  }

  Page<PageDTO> computePagesEntries(int pageNumber) {
    List<PageDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<PageDTO> pagedPageDTOEntries = pageService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedPageDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    for (PageDTO pageDTOFromDB : pagedPageDTOEntries.getContent()) {
      pageEntries.add(pageDTOFromDB);
    }

    return new PageImpl<>(pageEntries, pageRequest, pagedPageDTOEntries.getTotalElements());
  }

  PageCreateForm computeCreateForm(Locale locale) {
    PageCreateForm createForm = new PageCreateForm();

    createForm.setNameHelp(getI18nValue("page.name.help", locale));
    createForm.setMenuTitleHelp(getI18nValue("page.menuTitle.help", locale));
    createForm.setWithNewsHelp(getI18nValue("page.withNews.help", locale));
    createForm.setNameLabel(getI18nValue("page.name.label", locale));
    createForm.setBodyLabel(getI18nValue("page.body.label", locale));
    createForm.setMenuTitleLabel(getI18nValue("page.menuTitle.label", locale));
    createForm.setWithNewsLabel(getI18nValue("page.withNews.label", locale));
    createForm.setBodyHelp(getI18nValue("page.body.help", locale));

    return createForm;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePage(BACK_PAGE backPage, Locale locale, String pageId) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(backPage, locale);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject("updateForm", createUpdateForm(page, locale));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreatePage(BACK_PAGE backPage, Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des pages ");
    newsManager.addObject("createForm", computeCreateForm(locale));
    return newsManager;
  }

  PageUpdateForm createUpdateForm(PageDTO page, Locale locale) {

    String nameLabel = getI18nValue("page.name.label", locale);
    String nameHelp = getI18nValue("page.name.help", locale);
    String menuTitleLabel = getI18nValue("page.menuTitle.label", locale);
    String menuTitleHelp = getI18nValue("page.menuTitle.help", locale);
    String withNewsLabel = getI18nValue("page.withNews.label", locale);
    String withNewsHelp = getI18nValue("page.withNews.help", locale);
    String bodyLabel = getI18nValue("page.body.label", locale);
    String bodyHelp = getI18nValue("page.body.help", locale);

    return new PageUpdateForm(page, nameLabel, menuTitleLabel, withNewsLabel, bodyLabel, nameHelp, menuTitleHelp,
        withNewsHelp, bodyHelp);
  }
}
