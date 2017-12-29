package com.cmpl.web.facebook;

import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.news.NewsEntryDTO;
import com.cmpl.web.news.NewsEntryDTOBuilder;

@RunWith(MockitoJUnitRunner.class)
public class FacebookDispatcherImplTest {

  @Mock
  private FacebookImportService facebookImportService;

  @Mock
  private FacebookImportTranslator facebookImportTranslator;

  @InjectMocks
  @Spy
  private FacebookDispatcherImpl facebookDispatcher;

  @Test
  public void testCreateEntity() throws Exception {

    FacebookImportRequest facebookImportRequest = new FacebookImportRequest();
    FacebookImportPost post = new FacebookImportPost();

    List<FacebookImportPost> posts = Lists.newArrayList(post);
    facebookImportRequest.setPostsToImport(posts);

    FacebookImportResponse response = new FacebookImportResponse();

    NewsEntryDTO newsEntry = new NewsEntryDTOBuilder().id(123456789L).build();

    BDDMockito.doReturn(posts).when(facebookImportTranslator).fromRequestToPosts(BDDMockito.eq(facebookImportRequest));
    BDDMockito.doReturn(Lists.newArrayList(newsEntry)).when(facebookImportService)
        .importFacebookPost(BDDMockito.anyList(), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(response).when(facebookImportTranslator).fromDTOToResponse(BDDMockito.anyList());

    FacebookImportResponse result = facebookDispatcher.createEntity(facebookImportRequest, Locale.FRANCE);

    Assert.assertEquals(response, result);

    BDDMockito.verify(facebookImportTranslator, BDDMockito.times(1)).fromRequestToPosts(
        BDDMockito.eq(facebookImportRequest));
    BDDMockito.verify(facebookImportTranslator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.anyList());
    BDDMockito.verify(facebookImportService, BDDMockito.times(1)).importFacebookPost(BDDMockito.anyList(),
        BDDMockito.any(Locale.class));
  }
}
