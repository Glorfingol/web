package cmpl.web.meta;

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

import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.builder.NewsContentDTOBuilder;
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.menu.MENUS;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsContentDTO;
import cmpl.web.news.NewsEntryDTO;
import cmpl.web.news.NewsEntryService;
import cmpl.web.page.PAGES;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private MetaElementFactoryImpl metaElementFactory;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeMetaElement() throws Exception {

    String name = "name";
    String content = "content";

    MetaElementToDelete metaElement = metaElementFactory.computeMetaElement(name, content);

    Assert.assertEquals(name, metaElement.getName());
    Assert.assertEquals(content, metaElement.getContent());
    Assert.assertNull(metaElement.getProperty());
  }

  @Test
  public void testComputeLanguage() throws Exception {

    String name = "language";

    MetaElementToDelete language = new MetaElementBuilder().name(name).content(locale.getLanguage()).toMetaElement();

    BDDMockito.doReturn(language).when(metaElementFactory)
        .computeMetaElement(Mockito.eq(name), Mockito.eq(locale.getLanguage()));

    MetaElementToDelete result = metaElementFactory.computeLanguage(locale);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(locale.getLanguage(), result.getContent());
  }

  @Test
  public void testComputeViewPort() throws Exception {
    String name = "viewport";
    String content = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    MetaElementToDelete viewport = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementFactory).computeMetaElement(Mockito.eq(name), Mockito.eq(content));

    MetaElementToDelete result = metaElementFactory.computeViewPort();

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeDescription() throws Exception {
    String name = "description";
    String content = "test";

    BDDMockito.doReturn("test").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.CENTER.getDescription()), Mockito.eq(locale));

    MetaElementToDelete description = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(description).when(metaElementFactory).computeMetaElement(Mockito.eq(name), Mockito.anyString());

    MetaElementToDelete result = metaElementFactory.computeDescription(locale, PAGES.CENTER);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeTitle() throws Exception {
    String name = "title";
    String content = "test";

    BDDMockito.doReturn("test").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.CENTER.getTitle()), Mockito.eq(locale));

    MetaElementToDelete description = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(description).when(metaElementFactory).computeMetaElement(Mockito.eq(name), Mockito.anyString());

    MetaElementToDelete result = metaElementFactory.computeTitle(locale, PAGES.CENTER);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeMetaElementsForBackPage() throws Exception {

    String viewPortName = "viewport";
    String viewPortContent = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    String languageName = "viewport";
    String languageContent = locale.getLanguage();

    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementFactory).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementFactory).computeLanguage(Mockito.eq(locale));

    List<MetaElementToDelete> result = metaElementFactory.computeMetaElementsForBackPage(locale);

    Assert.assertEquals(2, result.size());

    Assert.assertEquals(language, result.get(0));
    Assert.assertEquals(viewport, result.get(1));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementFactory, Mockito.times(0)).computeDescription(Mockito.eq(locale),
        Mockito.any(PAGES.class));
    Mockito.verify(metaElementFactory, Mockito.times(0)).computeTitle(Mockito.eq(locale), Mockito.any(PAGES.class));

  }

  @Test
  public void testComputeMetaElementsForPage() throws Exception {
    String viewPortName = "viewport";
    String viewPortContent = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    String languageName = "viewport";
    String languageContent = locale.getLanguage();

    String titleName = "title";
    String titleContent = "title";

    String descriptionName = "description";
    String descriptionContent = "description";

    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElementToDelete title = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElementToDelete description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementFactory).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementFactory).computeLanguage(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(metaElementFactory).computeTitle(Mockito.eq(locale), Mockito.any(PAGES.class));
    BDDMockito.doReturn(description).when(metaElementFactory)
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGES.class));

    List<MetaElementToDelete> result = metaElementFactory.computeMetaElementsForPage(locale, PAGES.INDEX);

    Assert.assertEquals(4, result.size());

    Assert.assertEquals(title, result.get(0));
    Assert.assertEquals(description, result.get(1));
    Assert.assertEquals(language, result.get(2));
    Assert.assertEquals(viewport, result.get(3));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeDescription(Mockito.eq(locale),
        Mockito.any(PAGES.class));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeTitle(Mockito.eq(locale), Mockito.any(PAGES.class));
  }

  @Test
  public void testComputeOpenGraphMetaElement() throws Exception {
    String property = "property";
    String content = "content";

    MetaElementToDelete metaElement = metaElementFactory.computeOpenGraphMetaElement(property, content);

    Assert.assertEquals(property, metaElement.getProperty());
    Assert.assertEquals(content, metaElement.getContent());
    Assert.assertNull(metaElement.getName());
  }

  @Test
  public void testComputeOpenGraphUrlForNewsEntry_Not_Null_NewsEntry() throws Exception {
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();

    MetaElementToDelete openGraphUrl = new MetaElementBuilder().property("og:url")
        .content("http://cm-pl.com/actualites/123456789").toMetaElement();

    BDDMockito.doReturn("/actualites").when(metaElementFactory)
        .getI18nValue(Mockito.eq(MENUS.NEWS.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphUrl).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:url"), Mockito.eq("http://cm-pl.com/actualites/123456789"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphUrlForNewsEntry(locale, newsEntry);

    Assert.assertEquals(openGraphUrl.getProperty(), result.getProperty());
    Assert.assertEquals(openGraphUrl.getContent(), result.getContent());
  }

  @Test
  public void testComputeOpenGraphUrl() throws Exception {

    MetaElementToDelete openGraphUrl = new MetaElementBuilder().property("og:url")
        .content("http://cm-pl.com/techniques").toMetaElement();

    BDDMockito.doReturn("/techniques").when(metaElementFactory)
        .getI18nValue(Mockito.eq(MENUS.TECHNICS.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphUrl).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:url"), Mockito.eq("http://cm-pl.com/techniques"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphUrl(locale, PAGES.TECHNICS);

    Assert.assertEquals("og:url", result.getProperty());
    Assert.assertEquals("http://cm-pl.com/techniques", result.getContent());
  }

  @Test
  public void testComputeOpenGraphType() throws Exception {

    MetaElementToDelete openGraphType = new MetaElementBuilder().property("og:type").content("website").toMetaElement();

    BDDMockito.doReturn(openGraphType).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:type"), Mockito.eq("website"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphType();

    Assert.assertEquals(openGraphType.getProperty(), result.getProperty());
    Assert.assertEquals(openGraphType.getContent(), result.getContent());
  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_With_Content() throws Exception {
    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).content("someContent").toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).toNewsEntryDTO();

    MetaElementToDelete metaDescription = new MetaElementBuilder().property("description").content("someContent")
        .toMetaElement();

    BDDMockito.doReturn(metaDescription).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("description"), Mockito.eq("someContent"));

    MetaElementToDelete result = metaElementFactory.computeDescriptionForNewsEntry(locale, PAGES.NEWS_ENTRY, newsEntry);

    Assert.assertEquals(metaDescription, result);

  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_Without_Content_Null() throws Exception {
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();

    MetaElementToDelete metaDescription = new MetaElementBuilder().property("description").content("someContent")
        .toMetaElement();

    BDDMockito.doReturn("someContent").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.NEWS_ENTRY.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(metaDescription).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("description"), Mockito.eq("someContent"));

    MetaElementToDelete result = metaElementFactory.computeDescriptionForNewsEntry(locale, PAGES.NEWS_ENTRY, newsEntry);

    Assert.assertEquals(metaDescription, result);
  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_Without_Content_Empty() throws Exception {
    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).toNewsEntryDTO();

    MetaElementToDelete metaDescription = new MetaElementBuilder().property("description").content("someContent")
        .toMetaElement();

    BDDMockito.doReturn("someContent").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.NEWS_ENTRY.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(metaDescription).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("description"), Mockito.eq("someContent"));

    MetaElementToDelete result = metaElementFactory.computeDescriptionForNewsEntry(locale, PAGES.NEWS_ENTRY, newsEntry);

    Assert.assertEquals(metaDescription, result);
  }

  @Test
  public void testComputeTitleForNewsEntry() throws Exception {
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).title("someTitle").toNewsEntryDTO();

    MetaElementToDelete metaTitle = new MetaElementBuilder().property("title").content("someTitle").toMetaElement();

    BDDMockito.doReturn(metaTitle).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("title"), Mockito.eq("someTitle"));

    MetaElementToDelete result = metaElementFactory.computeTitleForNewsEntry(newsEntry);

    Assert.assertEquals(metaTitle, result);

  }

  @Test
  public void testComputeOpenGraphTitle() throws Exception {

    MetaElementToDelete openGraphTitle = new MetaElementBuilder().property("og:title").content("someTitle")
        .toMetaElement();

    BDDMockito.doReturn("someTitle").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.INDEX.getTitle()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphTitle).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:title"), Mockito.eq("someTitle"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphTitle(locale, PAGES.INDEX);

    Assert.assertEquals(openGraphTitle, result);
  }

  @Test
  public void testComputeOpenGraphDescription() throws Exception {

    MetaElementToDelete openGraphDescription = new MetaElementBuilder().property("og:description")
        .content("description").toMetaElement();

    BDDMockito.doReturn("description").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.INDEX.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphDescription).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:description"), Mockito.eq("description"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphDescription(locale, PAGES.INDEX);

    Assert.assertEquals(openGraphDescription, result);
  }

  @Test
  public void testComputeOpenGraphTitleForNewsEntry() throws Exception {

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).title("someTitle").toNewsEntryDTO();

    MetaElementToDelete openGraphTitle = new MetaElementBuilder().property("og:title").content("someTitle")
        .toMetaElement();

    BDDMockito.doReturn(openGraphTitle).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:title"), Mockito.eq("someTitle"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphTitleForNewsEntry(newsEntry);

    Assert.assertEquals(openGraphTitle, result);

  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_Content() throws Exception {

    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).content("someContent").toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).toNewsEntryDTO();

    MetaElementToDelete openGraphMetaDescription = new MetaElementBuilder().property("og:description")
        .content("someContent").toMetaElement();

    BDDMockito.doReturn(openGraphMetaDescription).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:description"), Mockito.eq("someContent"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphDescriptionForNewsEntry(locale, PAGES.NEWS_ENTRY,
        newsEntry);

    Assert.assertEquals(openGraphMetaDescription, result);
  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_Content_Empty() throws Exception {

    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).toNewsEntryDTO();

    MetaElementToDelete openGraphMetaDescription = new MetaElementBuilder().property("og:description")
        .content("someContent").toMetaElement();

    BDDMockito.doReturn("someContent").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.NEWS_ENTRY.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphMetaDescription).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:description"), Mockito.eq("someContent"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphDescriptionForNewsEntry(locale, PAGES.NEWS_ENTRY,
        newsEntry);

    Assert.assertEquals(openGraphMetaDescription, result);

  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_No_Content() throws Exception {

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();

    MetaElementToDelete openGraphMetaDescription = new MetaElementBuilder().property("og:description")
        .content("someContent").toMetaElement();

    BDDMockito.doReturn("someContent").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGES.NEWS_ENTRY.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphMetaDescription).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:description"), Mockito.eq("someContent"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphDescriptionForNewsEntry(locale, PAGES.NEWS_ENTRY,
        newsEntry);

    Assert.assertEquals(openGraphMetaDescription, result);
  }

  @Test
  public void testComputeOpenGraphMetaElementsNewsEntry() throws Exception {

    String titleProperty = "og:title";
    String titleContent = "title";

    String typeProperty = "og:type";
    String typeContent = "website";

    String urlProperty = "og:url";
    String urlContent = "http://cm-pl.com";

    String descriptionProperty = "og:description";
    String descriptionContent = "description";

    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).content("someContent").toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).title("someTitle")
        .toNewsEntryDTO();
    MetaElementToDelete openGraphTitle = new MetaElementBuilder().property(titleProperty).content(titleContent)
        .toMetaElement();
    MetaElementToDelete openGraphType = new MetaElementBuilder().property(typeProperty).content(typeContent)
        .toMetaElement();
    MetaElementToDelete openGraphUrl = new MetaElementBuilder().property(urlProperty).content(urlContent)
        .toMetaElement();
    MetaElementToDelete openGraphDescription = new MetaElementBuilder().property(descriptionProperty)
        .content(descriptionContent).toMetaElement();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.eq(123456789L));
    BDDMockito.doReturn(openGraphTitle).when(metaElementFactory)
        .computeOpenGraphTitleForNewsEntry(Mockito.eq(newsEntry));
    BDDMockito.doReturn(openGraphType).when(metaElementFactory).computeOpenGraphType();
    BDDMockito.doReturn(openGraphUrl).when(metaElementFactory)
        .computeOpenGraphUrlForNewsEntry(Mockito.eq(locale), Mockito.eq(newsEntry));
    BDDMockito.doReturn(openGraphDescription).when(metaElementFactory)
        .computeOpenGraphDescriptionForNewsEntry(Mockito.eq(locale), Mockito.eq(PAGES.INDEX), Mockito.eq(newsEntry));

    List<MetaElementToDelete> result = metaElementFactory.computeOpenGraphMetaElementsNewsEntry(locale, PAGES.INDEX,
        "123456789");
    Assert.assertEquals(4, result.size());

    Assert.assertEquals(openGraphTitle, result.get(0));
    Assert.assertEquals(openGraphType, result.get(1));
    Assert.assertEquals(openGraphUrl, result.get(2));
    Assert.assertEquals(openGraphDescription, result.get(3));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphType();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphTitleForNewsEntry(Mockito.eq(newsEntry));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphUrlForNewsEntry(Mockito.eq(locale),
        Mockito.eq(newsEntry));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphDescriptionForNewsEntry(Mockito.eq(locale),
        Mockito.eq(PAGES.INDEX), Mockito.eq(newsEntry));
  }

  @Test
  public void testComputeMetaElementsForNewsEntry() throws Exception {
    String viewPortName = "viewport";
    String viewPortContent = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    String languageName = "viewport";
    String languageContent = locale.getLanguage();

    String titleName = "title";
    String titleContent = "title";

    String descriptionName = "description";
    String descriptionContent = "description";

    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).content("someContent").toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).title("someTitle")
        .toNewsEntryDTO();
    MetaElementToDelete viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElementToDelete language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElementToDelete title = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElementToDelete description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).getEntity(Mockito.eq(123456789L));
    BDDMockito.doReturn(viewport).when(metaElementFactory).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementFactory).computeLanguage(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(metaElementFactory).computeTitleForNewsEntry(Mockito.eq(newsEntry));
    BDDMockito.doReturn(description).when(metaElementFactory)
        .computeDescriptionForNewsEntry(Mockito.eq(locale), Mockito.any(PAGES.class), Mockito.eq(newsEntry));

    List<MetaElementToDelete> result = metaElementFactory.computeMetaElementsForNewsEntry(locale, PAGES.INDEX,
        "123456789");
    Assert.assertEquals(4, result.size());

    Assert.assertEquals(title, result.get(0));
    Assert.assertEquals(description, result.get(1));
    Assert.assertEquals(language, result.get(2));
    Assert.assertEquals(viewport, result.get(3));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeDescriptionForNewsEntry(Mockito.eq(locale),
        Mockito.any(PAGES.class), Mockito.eq(newsEntry));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeTitleForNewsEntry(Mockito.eq(newsEntry));
  }

  @Test
  public void testComputeOpenGraphMetaElementsForPage() throws Exception {
    String titleProperty = "og:title";
    String titleContent = "title";

    String typeProperty = "og:type";
    String typeContent = "website";

    String urlProperty = "og:url";
    String urlContent = "http://cm-pl.com";

    String descriptionProperty = "og:description";
    String descriptionContent = "description";

    MetaElementToDelete openGraphTitle = new MetaElementBuilder().property(titleProperty).content(titleContent)
        .toMetaElement();
    MetaElementToDelete openGraphType = new MetaElementBuilder().property(typeProperty).content(typeContent)
        .toMetaElement();
    MetaElementToDelete openGraphUrl = new MetaElementBuilder().property(urlProperty).content(urlContent)
        .toMetaElement();
    MetaElementToDelete openGraphDescription = new MetaElementBuilder().property(descriptionProperty)
        .content(descriptionContent).toMetaElement();

    BDDMockito.doReturn(openGraphTitle).when(metaElementFactory)
        .computeOpenGraphTitle(Mockito.eq(locale), Mockito.eq(PAGES.INDEX));
    BDDMockito.doReturn(openGraphType).when(metaElementFactory).computeOpenGraphType();
    BDDMockito.doReturn(openGraphUrl).when(metaElementFactory)
        .computeOpenGraphUrl(Mockito.eq(locale), Mockito.eq(PAGES.INDEX));
    BDDMockito.doReturn(openGraphDescription).when(metaElementFactory)
        .computeOpenGraphDescription(Mockito.eq(locale), Mockito.eq(PAGES.INDEX));

    List<MetaElementToDelete> result = metaElementFactory.computeOpenGraphMetaElementsForPage(locale, PAGES.INDEX);
    Assert.assertEquals(4, result.size());

    Assert.assertEquals(openGraphTitle, result.get(0));
    Assert.assertEquals(openGraphType, result.get(1));
    Assert.assertEquals(openGraphUrl, result.get(2));
    Assert.assertEquals(openGraphDescription, result.get(3));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphType();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphTitle(Mockito.eq(locale),
        Mockito.eq(PAGES.INDEX));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphUrl(Mockito.eq(locale),
        Mockito.any(PAGES.class));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeOpenGraphDescription(Mockito.eq(locale),
        Mockito.eq(PAGES.INDEX));
  }
}
