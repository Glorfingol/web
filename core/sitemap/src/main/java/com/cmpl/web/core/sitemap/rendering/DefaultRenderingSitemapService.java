package com.cmpl.web.core.sitemap.rendering;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.sitemap.SitemapDTO;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation de l'interface gerant le sitemap
 *
 * @author Louis
 */

public class DefaultRenderingSitemapService implements RenderingSitemapService {

  private static final Logger LOGGER = LoggerFactory
    .getLogger(DefaultRenderingSitemapService.class);

  private final WebMessageSource messageSource;

  private final PageService pageService;

  private final WebsiteService websiteService;

  private final SitemapService sitemapService;

  public DefaultRenderingSitemapService(WebMessageSource messageSource, PageService pageService,
    WebsiteService websiteService, SitemapService sitemapService) {
    this.messageSource = Objects.requireNonNull(messageSource);

    this.pageService = Objects.requireNonNull(pageService);

    this.websiteService = Objects.requireNonNull(websiteService);

    this.sitemapService = Objects.requireNonNull(sitemapService);

  }

  @Override
  public String createSiteMap(String websiteName, Locale locale) throws BaseException {

    WebsiteDTO website = websiteService.getWebsiteByName(websiteName);
    if (website == null) {
      return "";
    }
    List<SitemapDTO> sitemapDTOS = sitemapService.findByWebsiteId(website.getId());

    try {
      Path temporarySitemapFile = Files.createTempDirectory("sitemap");
      writeSitemap(temporarySitemapFile, website, sitemapDTOS);
      Path sitemapPath = Paths.get(temporarySitemapFile.toString(), "sitemap.xml");
      return readSitemap(sitemapPath);
    } catch (IOException e) {
      LOGGER.error("Erreur lors de la génération du sitemap", e);
      throw new BaseException(e.getMessage());
    }

  }

  void writeSitemap(Path temporarySitemapFile, WebsiteDTO website, List<SitemapDTO> sitemapDTOS)
    throws IOException {
    String scheme = website.isSecure() ? "https://" : "http://";
    WebSitemapGenerator sitemap = WebSitemapGenerator
      .builder(scheme + website.getName(), temporarySitemapFile.toFile())
      .build();

    List<WebSitemapUrl> menuUrls = computeMenuUrls(website, sitemapDTOS);
    sitemap.addUrls(menuUrls);
    sitemap.write();

  }

  String readSitemap(Path sitemapPath) throws IOException {
    Charset charset = Charset.forName("UTF-8");
    BufferedReader reader = Files.newBufferedReader(sitemapPath, charset);
    StringBuilder siteMapbuilder = new StringBuilder();
    String sitemapLine;
    while ((sitemapLine = reader.readLine()) != null) {
      siteMapbuilder.append(sitemapLine);
    }
    reader.close();

    return siteMapbuilder.toString();
  }

  String getI18nValue(String key, Locale locale) {
    return messageSource.getI18n(key, locale);
  }

  List<WebSitemapUrl> computeMenuUrls(WebsiteDTO website, List<SitemapDTO> sitemapDTOS) {
    List<Long> pagesId = sitemapDTOS.stream().map(sitemap -> sitemap.getPageId())
      .collect(Collectors.toList());
    return pageService.getEntities().stream()
      .filter(page -> pagesId.contains(page.getId()))
      .collect(Collectors.toList()).stream().map(page -> computeUrlForPage(website, page))
      .collect(Collectors.toList());
  }

  WebSitemapUrl computeUrlForPage(WebsiteDTO website, PageDTO page) {
    try {
      String scheme = website.isSecure() ? "https://" : "http://";
      return new WebSitemapUrl.Options(scheme + website.getName() + "/pages/" + page.getHref())
        .changeFreq(ChangeFreq.YEARLY)
        .priority(1d).build();
    } catch (MalformedURLException e) {
      LOGGER.error("URL malformée", e);
      return null;
    }
  }

  LocalDateTime computeLastModified(List<NewsEntryDTO> entries) {
    LocalDateTime lastModified = entries.get(0).getModificationDate();
    for (NewsEntryDTO newsEntry : entries) {
      LocalDateTime dateModification = newsEntry.getModificationDate();
      if (dateModification.isAfter(lastModified)) {
        lastModified = dateModification;
      }
    }
    return lastModified;

  }

}
