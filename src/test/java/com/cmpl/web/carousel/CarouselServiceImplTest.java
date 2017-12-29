package com.cmpl.web.carousel;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.media.MediaDTOBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CarouselServiceImplTest {

  @Mock
  private CarouselRepository carouselRepository;
  @Mock
  private CarouselItemService carouselItemService;

  @Spy
  @InjectMocks
  private CarouselServiceImpl carouselService;

  @Test
  public void testToEntity() throws Exception {
    CarouselDTO dto = new CarouselDTOBuilder().build();

    BDDMockito.doNothing().when(carouselService)
        .fillObject(BDDMockito.any(CarouselDTO.class), BDDMockito.any(Carousel.class));
    carouselService.toEntity(dto);

    BDDMockito.verify(carouselService, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselDTO.class),
        BDDMockito.any(Carousel.class));
  }

  @Test
  public void testToDTO() throws Exception {

    Carousel entity = new CarouselBuilder().id(123456789l).build();
    MediaDTO media = new MediaDTOBuilder().id(123456789l).build();
    CarouselItemDTO dto = new CarouselItemDTOBuilder().media(media).build();

    BDDMockito.given(carouselItemService.getByCarouselId(BDDMockito.anyString())).willReturn(Lists.newArrayList(dto));

    BDDMockito.doNothing().when(carouselService)
        .fillObject(BDDMockito.any(Carousel.class), BDDMockito.any(CarouselDTO.class));
    CarouselDTO result = carouselService.toDTO(entity);
    Assert.assertEquals(dto, result.getCarouselItems().get(0));

    BDDMockito.verify(carouselService, BDDMockito.times(1)).fillObject(BDDMockito.any(Carousel.class),
        BDDMockito.any(CarouselDTO.class));
  }

  @Test
  public void testFindByPageId() throws Exception {
    MediaDTO media = new MediaDTOBuilder().id(123456789l).build();
    CarouselItemDTO dto = new CarouselItemDTOBuilder().media(media).build();
    CarouselDTO carousel = new CarouselDTOBuilder().carouselItems(Lists.newArrayList(dto)).build();

    BDDMockito.doReturn(Lists.newArrayList(carousel)).when(carouselService).toListDTO(BDDMockito.anyList());
    Carousel entity = new CarouselBuilder().build();
    BDDMockito.given(carouselRepository.findByPageId(BDDMockito.anyString())).willReturn(Lists.newArrayList(entity));

    Assert.assertEquals(carousel, carouselService.findByPageId("123456789").get(0));
  }
}
