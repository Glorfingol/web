package cmpl.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.message.WebMessageSource;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.service.NewsEntryService;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapUrl;

@RunWith(MockitoJUnitRunner.class)
public class SitemapServiceImplTest {

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
}
