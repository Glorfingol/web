package cmpl.web.translator.impl;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.builder.NewsEntryDTOBuilder;
import cmpl.web.model.facebook.FacebookImportPost;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;
import cmpl.web.model.news.dto.NewsEntryDTO;

@RunWith(MockitoJUnitRunner.class)
public class FacebookImportTranslatorImplTest {

  @InjectMocks
  @Spy
  private FacebookImportTranslatorImpl translator;

  @Test
  public void testFromRequestToPosts() throws Exception {
    FacebookImportRequest request = new FacebookImportRequest();

    FacebookImportPost post = new FacebookImportPost();

    request.setPostsToImport(Lists.newArrayList(post));

    List<FacebookImportPost> result = translator.fromRequestToPosts(request);

    Assert.assertEquals(request.getPostsToImport(), result);
  }

  @Test
  public void testFromDTOToResponse() throws Exception {

    NewsEntryDTO dto = new NewsEntryDTOBuilder().id(123456789L).toNewsEntryDTO();
    List<NewsEntryDTO> dtos = Lists.newArrayList(dto);

    FacebookImportResponse result = translator.fromDTOToResponse(dtos);

    Assert.assertEquals(dtos, result.getCreatedNewsEntries());

  }

}
