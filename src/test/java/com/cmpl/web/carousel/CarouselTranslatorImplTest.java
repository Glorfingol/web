package com.cmpl.web.carousel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarouselTranslatorImplTest {

  @Spy
  @InjectMocks
  private CarouselTranslatorImpl translator;

  @Test
  public void testFromCreateFormToDTOCarouselCreateForm() throws Exception {

    CarouselCreateForm form = new CarouselCreateFormBuilder().name("someName").build();
    CarouselDTO result = translator.fromCreateFormToDTO(form);

    Assert.assertEquals(form.getName(), result.getName());

  }

  @Test
  public void testFromDTOToResponseCarouselDTO() throws Exception {
    CarouselDTO dto = new CarouselDTOBuilder().build();
    CarouselResponse result = translator.fromDTOToResponse(dto);
    Assert.assertEquals(dto, result.getCarousel());
  }

  @Test
  public void testFromCreateFormToDTOCarouselItemCreateForm() throws Exception {
    CarouselItemCreateForm form = new CarouselItemCreateFormBuilder().carouselId("123456789").build();
    CarouselItemDTO result = translator.fromCreateFormToDTO(form);

    Assert.assertEquals(form.getCarouselId(), result.getCarouselId());
  }

  @Test
  public void testFromDTOToResponseCarouselItemDTO() throws Exception {
    CarouselItemDTO dto = new CarouselItemDTOBuilder().build();
    CarouselItemResponse result = translator.fromDTOToResponse(dto);

    Assert.assertEquals(dto, result.getItem());
  }

}
