package com.cmpl.web.carousel;

import java.util.Locale;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.error.ErrorBuilder;
import com.cmpl.web.core.error.ErrorCauseBuilder;
import com.cmpl.web.core.model.BaseException;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.media.MediaDTOBuilder;
import com.cmpl.web.media.MediaService;

@RunWith(MockitoJUnitRunner.class)
public class CarouselDispatcherImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private CarouselService carouselService;
  @Mock
  private CarouselItemService carouselItemService;
  @Mock
  private MediaService mediaService;
  @Mock
  private CarouselTranslator translator;
  @Mock
  private CarouselValidator validator;

  @Spy
  @InjectMocks
  CarouselDispatcherImpl dispatcher;

  @Test
  public void testDeleteCarouselItemEntity_Error() throws Exception {
    exception.expect(BaseException.class);
    ErrorCause cause = new ErrorCauseBuilder().message("someMessage").code("someCode").build();
    BDDMockito.given(validator.validateDelete(BDDMockito.anyString(), BDDMockito.any(Locale.class))).willReturn(
        new ErrorBuilder().causes(Lists.newArrayList(cause)).build());
    dispatcher.deleteCarouselItemEntity("123456789", Locale.FRANCE);
  }

  @Test
  public void testDeleteCarouselItemEntity_No_Error() throws Exception {
    BDDMockito.given(validator.validateDelete(BDDMockito.anyString(), BDDMockito.any(Locale.class))).willReturn(null);

    BDDMockito.doNothing().when(carouselItemService).deleteEntity(BDDMockito.anyLong());

    dispatcher.deleteCarouselItemEntity("123456789", Locale.FRANCE);

    BDDMockito.verify(carouselItemService, BDDMockito.times(1)).deleteEntity(BDDMockito.anyLong());
  }

  @Test
  public void testCreateEntityCarouselCreateFormLocale_No_Error() throws Exception {
    BDDMockito.given(validator.validateCreate(BDDMockito.any(CarouselCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    CarouselDTO dto = new CarouselDTOBuilder().build();
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(CarouselCreateForm.class))).willReturn(dto);
    BDDMockito.given(carouselService.createEntity(BDDMockito.any(CarouselDTO.class))).willReturn(dto);
    CarouselResponse response = new CarouselResponseBuilder().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(CarouselDTO.class))).willReturn(response);

    Assert.assertEquals(response, dispatcher.createEntity(new CarouselCreateFormBuilder().build(), Locale.FRANCE));

  }

  @Test
  public void testCreateEntityCarouselCreateFormLocale_Error() throws Exception {
    ErrorCause cause = new ErrorCauseBuilder().message("someMessage").code("someCode").build();
    BDDMockito.given(validator.validateCreate(BDDMockito.any(CarouselCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(new ErrorBuilder().causes(Lists.newArrayList(cause)).build());
    Assert.assertEquals(cause, dispatcher.createEntity(new CarouselCreateFormBuilder().build(), Locale.FRANCE)
        .getError().getCauses().get(0));
  }

  @Test
  public void testUpdateEntity_No_Error() throws Exception {
    BDDMockito.given(validator.validateUpdate(BDDMockito.any(CarouselUpdateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    CarouselDTO dto = new CarouselDTOBuilder().build();
    BDDMockito.given(carouselService.getEntity(BDDMockito.anyLong())).willReturn(dto);
    BDDMockito.given(carouselService.updateEntity(BDDMockito.any(CarouselDTO.class))).willReturn(dto);
    CarouselResponse response = new CarouselResponseBuilder().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(CarouselDTO.class))).willReturn(response);

    Assert.assertEquals(response,
        dispatcher.updateEntity(new CarouselUpdateFormBuilder().id(123456789l).build(), Locale.FRANCE));
  }

  @Test
  public void testUpdateEntity_Error() throws Exception {
    ErrorCause cause = new ErrorCauseBuilder().message("someMessage").code("someCode").build();
    BDDMockito.given(validator.validateUpdate(BDDMockito.any(CarouselUpdateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(new ErrorBuilder().causes(Lists.newArrayList(cause)).build());
    Assert.assertEquals(cause, dispatcher.updateEntity(new CarouselUpdateFormBuilder().build(), Locale.FRANCE)
        .getError().getCauses().get(0));
  }

  @Test
  public void testCreateEntityCarouselItemCreateFormLocale_No_Error() throws Exception {
    BDDMockito.given(
        validator.validateCreate(BDDMockito.any(CarouselItemCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(null);

    CarouselItemDTO dto = new CarouselItemDTOBuilder().build();
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(CarouselItemCreateForm.class))).willReturn(dto);
    BDDMockito.given(carouselItemService.createEntity(BDDMockito.any(CarouselItemDTO.class))).willReturn(dto);
    CarouselItemResponse response = new CarouselItemResponseBuilder().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(CarouselItemDTO.class))).willReturn(response);
    MediaDTO media = new MediaDTOBuilder().build();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    Assert.assertEquals(response,
        dispatcher.createEntity(new CarouselItemCreateFormBuilder().mediaId("123456789").build(), Locale.FRANCE));
  }

  @Test
  public void testCreateEntityCarouselItemCreateFormLocale_Error() throws Exception {
    ErrorCause cause = new ErrorCauseBuilder().message("someMessage").code("someCode").build();
    BDDMockito.given(
        validator.validateCreate(BDDMockito.any(CarouselItemCreateForm.class), BDDMockito.any(Locale.class)))
        .willReturn(new ErrorBuilder().causes(Lists.newArrayList(cause)).build());
    Assert.assertEquals(cause, dispatcher.createEntity(new CarouselItemCreateFormBuilder().build(), Locale.FRANCE)
        .getError().getCauses().get(0));
  }

}
