package cmpl.web.factory.impl;

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
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.page.PAGE;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

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

    MetaElement metaElement = metaElementFactory.computeMetaElement(name, content);

    Assert.assertEquals(name, metaElement.getName());
    Assert.assertEquals(content, metaElement.getContent());
    Assert.assertNull(metaElement.getProperty());
  }

  @Test
  public void testComputeLanguage() throws Exception {

    String name = "language";

    MetaElement language = new MetaElementBuilder().name(name).content(locale.getLanguage()).toMetaElement();

    BDDMockito.doReturn(language).when(metaElementFactory)
        .computeMetaElement(Mockito.eq(name), Mockito.eq(locale.getLanguage()));

    MetaElement result = metaElementFactory.computeLanguage(locale);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(locale.getLanguage(), result.getContent());
  }

  @Test
  public void testComputeViewPort() throws Exception {
    String name = "viewport";
    String content = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    MetaElement viewport = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementFactory).computeMetaElement(Mockito.eq(name), Mockito.eq(content));

    MetaElement result = metaElementFactory.computeViewPort();

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeDescription() throws Exception {
    String name = "description";
    String content = "test";

    BDDMockito.doReturn("test").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGE.CENTER.getDescription()), Mockito.eq(locale));

    MetaElement description = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(description).when(metaElementFactory).computeMetaElement(Mockito.eq(name), Mockito.anyString());

    MetaElement result = metaElementFactory.computeDescription(locale, PAGE.CENTER);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeTitle() throws Exception {
    String name = "title";
    String content = "test";

    BDDMockito.doReturn("test").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGE.CENTER.getTitle()), Mockito.eq(locale));

    MetaElement description = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(description).when(metaElementFactory).computeMetaElement(Mockito.eq(name), Mockito.anyString());

    MetaElement result = metaElementFactory.computeTitle(locale, PAGE.CENTER);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeMetaElementsForBackPage() throws Exception {

    String viewPortName = "viewport";
    String viewPortContent = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    String languageName = "viewport";
    String languageContent = locale.getLanguage();

    MetaElement viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElement language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementFactory).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementFactory).computeLanguage(Mockito.eq(locale));

    List<MetaElement> result = metaElementFactory.computeMetaElementsForBackPage(locale);

    Assert.assertEquals(2, result.size());

    Assert.assertEquals(language, result.get(0));
    Assert.assertEquals(viewport, result.get(1));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementFactory, Mockito.times(0))
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(metaElementFactory, Mockito.times(0)).computeTitle(Mockito.eq(locale), Mockito.any(PAGE.class));

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

    MetaElement viewport = new MetaElementBuilder().name(viewPortName).content(viewPortContent).toMetaElement();
    MetaElement language = new MetaElementBuilder().name(languageName).content(languageContent).toMetaElement();
    MetaElement title = new MetaElementBuilder().name(titleName).content(titleContent).toMetaElement();
    MetaElement description = new MetaElementBuilder().name(descriptionName).content(descriptionContent)
        .toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementFactory).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementFactory).computeLanguage(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(metaElementFactory).computeTitle(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(description).when(metaElementFactory)
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGE.class));

    List<MetaElement> result = metaElementFactory.computeMetaElementsForPage(locale, PAGE.INDEX);

    Assert.assertEquals(4, result.size());

    Assert.assertEquals(title, result.get(0));
    Assert.assertEquals(description, result.get(1));
    Assert.assertEquals(language, result.get(2));
    Assert.assertEquals(viewport, result.get(3));

    Mockito.verify(metaElementFactory, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementFactory, Mockito.times(1))
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(metaElementFactory, Mockito.times(1)).computeTitle(Mockito.eq(locale), Mockito.any(PAGE.class));
  }

  @Test
  public void testComputeOpenGraphMetaElement() throws Exception {
    String property = "property";
    String content = "content";

    MetaElement metaElement = metaElementFactory.computeOpenGraphMetaElement(property, content);

    Assert.assertEquals(property, metaElement.getProperty());
    Assert.assertEquals(content, metaElement.getContent());
    Assert.assertNull(metaElement.getName());
  }

  @Test
  public void testComputeOpenGraphUrlForNewsEntry_Not_Null_NewsEntry() throws Exception {
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();

    MetaElement openGraphUrl = new MetaElement();
    openGraphUrl.setProperty("og:url");
    openGraphUrl.setContent("http://cm-pl.com/actualites/123456789");

    BDDMockito.doReturn("/actualites").when(metaElementFactory)
        .getI18nValue(Mockito.eq(MENU.NEWS.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphUrl).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:url"), Mockito.eq("http://cm-pl.com/actualites/123456789"));

    MetaElement result = metaElementFactory.computeOpenGraphUrlForNewsEntry(locale, newsEntry);

    Assert.assertEquals(openGraphUrl.getProperty(), result.getProperty());
    Assert.assertEquals(openGraphUrl.getContent(), result.getContent());
  }

  @Test
  public void testComputeOpenGraphUrl() throws Exception {
    MetaElement openGraphUrl = new MetaElement();
    openGraphUrl.setProperty("og:url");
    openGraphUrl.setContent("http://cm-pl.com/techniques");

    BDDMockito.doReturn("/techniques").when(metaElementFactory)
        .getI18nValue(Mockito.eq(MENU.TECHNICS.getHref()), Mockito.eq(locale));
    BDDMockito.doReturn(openGraphUrl).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:url"), Mockito.eq("http://cm-pl.com/techniques"));

    MetaElement result = metaElementFactory.computeOpenGraphUrl(locale, PAGE.TECHNICS);

    Assert.assertEquals("og:url", result.getProperty());
    Assert.assertEquals("http://cm-pl.com/techniques", result.getContent());
  }

  @Test
  public void testComputeOpenGraphType() throws Exception {

    MetaElement openGraphType = new MetaElement();
    openGraphType.setProperty("og:type");
    openGraphType.setContent("website");

    BDDMockito.doReturn(openGraphType).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:type"), Mockito.eq("website"));

    MetaElement result = metaElementFactory.computeOpenGraphType();

    Assert.assertEquals(openGraphType.getProperty(), result.getProperty());
    Assert.assertEquals(openGraphType.getContent(), result.getContent());
  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_With_Content() throws Exception {
    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).content("someContent").toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).toNewsEntryDTO();

    MetaElement metaDescription = new MetaElement();
    metaDescription.setName("description");
    metaDescription.setContent("someContent");

    BDDMockito.doReturn(metaDescription).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("description"), Mockito.eq("someContent"));

    MetaElement result = metaElementFactory.computeDescriptionForNewsEntry(locale, PAGE.NEWS_ENTRY, newsEntry);

    Assert.assertEquals(metaDescription, result);

  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_Without_Content_Null() throws Exception {
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();

    MetaElement metaDescription = new MetaElement();
    metaDescription.setName("description");
    metaDescription.setContent("someContent");

    BDDMockito.doReturn("someContent").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGE.NEWS_ENTRY.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(metaDescription).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("description"), Mockito.eq("someContent"));

    MetaElement result = metaElementFactory.computeDescriptionForNewsEntry(locale, PAGE.NEWS_ENTRY, newsEntry);

    Assert.assertEquals(metaDescription, result);
  }

  @Test
  public void testComputeOpenGraphDescriptionForNewsEntry_Without_Content_Empty() throws Exception {
    NewsContentDTO newsContent = new NewsContentDTOBuilder().id(123456789L).toNewsContentDTO();
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).newsContent(newsContent).toNewsEntryDTO();

    MetaElement metaDescription = new MetaElement();
    metaDescription.setName("description");
    metaDescription.setContent("someContent");

    BDDMockito.doReturn("someContent").when(metaElementFactory)
        .getI18nValue(Mockito.eq(PAGE.NEWS_ENTRY.getDescription()), Mockito.eq(locale));
    BDDMockito.doReturn(metaDescription).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("description"), Mockito.eq("someContent"));

    MetaElement result = metaElementFactory.computeDescriptionForNewsEntry(locale, PAGE.NEWS_ENTRY, newsEntry);

    Assert.assertEquals(metaDescription, result);
  }

  @Test
  public void testComputeTitleForNewsEntry() throws Exception {
    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).title("someTitle").toNewsEntryDTO();

    MetaElement metaTitle = new MetaElement();
    metaTitle.setName("title");
    metaTitle.setContent("someTitle");

    BDDMockito.doReturn(metaTitle).when(metaElementFactory)
        .computeMetaElement(Mockito.eq("title"), Mockito.eq("someTitle"));

    MetaElement result = metaElementFactory.computeTitleForNewsEntry(newsEntry);

    Assert.assertEquals(metaTitle, result);

  }
}
