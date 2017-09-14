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
import cmpl.web.meta.MetaElementCreateForm;
import cmpl.web.meta.MetaElementDTO;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementService;
import cmpl.web.meta.OpenGraphMetaElementCreateForm;
import cmpl.web.meta.OpenGraphMetaElementDTO;
import cmpl.web.meta.OpenGraphMetaElementService;

public class PagesManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements PagesManagerDisplayFactory {

  private final PageService pageService;
  private final OpenGraphMetaElementService openGraphMetaElementService;
  private final MetaElementService metaElementService;
  private final ContextHolder contextHolder;

  public PagesManagerDisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory, PageService pageService,
      ContextHolder contextHolder, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService) {
    super(menuFactory, footerFactory, messageSource, metaElementFactory);
    this.pageService = pageService;
    this.contextHolder = contextHolder;
    this.metaElementService = metaElementService;
    this.openGraphMetaElementService = openGraphMetaElementService;
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

  PageCreateForm computeCreateForm() {
    return new PageCreateForm();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePage(BACK_PAGE backPage, Locale locale, String pageId) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(backPage, locale);
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject("updateForm", createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreatePage(BACK_PAGE backPage, Locale locale) {
    ModelAndView pageManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des pages ");
    pageManager.addObject("createForm", computeCreateForm());
    return pageManager;
  }

  PageUpdateForm createUpdateForm(PageDTO page) {
    return new PageUpdateForm(page);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMain(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_main");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject("updateForm", createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageBody(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_body");
    PageDTO page = pageService.getEntity(Long.parseLong(pageId));
    pageManager.addObject("updateForm", createUpdateForm(page));
    return pageManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageMeta(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_meta");
    List<MetaElementDTO> metaElements = metaElementService.findMetaElementsByPageId(pageId);
    pageManager.addObject("metaElements", metaElements);
    pageManager.addObject("createForm", createMetaElementCreateForm(pageId, locale));
    return pageManager;
  }

  MetaElementCreateForm createMetaElementCreateForm(String pageId, Locale locale) {
    MetaElementCreateForm createForm = new MetaElementCreateForm();
    createForm.setPageId(pageId);
    return createForm;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdatePageOpenGraphMeta(Locale locale, String pageId) {
    ModelAndView pageManager = new ModelAndView("back/pages/edit/tab_open_graph_meta");
    List<OpenGraphMetaElementDTO> metaElements = openGraphMetaElementService.findOpenGraphMetaElementsByPageId(pageId);
    pageManager.addObject("metaElements", metaElements);
    pageManager.addObject("createForm", createOpenGraphMetaElementCreateForm(pageId, locale));
    return pageManager;
  }

  OpenGraphMetaElementCreateForm createOpenGraphMetaElementCreateForm(String pageId, Locale locale) {
    OpenGraphMetaElementCreateForm createForm = new OpenGraphMetaElementCreateForm();
    createForm.setPageId(pageId);
    return createForm;
  }
}
