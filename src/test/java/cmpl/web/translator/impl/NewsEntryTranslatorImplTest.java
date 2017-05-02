package cmpl.web.translator.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.builder.NewsContentRequestBuilder;
import cmpl.web.builder.NewsEntryRequestBuilder;
import cmpl.web.builder.NewsImageRequestBuilder;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.model.news.rest.news.NewsContentRequest;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;
import cmpl.web.model.news.rest.news.NewsImageRequest;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryTranslatorImplTest {

  @InjectMocks
  @Spy
  private NewsEntryTranslatorImpl translator;

  @Test
  public void testFromImageRequestToDTO() throws Exception {

    NewsImageRequest imageRequest = new NewsImageRequestBuilder().src("someSrc").alt("someAlt").legend("someLegend")
        .creationDate(new Date()).modificationDate(new Date()).id(1L).toNewsImageRequest();

    NewsImageDTO result = translator.fromImageRequestToDTO(imageRequest);

    Assert.assertEquals(imageRequest.getAlt(), result.getAlt());
    Assert.assertEquals(imageRequest.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(imageRequest.getId(), result.getId());
    Assert.assertEquals(imageRequest.getLegend(), result.getLegend());
    Assert.assertEquals(imageRequest.getModificationDate(), result.getModificationDate());
    Assert.assertEquals(imageRequest.getSrc(), result.getBase64Src());
    Assert.assertNull(result.getSrc());
  }

  @Test
  public void testFromContentRequestToDTO() throws Exception {
    NewsContentRequest contentRequest = new NewsContentRequestBuilder().content("someContent").creationDate(new Date())
        .modificationDate(new Date()).id(1L).toNewsContentRequest();

    NewsContentDTO result = translator.fromContentRequestToDTO(contentRequest);

    Assert.assertEquals(contentRequest.getContent(), result.getContent());
    Assert.assertEquals(contentRequest.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(contentRequest.getId(), result.getId());
    Assert.assertEquals(contentRequest.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromDTOToResponse() throws Exception {

    NewsEntryDTO newsEntryDTO = new NewsEntryDTO();

    NewsEntryResponse result = translator.fromDTOToResponse(newsEntryDTO);

    Assert.assertEquals(newsEntryDTO, result.getNewsEntry());
  }

  @Test
  public void testFromRequestToDTO_No_Image_No_Content() throws Exception {

    NewsEntryRequest request = new NewsEntryRequestBuilder().author("someAuthor").title("someTitle").tags("someTags")
        .id(1L).creationDate(new Date()).modificationDate(new Date()).toNewsEntryRequest();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertNull(result.getNewsImage());
    Assert.assertNull(result.getNewsContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());

  }

  @Test
  public void testFromRequestToDTO_No_Image_Content() throws Exception {

    NewsContentRequest contentRequest = new NewsContentRequestBuilder().content("someContent").creationDate(new Date())
        .modificationDate(new Date()).id(1L).toNewsContentRequest();
    NewsEntryRequest request = new NewsEntryRequestBuilder().author("someAuthor").title("someTitle").tags("someTags")
        .content(contentRequest).id(1L).creationDate(new Date()).modificationDate(new Date()).toNewsEntryRequest();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertNull(result.getNewsImage());
    Assert.assertEquals(contentRequest.getContent(), result.getNewsContent().getContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromRequestToDTO_Image_No_Content() throws Exception {

    NewsImageRequest imageRequest = new NewsImageRequestBuilder().src("someSrc").alt("someAlt").legend("someLegend")
        .creationDate(new Date()).modificationDate(new Date()).id(1L).toNewsImageRequest();
    NewsEntryRequest request = new NewsEntryRequestBuilder().author("someAuthor").title("someTitle").tags("someTags")
        .image(imageRequest).id(1L).creationDate(new Date()).modificationDate(new Date()).toNewsEntryRequest();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertEquals(imageRequest.getSrc(), result.getNewsImage().getBase64Src());
    Assert.assertNull(result.getNewsContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromRequestToDTO_With_Image_With_Content() throws Exception {

    NewsContentRequest contentRequest = new NewsContentRequestBuilder().content("someContent").creationDate(new Date())
        .modificationDate(new Date()).id(1L).toNewsContentRequest();
    NewsImageRequest imageRequest = new NewsImageRequestBuilder().src("someSrc").alt("someAlt").legend("someLegend")
        .creationDate(new Date()).modificationDate(new Date()).id(1L).toNewsImageRequest();
    NewsEntryRequest request = new NewsEntryRequestBuilder().author("someAuthor").title("someTitle").tags("someTags")
        .image(imageRequest).content(contentRequest).id(1L).creationDate(new Date()).modificationDate(new Date())
        .toNewsEntryRequest();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertEquals(imageRequest.getSrc(), result.getNewsImage().getBase64Src());
    Assert.assertEquals(contentRequest.getContent(), result.getNewsContent().getContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());
  }
}
