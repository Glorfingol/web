package cmpl.web.dispatcher.impl;

import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
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
import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.service.FacebookImportService;
import cmpl.web.translator.FacebookImportTranslator;

@RunWith(MockitoJUnitRunner.class)
public class FacebookDispatcherImplTest {

  @Mock
  private FacebookImportService facebookImportService;

  @Mock
  private FacebookImportTranslator facebookImportTranslator;

  @InjectMocks
  @Spy
  private FacebookDispatcherImpl facebookDispatcher;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testCreateEntity() throws Exception {

    FacebookImportRequest facebookImportRequest = new FacebookImportRequest();
    FacebookImportPost post = new FacebookImportPost();

    List<FacebookImportPost> posts = Lists.newArrayList(post);
    facebookImportRequest.setPostsToImport(posts);

    FacebookImportResponse response = new FacebookImportResponse();

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();

    BDDMockito.doReturn(posts).when(facebookImportTranslator).fromRequestToPosts(Mockito.eq(facebookImportRequest));
    BDDMockito.doReturn(Lists.newArrayList(newsEntry)).when(facebookImportService)
        .importFacebookPost(Mockito.anyListOf(FacebookImportPost.class), Mockito.any(Locale.class));
    BDDMockito.doReturn(response).when(facebookImportTranslator)
        .fromDTOToResponse(Mockito.anyListOf(NewsEntryDTO.class));

    FacebookImportResponse result = facebookDispatcher.createEntity(facebookImportRequest, locale);

    Assert.assertEquals(response, result);

    Mockito.verify(facebookImportTranslator, Mockito.times(1)).fromRequestToPosts(Mockito.eq(facebookImportRequest));
    Mockito.verify(facebookImportTranslator, Mockito.times(1)).fromDTOToResponse(Mockito.anyListOf(NewsEntryDTO.class));
    Mockito.verify(facebookImportService, Mockito.times(1)).importFacebookPost(
        Mockito.anyListOf(FacebookImportPost.class), Mockito.any(Locale.class));
  }
}
