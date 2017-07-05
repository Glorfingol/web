package cmpl.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.message.WebMessageSource;
import cmpl.web.model.BaseException;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.service.NewsEntryService;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapUrl;

@RunWith(MockitoJUnitRunner.class)
public class SitemapServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private SitemapServiceImpl service;

  private Locale locale;

  @Before
  public void setUp() {
    File sitemap = new File("src/test/resources/sitemap.xml");
    if (sitemap.exists()) {
      sitemap.delete();
    }

    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeLastModified() throws Exception {
    Calendar today = Calendar.getInstance();
    Calendar yesterday = Calendar.getInstance();
    yesterday.add(Calendar.MONTH, -1);
    Calendar twoDaysAgo = Calendar.getInstance();
    twoDaysAgo.add(Calendar.MONTH, -2);

    NewsEntryDTO newsEntryToday = new NewsEntryDTOBuilder().modificationDate(today.getTime()).toNewsEntryDTO();
    NewsEntryDTO newsEntryYesterday = new NewsEntryDTOBuilder().modificationDate(yesterday.getTime()).toNewsEntryDTO();
    NewsEntryDTO newsEntryTwoDaysAgo = new NewsEntryDTOBuilder().modificationDate(twoDaysAgo.getTime())
        .toNewsEntryDTO();

    List<NewsEntryDTO> entries = new ArrayList<>();
    entries.add(newsEntryTwoDaysAgo);
    entries.add(newsEntryToday);
    entries.add(newsEntryYesterday);

    Date result = service.computeLastModified(entries);

    Assert.assertEquals(today.getTime(), result);
  }

  @Test
  public void testGetI18nValue() throws Exception {

    String value = "someValue";
    String key = "someKey";
    BDDMockito.doReturn(value).when(messageSource).getI18n(Mockito.eq(key), Mockito.eq(locale));
    String result = service.getI18nValue(key, locale);

    Assert.assertEquals(value, result);
  }

  @Test
  public void testComputeUrlForNewsEntry() throws Exception {

    String url = "http://cmpl.com/actualites/666";
    String newsHref = "/actualites";
    String key = "news.href";
    Long id = Long.valueOf("666");
    Date dateModification = new Date();
    Double priority = Double.valueOf("0.5");
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(id.longValue()).modificationDate(dateModification)
        .toNewsEntryDTO();

    BDDMockito.doReturn(newsHref).when(service).getI18nValue(Mockito.eq(key), Mockito.eq(locale));

    WebSitemapUrl result = service.computeUrlForNewsEntry(newsEntry, locale);

    Assert.assertEquals(url, result.getUrl().toExternalForm());
    Assert.assertEquals(dateModification, result.getLastMod());
    Assert.assertEquals(ChangeFreq.YEARLY, result.getChangeFreq());
    Assert.assertEquals(priority, result.getPriority());

  }

  @Test
  public void testComputeUrlForMenuNews() throws Exception {
    String url = "http://cmpl.com/actualites";
    String newsHref = "/actualites";
    String key = "news.href";
    Date dateModification = new Date();
    Double priority = Double.valueOf("1.0");

    BDDMockito.doReturn(newsHref).when(service).getI18nValue(Mockito.eq(key), Mockito.eq(locale));

    WebSitemapUrl result = service.computeUrlForMenuNews(dateModification, locale);

    Assert.assertEquals(url, result.getUrl().toExternalForm());
    Assert.assertEquals(dateModification, result.getLastMod());
    Assert.assertEquals(ChangeFreq.YEARLY, result.getChangeFreq());
    Assert.assertEquals(priority, result.getPriority());

  }

  @Test
  public void testComputeUrlForMenuNotNews() throws Exception {
    String url = "http://cmpl.com/techniques";
    String newsHref = "/techniques";
    String key = "technics.href";
    Double priority = Double.valueOf("1.0");

    BDDMockito.doReturn(newsHref).when(service).getI18nValue(Mockito.eq(key), Mockito.eq(locale));

    WebSitemapUrl result = service.computeUrlForMenuNotNews(MENU.TECHNICS, locale);

    Assert.assertEquals(url, result.getUrl().toExternalForm());
    Assert.assertEquals(null, result.getLastMod());
    Assert.assertEquals(ChangeFreq.NEVER, result.getChangeFreq());
    Assert.assertEquals(priority, result.getPriority());
  }

  @Test
  public void testComputeMenuUrls() throws MalformedURLException {
    List<WebSitemapUrl> result = service.computeMenuUrls(locale);

    Assert.assertEquals(MENU.values().length - 1, result.size());

    for (WebSitemapUrl url : result) {
      Assert.assertFalse(url.getUrl().toExternalForm().contains("actualites"));
    }
  }

  @Test
  public void testComputeNewsEntriesUrls() throws MalformedURLException {

    NewsEntryDTO entry1 = new NewsEntryDTOBuilder().toNewsEntryDTO();
    NewsEntryDTO entry2 = new NewsEntryDTOBuilder().toNewsEntryDTO();

    List<NewsEntryDTO> entries = Lists.newArrayList(entry1, entry2);

    BDDMockito.doReturn(entries).when(newsEntryService).getEntities();

    List<WebSitemapUrl> result = service.computeNewsEntriesUrls(locale, entries);

    Assert.assertEquals(entries.size(), result.size());

  }

  @Test
  public void testReadSitemap() throws IOException {
    Path path = Paths.get("src/test/resources/sitemap_test.xml");

    String result = service.readSitemap(path);

    Assert.assertTrue(result.contains("actualites"));
    Assert.assertTrue(result.contains("horaires"));
    Assert.assertTrue(result.contains("rendez-vous"));
    Assert.assertTrue(result.contains("contact"));
    Assert.assertTrue(result.contains("tarifs"));
    Assert.assertTrue(result.contains("techniques"));
    Assert.assertTrue(result.contains("soins_medicaux"));
    Assert.assertTrue(result.contains("gynecologue"));
    Assert.assertTrue(result.contains("centre-medical"));

  }

  @Test
  public void testWriteSitemap() throws IOException {

    Path path = Paths.get("src/test/resources");
    String host = "http://cmpl.com/";
    NewsEntryDTO entry1 = new NewsEntryDTOBuilder().toNewsEntryDTO();
    NewsEntryDTO entry2 = new NewsEntryDTOBuilder().toNewsEntryDTO();

    List<NewsEntryDTO> entries = Lists.newArrayList(entry1, entry2);
    Date dateModification = new Date();

    WebSitemapUrl urlMenu = new WebSitemapUrl.Options(host + "techniques").priority(1d).changeFreq(ChangeFreq.NEVER)
        .build();
    WebSitemapUrl urlNews = new WebSitemapUrl.Options(host + "actualites").lastMod(dateModification).priority(1d)
        .changeFreq(ChangeFreq.YEARLY).build();
    WebSitemapUrl urlNewsEntry = new WebSitemapUrl.Options(host + "actualites/666").lastMod(dateModification)
        .priority(1d).changeFreq(ChangeFreq.YEARLY).build();

    BDDMockito.doReturn(entries).when(newsEntryService).getEntities();
    BDDMockito.doReturn(Lists.newArrayList(urlMenu)).when(service).computeMenuUrls(Mockito.eq(locale));
    BDDMockito.doReturn(urlNews).when(service).computeUrlForMenuNews(Mockito.eq(dateModification), Mockito.eq(locale));
    BDDMockito.doReturn(Lists.newArrayList(urlNewsEntry)).when(service)
        .computeNewsEntriesUrls(Mockito.eq(locale), Mockito.eq(entries));
    BDDMockito.doReturn(dateModification).when(service).computeLastModified(Mockito.eq(entries));

    service.writeSitemap(path, locale);

    Mockito.verify(newsEntryService, Mockito.times(1)).getEntities();
    Mockito.verify(service, Mockito.times(1)).computeMenuUrls(Mockito.eq(locale));
    Mockito.verify(service, Mockito.times(1)).computeUrlForMenuNews(Mockito.eq(dateModification), Mockito.eq(locale));
    Mockito.verify(service, Mockito.times(1)).computeNewsEntriesUrls(Mockito.eq(locale), Mockito.eq(entries));
    Mockito.verify(service, Mockito.times(1)).computeLastModified(Mockito.eq(entries));

    File sitemap = new File("src/test/resources/sitemap.xml");
    if (sitemap.exists()) {
      sitemap.delete();
    }

  }

  @Test
  public void testCreateSiteMap() throws IOException, BaseException {
    String sitemap = "<?xml version='1.0' encoding='UTF-8'?><urlset><url><loc>http://cmpl.com/</loc><changefreq>never</changefreq><priority>1.0</priority></url></urlset>";

    BDDMockito.doReturn(sitemap).when(service).readSitemap(Mockito.any(Path.class));
    BDDMockito.doNothing().when(service).writeSitemap(Mockito.any(Path.class), Mockito.eq(locale));

    String result = service.createSiteMap(locale);

    Assert.assertEquals(sitemap, result);
  }

  @Test
  public void testCreateSiteMap_Exception() throws Exception {

    exception.expect(BaseException.class);
    BDDMockito.doThrow(new IOException("")).when(service).writeSitemap(Mockito.any(Path.class), Mockito.eq(locale));

    service.createSiteMap(locale);

  }

}
