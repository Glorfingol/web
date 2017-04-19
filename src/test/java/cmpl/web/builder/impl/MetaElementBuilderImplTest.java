package cmpl.web.builder.impl;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementBuilderImplTest {

  private WebMessageSourceImpl messageBundle;

  private MetaElementBuilderImpl metaElementBuilder;

  private Locale locale;

  @Before
  public void setUp() {
    messageBundle = Mockito.mock(WebMessageSourceImpl.class);
    metaElementBuilder = MetaElementBuilderImpl.fromMessageSource(messageBundle);
    metaElementBuilder = Mockito.spy(metaElementBuilder);
    locale = Locale.FRANCE;
  }

  @Test
  public void testComputeMetaElement() throws Exception {

    String name = "name";
    String content = "content";

    MetaElement metaElement = metaElementBuilder.computeMetaElement(name, content);

    Assert.assertEquals(name, metaElement.getName());
    Assert.assertEquals(content, metaElement.getContent());
  }

  @Test
  public void testComputeLanguage() throws Exception {

    String name = "language";

    MetaElement language = new MetaElementBuilder().name(name).content(locale.getLanguage()).toMetaElement();

    BDDMockito.doReturn(language).when(metaElementBuilder)
        .computeMetaElement(Mockito.eq(name), Mockito.eq(locale.getLanguage()));

    MetaElement result = metaElementBuilder.computeLanguage(locale);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(locale.getLanguage(), result.getContent());
  }

  @Test
  public void testComputeViewPort() throws Exception {
    String name = "viewport";
    String content = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

    MetaElement viewport = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(viewport).when(metaElementBuilder).computeMetaElement(Mockito.eq(name), Mockito.eq(content));

    MetaElement result = metaElementBuilder.computeViewPort();

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeDescription() throws Exception {
    String name = "description";
    String content = "test";

    BDDMockito.doReturn("test").when(metaElementBuilder)
        .getI18nValue(Mockito.eq(PAGE.CENTER.getDescription()), Mockito.eq(locale));

    MetaElement description = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(description).when(metaElementBuilder).computeMetaElement(Mockito.eq(name), Mockito.anyString());

    MetaElement result = metaElementBuilder.computeDescription(locale, PAGE.CENTER);

    Assert.assertEquals(name, result.getName());
    Assert.assertEquals(content, result.getContent());
  }

  @Test
  public void testComputeTitle() throws Exception {
    String name = "title";
    String content = "test";

    BDDMockito.doReturn("test").when(metaElementBuilder)
        .getI18nValue(Mockito.eq(PAGE.CENTER.getTitle()), Mockito.eq(locale));

    MetaElement description = new MetaElementBuilder().name(name).content(content).toMetaElement();

    BDDMockito.doReturn(description).when(metaElementBuilder).computeMetaElement(Mockito.eq(name), Mockito.anyString());

    MetaElement result = metaElementBuilder.computeTitle(locale, PAGE.CENTER);

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

    BDDMockito.doReturn(viewport).when(metaElementBuilder).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementBuilder).computeLanguage(Mockito.eq(locale));

    List<MetaElement> result = metaElementBuilder.computeMetaElementsForBackPage(locale);

    Assert.assertEquals(2, result.size());

    Assert.assertEquals(language, result.get(0));
    Assert.assertEquals(viewport, result.get(1));

    Mockito.verify(metaElementBuilder, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementBuilder, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementBuilder, Mockito.times(0))
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(metaElementBuilder, Mockito.times(0)).computeTitle(Mockito.eq(locale), Mockito.any(PAGE.class));

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

    BDDMockito.doReturn(viewport).when(metaElementBuilder).computeViewPort();
    BDDMockito.doReturn(language).when(metaElementBuilder).computeLanguage(Mockito.eq(locale));
    BDDMockito.doReturn(title).when(metaElementBuilder).computeTitle(Mockito.eq(locale), Mockito.any(PAGE.class));
    BDDMockito.doReturn(description).when(metaElementBuilder)
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGE.class));

    List<MetaElement> result = metaElementBuilder.computeMetaElementsForPage(locale, PAGE.INDEX);

    Assert.assertEquals(4, result.size());

    Assert.assertEquals(title, result.get(0));
    Assert.assertEquals(description, result.get(1));
    Assert.assertEquals(language, result.get(2));
    Assert.assertEquals(viewport, result.get(3));

    Mockito.verify(metaElementBuilder, Mockito.times(1)).computeViewPort();
    Mockito.verify(metaElementBuilder, Mockito.times(1)).computeLanguage(Mockito.eq(locale));
    Mockito.verify(metaElementBuilder, Mockito.times(1))
        .computeDescription(Mockito.eq(locale), Mockito.any(PAGE.class));
    Mockito.verify(metaElementBuilder, Mockito.times(1)).computeTitle(Mockito.eq(locale), Mockito.any(PAGE.class));
  }

}
