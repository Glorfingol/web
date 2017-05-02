package cmpl.web.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import cmpl.web.model.news.dao.NewsImage;
import cmpl.web.model.news.dto.NewsImageDTO;
import cmpl.web.repository.NewsImageRepository;

@RunWith(MockitoJUnitRunner.class)
public class NewsImageServiceImplTest {

  @Mock
  private NewsImageRepository repository;

  @InjectMocks
  @Spy
  private NewsImageServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsImageDTO dto = new NewsImageDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(dto), Mockito.any(NewsImage.class));
    NewsImage result = service.toEntity(dto);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(dto), Mockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsImage entity = new NewsImage();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(Mockito.eq(entity), Mockito.any(NewsImageDTO.class));
    NewsImageDTO result = service.toDTO(entity);

    Mockito.verify(service, Mockito.times(1)).fillObject(Mockito.eq(entity), Mockito.eq(result));
  }

}
