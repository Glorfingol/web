package cmpl.web.meta;

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
import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryDTO;
import cmpl.web.news.NewsEntryService;

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
  public void testComputeOpenGraphMetaElement() throws Exception {
    String property = "property";
    String content = "content";

    MetaElementToDelete metaElement = metaElementFactory.computeOpenGraphMetaElement(property, content);

    Assert.assertEquals(property, metaElement.getProperty());
    Assert.assertEquals(content, metaElement.getContent());
    Assert.assertNull(metaElement.getName());
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
  public void testComputeOpenGraphTitleForNewsEntry() throws Exception {

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).title("someTitle").toNewsEntryDTO();

    MetaElementToDelete openGraphTitle = new MetaElementBuilder().property("og:title").content("someTitle")
        .toMetaElement();

    BDDMockito.doReturn(openGraphTitle).when(metaElementFactory)
        .computeOpenGraphMetaElement(Mockito.eq("og:title"), Mockito.eq("someTitle"));

    MetaElementToDelete result = metaElementFactory.computeOpenGraphTitleForNewsEntry(newsEntry);

    Assert.assertEquals(openGraphTitle, result);

  }

}
