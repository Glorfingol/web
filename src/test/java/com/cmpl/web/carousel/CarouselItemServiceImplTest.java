package com.cmpl.web.carousel;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.media.MediaDTOBuilder;
import com.cmpl.web.media.MediaService;

@RunWith(MockitoJUnitRunner.class)
public class CarouselItemServiceImplTest {

  @Mock
  private CarouselItemRepository carouselItemRepository;
  @Mock
  private MediaService mediaService;

  @Spy
  @InjectMocks
  private CarouselItemServiceImpl carouselItemService;

  @Test
  public void testToEntity() throws Exception {
    MediaDTO media = new MediaDTOBuilder().id(123456789l).build();
    CarouselItemDTO dto = new CarouselItemDTOBuilder().media(media).build();

    BDDMockito.doNothing().when(carouselItemService)
        .fillObject(BDDMockito.any(CarouselItemDTO.class), BDDMockito.any(CarouselItem.class));
    carouselItemService.toEntity(dto);

    BDDMockito.verify(carouselItemService, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselItemDTO.class),
        BDDMockito.any(CarouselItem.class));
  }

  @Test
  public void testToDTO() throws Exception {

    MediaDTO media = new MediaDTOBuilder().build();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    CarouselItem entity = new CarouselItemBuilder().mediaId("123456789").build();

    BDDMockito.doNothing().when(carouselItemService)
        .fillObject(BDDMockito.any(CarouselItem.class), BDDMockito.any(CarouselItemDTO.class));
    CarouselItemDTO result = carouselItemService.toDTO(entity);
    Assert.assertEquals(media, result.getMedia());

    BDDMockito.verify(carouselItemService, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselItem.class),
        BDDMockito.any(CarouselItemDTO.class));
  }

  @Test
  public void testCreateEntity() throws Exception {
    MediaDTO media = new MediaDTOBuilder().id(123456789l).build();
    CarouselItemDTO dto = new CarouselItemDTOBuilder().media(media).build();

    BDDMockito.doNothing().when(carouselItemService)
        .fillObject(BDDMockito.any(CarouselItem.class), BDDMockito.any(CarouselItemDTO.class));
    BDDMockito.doReturn(dto).when(carouselItemService).toDTO(BDDMockito.any(CarouselItem.class));

    CarouselItem entity = new CarouselItemBuilder().build();
    BDDMockito.given(carouselItemRepository.save(BDDMockito.any(CarouselItem.class))).willReturn(entity);

    Assert.assertEquals(dto, carouselItemService.createEntity(dto));

  }

  @Test
  public void testGetByCarouselId() throws Exception {

    MediaDTO media = new MediaDTOBuilder().id(123456789l).build();
    CarouselItemDTO dto = new CarouselItemDTOBuilder().media(media).build();

    BDDMockito.doReturn(Lists.newArrayList(dto)).when(carouselItemService)
        .toListDTO(BDDMockito.anyListOf(CarouselItem.class));
    CarouselItem entity = new CarouselItemBuilder().build();
    BDDMockito.given(carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(entity));

    Assert.assertEquals(dto, carouselItemService.getByCarouselId("123456789").get(0));

  }

}
