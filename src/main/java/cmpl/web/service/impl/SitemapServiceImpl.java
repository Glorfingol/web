package cmpl.web.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import cmpl.web.message.WebMessageSource;
import cmpl.web.model.BaseException;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.service.NewsEntryService;
import cmpl.web.service.SitemapService;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

/**
 * Implementation de l'interface gerant le sitemap
 * 
 * @author Louis
 *
 */
public class SitemapServiceImpl implements SitemapService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SitemapServiceImpl.class);

  private static final String BASE_URL = "http://cmpl.com";

  private final WebMessageSource messageSource;

  private final NewsEntryService newsEntryService;

  public SitemapServiceImpl(NewsEntryService newsEntryService, WebMessageSource messageSource) {
    this.newsEntryService = newsEntryService;
    this.messageSource = messageSource;
  }

  @Override
  @Cacheable(sync = true)
  public String createSiteMap(Locale locale) throws BaseException {

    try {
      Path temporarySitemapFile = Files.createTempDirectory("sitemap");
      writeSitemap(temporarySitemapFile, locale);
      Path sitemapPath = Paths.get(temporarySitemapFile.toString(), "sitemap.xml");
      return readSitemap(sitemapPath);
    } catch (IOException e) {
      LOGGER.error("Erreur lors de la génération du sitemap", e);
      throw new BaseException(e.getMessage());
    }

  }

  void writeSitemap(Path temporarySitemapFile, Locale locale) throws IOException {
    WebSitemapGenerator sitemap = WebSitemapGenerator.builder(BASE_URL, temporarySitemapFile.toFile()).build();

    List<NewsEntryDTO> entries = newsEntryService.getEntities();
    LocalDate lastModified = computeLastModified(entries);
    List<WebSitemapUrl> newsEntriesUrls = computeNewsEntriesUrls(locale, entries);
    List<WebSitemapUrl> menuUrls = computeMenuUrls(locale);
    sitemap.addUrls(menuUrls);
    sitemap.addUrl(computeUrlForMenuNews(lastModified, locale));
    sitemap.addUrls(newsEntriesUrls);
    sitemap.write();

  }

  List<WebSitemapUrl> computeNewsEntriesUrls(Locale locale, List<NewsEntryDTO> entries) throws MalformedURLException {
    List<WebSitemapUrl> newsEntriesUrls = new ArrayList<>();
    for (NewsEntryDTO newsEntry : entries) {
      newsEntriesUrls.add(computeUrlForNewsEntry(newsEntry, locale));
    }
    return newsEntriesUrls;
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

  List<WebSitemapUrl> computeMenuUrls(Locale locale) throws MalformedURLException {
    List<WebSitemapUrl> menuUrls = new ArrayList<>();
    for (MENU menu : MENU.values()) {
      if (!MENU.NEWS.equals(menu)) {
        menuUrls.add(computeUrlForMenuNotNews(menu, locale));
      }
    }

    return menuUrls;
  }

  WebSitemapUrl computeUrlForMenuNotNews(MENU menu, Locale locale) throws MalformedURLException {
    return new WebSitemapUrl.Options(BASE_URL + getI18nValue(menu.getHref(), locale)).changeFreq(ChangeFreq.NEVER)
        .priority(1d).build();
  }

  WebSitemapUrl computeUrlForMenuNews(LocalDate lastModified, Locale locale) throws MalformedURLException {
    ZoneId defaultZoneId = ZoneId.systemDefault();
    return new WebSitemapUrl.Options(BASE_URL + getI18nValue(MENU.NEWS.getHref(), locale))
        .lastMod(Date.from(lastModified.atStartOfDay(defaultZoneId).toInstant())).priority(1d)
        .changeFreq(ChangeFreq.YEARLY).build();
  }

  WebSitemapUrl computeUrlForNewsEntry(NewsEntryDTO newsEntry, Locale locale) throws MalformedURLException {
    ZoneId defaultZoneId = ZoneId.systemDefault();
    return new WebSitemapUrl.Options(BASE_URL + getI18nValue(MENU.NEWS.getHref(), locale) + "/" + newsEntry.getId())
        .lastMod(Date.from(newsEntry.getModificationDate().atStartOfDay(defaultZoneId).toInstant()))
        .changeFreq(ChangeFreq.YEARLY).priority(0.5d).build();
  }

  LocalDate computeLastModified(List<NewsEntryDTO> entries) {
    LocalDate lastModified = entries.get(0).getModificationDate();
    for (NewsEntryDTO newsEntry : entries) {
      LocalDate dateModification = newsEntry.getModificationDate();
      if (dateModification.isAfter(lastModified)) {
        lastModified = dateModification;
      }
    }
    return lastModified;

  }

}
