package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.CarouselItem;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCarouselItemServiceTest {

  @Mock
  private CarouselItemMapper mapper;

  @Mock
  private CarouselItemDAO carouselItemDAO;

  @Mock
  private MediaService mediaService;

  @Spy
  @InjectMocks
  private DefaultCarouselItemService carouselItemService;

  @Test
  public void testCreateEntity() throws Exception {
    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    CarouselItem entity = CarouselItemBuilder.create().build();
    BDDMockito.given(carouselItemDAO.createEntity(BDDMockito.any(CarouselItem.class)))
        .willReturn(entity);
    BDDMockito.given(mapper.toEntity(BDDMockito.any(CarouselItemDTO.class))).willReturn(entity);
    BDDMockito.given(mapper.toDTO(BDDMockito.any(CarouselItem.class))).willReturn(dto);

    Assert.assertEquals(dto, carouselItemService.createEntity(dto));

  }

  @Test
  public void testGetByCarouselId() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    BDDMockito.doReturn(Arrays.asList(dto)).when(mapper).toListDTO(BDDMockito.anyList());
    CarouselItem entity = CarouselItemBuilder.create().build();
    BDDMockito.given(carouselItemDAO.getByCarouselId(BDDMockito.anyString()))
        .willReturn(Arrays.asList(entity));

    Assert.assertEquals(dto, carouselItemService.getByCarouselId("123456789").get(0));

  }

}
