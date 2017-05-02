package cmpl.web.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.model.news.dao.NewsEntry;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.repository.NewsEntryRepository;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryServiceImplTest {

  @Mock
  private NewsEntryRepository repository;

  @InjectMocks
  @Spy
  private NewsEntryServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsEntryDTO dto = new NewsEntryDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(dto), Mockito.any(NewsEntry.class));
    NewsEntry result = service.toEntity(dto);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(dto), Mockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsEntry entity = new NewsEntry();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(entity), Mockito.any(NewsEntry.class));
    NewsEntryDTO result = service.toDTO(entity);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(entity), Mockito.eq(result));
  }

}
