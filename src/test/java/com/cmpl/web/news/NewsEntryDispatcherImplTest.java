package com.cmpl.web.news;

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

import com.cmpl.web.core.error.ERROR;
import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.news.NewsEntryDTO;
import com.cmpl.web.news.NewsEntryDispatcherImpl;
import com.cmpl.web.news.NewsEntryRequest;
import com.cmpl.web.news.NewsEntryRequestValidator;
import com.cmpl.web.news.NewsEntryResponse;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.news.NewsEntryTranslator;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDispatcherImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private NewsEntryRequestValidator validator;
  @Mock
  private NewsEntryTranslator translator;
  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private NewsEntryDispatcherImpl dispatcher;

  private Locale locale;

  @Before
  public void setUp() {
    locale = Locale.FRANCE;
  }

  @Test
  public void testDeleteEntity_Ok() throws Exception {

    BDDMockito.doReturn(null).when(validator).validateDelete(Mockito.anyString(), Mockito.eq(locale));

    dispatcher.deleteEntity(String.valueOf(1L), locale);

    Mockito.verify(validator, Mockito.times(1)).validateDelete(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(newsEntryService, Mockito.times(1)).deleteEntity(Mockito.anyLong());
  }

  @Test
  public void testDeleteEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(ERROR.INVALID_REQUEST.toString());
    error.setCauses(Lists.newArrayList(errorCause));

    exception.expect(BaseException.class);
    exception.expectMessage(errorCause.getMessage());

    BDDMockito.doReturn(error).when(validator).validateDelete(Mockito.anyString(), Mockito.eq(locale));

    dispatcher.deleteEntity(String.valueOf(1L), locale);

    Mockito.verify(validator, Mockito.times(1)).validateDelete(Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(newsEntryService, Mockito.times(0)).deleteEntity(Mockito.anyLong());
  }

  @Test
  public void testUpdateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(null).when(validator)
        .validateUpdate(Mockito.any(NewsEntryRequest.class), Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(response).when(translator).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), locale);

    Assert.assertEquals(response, result);

    Mockito.verify(validator, Mockito.times(1)).validateUpdate(Mockito.any(NewsEntryRequest.class),
        Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(1)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(1)).updateEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(1)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testUpdateEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(ERROR.INVALID_REQUEST.toString());
    error.setCauses(Lists.newArrayList(errorCause));

    NewsEntryResponse response = new NewsEntryResponse();
    response.setError(error);

    BDDMockito.doReturn(error).when(validator)
        .validateUpdate(Mockito.any(NewsEntryRequest.class), Mockito.anyString(), Mockito.eq(locale));

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), locale);

    Assert.assertEquals(response.getError(), result.getError());

    Mockito.verify(validator, Mockito.times(1)).validateUpdate(Mockito.any(NewsEntryRequest.class),
        Mockito.anyString(), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(0)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(0)).updateEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(0)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testCreateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(null).when(validator)
        .validateUpdate(Mockito.any(NewsEntryRequest.class), Mockito.anyString(), Mockito.eq(locale));
    BDDMockito.doReturn(response).when(translator).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

    NewsEntryResponse result = dispatcher.createEntity(new NewsEntryRequest(), locale);

    Assert.assertEquals(response, result);

    Mockito.verify(validator, Mockito.times(1)).validateCreate(Mockito.any(NewsEntryRequest.class), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(1)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(1)).createEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(1)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testCreateEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(ERROR.INVALID_REQUEST.toString());
    error.setCauses(Lists.newArrayList(errorCause));

    NewsEntryResponse response = new NewsEntryResponse();
    response.setError(error);

    BDDMockito.doReturn(error).when(validator).validateCreate(Mockito.any(NewsEntryRequest.class), Mockito.eq(locale));

    NewsEntryResponse result = dispatcher.createEntity(new NewsEntryRequest(), locale);

    Assert.assertEquals(response.getError(), result.getError());

    Mockito.verify(validator, Mockito.times(1)).validateCreate(Mockito.any(NewsEntryRequest.class), Mockito.eq(locale));
    Mockito.verify(translator, Mockito.times(0)).fromRequestToDTO(Mockito.any(NewsEntryRequest.class));
    Mockito.verify(newsEntryService, Mockito.times(0)).createEntity(Mockito.any(NewsEntryDTO.class));
    Mockito.verify(translator, Mockito.times(0)).fromDTOToResponse(Mockito.any(NewsEntryDTO.class));

  }
}
