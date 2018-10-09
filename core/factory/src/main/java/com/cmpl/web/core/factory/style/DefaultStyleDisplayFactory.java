package com.cmpl.web.core.factory.style;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.style.StyleCreateFormBuilder;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.style.StyleUpdateForm;
import com.cmpl.web.core.style.StyleUpdateFormBuilder;
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

public class DefaultStyleDisplayFactory extends AbstractBackDisplayFactory<StyleDTO> implements
  StyleDisplayFactory {

  private final StyleService styleService;

  public DefaultStyleDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
    StyleService styleService,
    ContextHolder contextHolder, PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales,
    GroupService groupService, MembershipService membershipService,
    PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService,
      backPagesRegistry, contextHolder);
    this.styleService = Objects.requireNonNull(styleService);


  }

  @Override
  public ModelAndView computeModelAndViewForViewAllStyles(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("STYLE_VIEW");
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des styles pour la page {} ", backPage.getPageName());

    PageWrapper<StyleDTO> pagedStyleDTOWrapped = computePageWrapper(locale, pageNumber, "");

    stylesManager.addObject("wrappedEntities", pagedStyleDTOWrapped);

    return stylesManager;

  }

  @Override
  public ModelAndView computeModelAndViewForCreateStyle(Locale locale) {
    BackPage backPage = computeBackPage("STYLE_CREATE");
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des styles");

    stylesManager.addObject("createForm", StyleCreateFormBuilder.create().content("").build());

    return stylesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyle(Locale locale, String styleId) {
    BackPage backPage = computeBackPage("STYLE_UPDATE");
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du style pour la page {} ", backPage.getPageName());
    StyleDTO style = styleService.getEntity(Long.parseLong(styleId));

    StyleUpdateForm updateForm = StyleUpdateFormBuilder.create().content(style.getContent())
      .creationDate(style.getCreationDate()).creationUser(style.getCreationUser())
      .id(style.getId())
      .name(style.getName()).modificationDate(style.getModificationDate())
      .modificationUser(style.getModificationUser()).mediaId(style.getMedia().getId())
      .mediaName(style.getMedia().getName()).build();

    stylesManager.addObject("updateForm", updateForm);

    return stylesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyleMain(Locale locale, String styleId) {
    ModelAndView stylesManager = new ModelAndView("back/styles/edit/tab_main");

    StyleDTO style = styleService.getEntity(Long.parseLong(styleId));

    StyleUpdateForm updateForm = StyleUpdateFormBuilder.create().content(style.getContent())
      .name(style.getName())
      .creationDate(style.getCreationDate()).creationUser(style.getCreationUser())
      .id(style.getId())
      .modificationDate(style.getModificationDate()).modificationUser(style.getModificationUser())
      .mediaId(style.getMedia().getId()).mediaName(style.getMedia().getName()).build();

    stylesManager.addObject("updateForm", updateForm);
    return stylesManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/styles";
  }

  @Override
  protected String getItemLink() {
    return "/manager/styles/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/styles/search";
  }

  @Override
  protected String getSearchPlaceHolder() {
    return "search.styles.placeHolder";
  }

  @Override
  protected Page<StyleDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<StyleDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<StyleDTO> pagedStyleDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedStyleDTOEntries = styleService.searchEntities(pageRequest, query);
    } else {
      pagedStyleDTOEntries = styleService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedStyleDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedStyleDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedStyleDTOEntries.getTotalElements());
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:style:create";
  }

}
