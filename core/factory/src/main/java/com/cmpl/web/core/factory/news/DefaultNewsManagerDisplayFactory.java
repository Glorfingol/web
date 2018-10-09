package com.cmpl.web.core.factory.news;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.news.content.NewsContentDTO;
import com.cmpl.web.core.news.content.NewsContentDTOBuilder;
import com.cmpl.web.core.news.content.NewsContentRequest;
import com.cmpl.web.core.news.content.NewsContentRequestBuilder;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.entry.NewsEntryRequest;
import com.cmpl.web.core.news.entry.NewsEntryRequestBuilder;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageDTOBuilder;
import com.cmpl.web.core.news.image.NewsImageRequest;
import com.cmpl.web.core.news.image.NewsImageRequestBuilder;
import com.cmpl.web.core.page.BackPage;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * Implementation de l'interface pour la factory des pages d'actualite sur le back
 *
 * @author Louis
 */
public class DefaultNewsManagerDisplayFactory extends AbstractBackDisplayFactory<NewsEntryDTO>
  implements NewsManagerDisplayFactory {

  private final NewsEntryService newsEntryService;


  public DefaultNewsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
    WebMessageSource messageSource, NewsEntryService newsEntryService,
    PluginRegistry<BreadCrumb, String> breadCrumbRegistry, Set<Locale> availableLocales,
    GroupService groupService,
    MembershipService membershipService, PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService, backPagesRegistry, contextHolder);

    this.newsEntryService = Objects.requireNonNull(newsEntryService);

  }


  @Override
  public ModelAndView computeModelAndViewForViewAllNews(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("NEWS_VIEW");
    ModelAndView newsManager = computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des entrées de blog pour la page {}", backPage.getPageName());

    PageWrapper<NewsEntryDTO> pagedNewsWrapped = computePageWrapper(locale, pageNumber, null);

    newsManager.addObject("wrappedEntities", pagedNewsWrapped);

    return newsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPageCreateNews(Locale locale) {
    BackPage backPage = computeBackPage("NEWS_CREATE");
    ModelAndView newsManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire d'entrées de blog pour la page {}",
      backPage.getPageName());
    newsManager.addObject("newsFormBean", computeNewsRequestForCreateForm());

    return newsManager;
  }

  @Override
  protected Page<NewsEntryDTO> computeEntries(Locale locale, int pageNumber, String query) {

    Page<NewsEntryDTO> pagedNewsEntries;
    if (StringUtils.hasText(query)) {
      pagedNewsEntries = newsEntryService
        .searchEntities(PageRequest.of(pageNumber, contextHolder.getElementsPerPage()),
          query);
    } else {
      pagedNewsEntries = newsEntryService
        .getPagedEntities(PageRequest.of(pageNumber, contextHolder.getElementsPerPage()));
    }
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(new ArrayList<>());
    }

    return pagedNewsEntries;
  }

  @Override
  public ModelAndView computeModelAndViewForOneNewsEntry(Locale locale, String newsEntryId) {
    BackPage backPage = computeBackPage("NEWS_UPDATE");
    ModelAndView newsManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    newsManager.addObject("updateForm", computeNewsRequestForEditForm(newsEntryId));

    return newsManager;
  }

  NewsEntryRequest computeNewsRequestForCreateForm() {
    return NewsEntryRequestBuilder.create().content(NewsContentRequestBuilder.create().build())
      .image(NewsImageRequestBuilder.create().build()).build();
  }

  NewsEntryRequest computeNewsRequestForEditForm(String newsEntryId) {

    NewsEntryDTO dto = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    if (dto.getNewsImage() == null) {
      dto.setNewsImage(new NewsImageDTO());
    }
    if (dto.getNewsContent() == null) {
      dto.setNewsContent(new NewsContentDTO());
    }

    return computeNewsEntryRequest(dto);

  }

  NewsEntryRequest computeNewsEntryRequest(NewsEntryDTO dto) {
    return NewsEntryRequestBuilder.create().author(dto.getAuthor()).tags(dto.getTags())
      .title(dto.getTitle())
      .content(computeNewsContentRequest(dto)).image(computeNewsImageRequest(dto)).id(dto.getId())
      .creationDate(dto.getCreationDate()).modificationDate(dto.getModificationDate()).build();
  }

  NewsImageRequest computeNewsImageRequest(NewsEntryDTO dto) {
    return NewsImageRequestBuilder.create().alt(dto.getNewsImage().getAlt())
      .media(dto.getNewsImage().getMedia())
      .id(dto.getNewsImage().getId()).creationDate(dto.getNewsImage().getCreationDate())
      .modificationDate(dto.getNewsImage().getModificationDate())
      .legend(dto.getNewsImage().getLegend()).build();
  }

  NewsContentRequest computeNewsContentRequest(NewsEntryDTO dto) {
    return NewsContentRequestBuilder.create().content(dto.getNewsContent().getContent())
      .id(dto.getNewsContent().getId()).creationDate(dto.getNewsContent().getCreationDate())
      .modificationDate(dto.getNewsContent().getModificationDate()).build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateNewsMain(String newsEntryId, Locale locale) {
    ModelAndView newsManager = new ModelAndView("back/news/edit/tab_main");
    newsManager.addObject("newsEntryFormBean", computeNewsRequestForEditForm(newsEntryId));

    return newsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateNewsContent(String newsEntryId, Locale locale) {
    ModelAndView newsManager = new ModelAndView("back/news/edit/tab_content");
    NewsEntryDTO newsEntryDTO = newsEntryService.getEntity(Long.parseLong(newsEntryId));
    NewsContentDTO newsContentDTO = newsEntryDTO.getNewsContent();
    if (newsContentDTO == null) {
      newsEntryDTO.setNewsContent(NewsContentDTOBuilder.create().build());
    }
    newsManager.addObject("newsContentFormBean", computeNewsContentRequest(newsEntryDTO));
    newsManager.addObject("newsEntryId", String.valueOf(newsEntryDTO.getId()));

    return newsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateNewsImage(String newsEntryId, Locale locale) {
    ModelAndView newsManager = new ModelAndView("back/news/edit/tab_image");
    NewsEntryDTO newsEntryDTO = newsEntryService.getEntity(Long.parseLong(newsEntryId));
    NewsImageDTO newsImageDTO = newsEntryDTO.getNewsImage();
    if (newsImageDTO == null) {
      newsEntryDTO.setNewsImage(NewsImageDTOBuilder.create().build());
    }
    newsManager.addObject("newsImageFormBean", computeNewsImageRequest(newsEntryDTO));
    newsManager.addObject("newsEntryId", String.valueOf(newsEntryDTO.getId()));

    return newsManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/news";
  }

  @Override
  protected String getItemLink() {
    return "/manager/news/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/news/search";
  }


  @Override
  protected String getSearchPlaceHolder() {
    return "search.news.placeHolder";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:news:create";
  }
}
