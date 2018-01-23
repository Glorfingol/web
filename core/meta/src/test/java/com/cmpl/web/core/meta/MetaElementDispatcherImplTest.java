package com.cmpl.web.core.meta;

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

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorBuilder;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.error.ErrorCauseBuilder;
import com.cmpl.web.core.common.exception.BaseException;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementDispatcherImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private MetaElementService metaElementService;
  @Mock
  private OpenGraphMetaElementService openGraphMetaElementService;
  @Mock
  private MetaElementTranslator translator;
  @Mock
  private MetaElementValidator validator;

  @Spy
  @InjectMocks
  private MetaElementDispatcherImpl dispatcher;

  @Test
  public void testDeleteOpenGraphMetaEntity_No_Error() throws Exception {
    BDDMockito.given(validator.validateDelete(BDDMockito.anyString(), BDDMockito.any(Locale.class))).willReturn(null);
    BDDMockito.doNothing().when(openGraphMetaElementService).deleteEntity(BDDMockito.anyLong());

    dispatcher.deleteOpenGraphMetaEntity("123456789", Locale.FRANCE);
    BDDMockito.verify(openGraphMetaElementService, BDDMockito.times(1)).deleteEntity(BDDMockito.anyLong());
  }

  @Test
  public void testDeleteOpenGraphMetaEntity_Error() throws BaseException {
    exception.expect(BaseException.class);
    ErrorCause errorCause = ErrorCauseBuilder.create().message("someError").code("someCode").build();
    Error error = ErrorBuilder.create().causes(Lists.newArrayList(errorCause)).code("someCode").build();
    BDDMockito.given(validator.validateDelete(BDDMockito.anyString(), BDDMockito.any(Locale.class))).willReturn(error);

    dispatcher.deleteOpenGraphMetaEntity("123456789", Locale.FRANCE);
  }

  @Test
  public void testDeleteMetaEntity_No_Error() throws Exception {
    BDDMockito.given(validator.validateDelete(BDDMockito.anyString(), BDDMockito.any(Locale.class))).willReturn(null);

    dispatcher.deleteMetaEntity("123456789", Locale.FRANCE);
    BDDMockito.verify(metaElementService, BDDMockito.times(1)).deleteEntity(BDDMockito.anyLong());
  }

  @Test
  public void testDeleteMetaEntity_Error() throws BaseException {
    exception.expect(BaseException.class);
    ErrorCause errorCause = ErrorCauseBuilder.create().message("someError").code("someCode").build();
    Error error = ErrorBuilder.create().causes(Lists.newArrayList(errorCause)).code("someCode").build();
    BDDMockito.given(validator.validateDelete(BDDMockito.anyString(), BDDMockito.any(Locale.class))).willReturn(error);

    dispatcher.deleteMetaEntity("123456789", Locale.FRANCE);
  }

  @Test
  public void testCreateEntityStringOpenGraphMetaElementCreateFormLocale_No_Error() throws Exception {
    OpenGraphMetaElementDTO dtoToCreate = OpenGraphMetaElementDTOBuilder.create().build();
    OpenGraphMetaElementCreateForm form = OpenGraphMetaElementCreateFormBuilder.create().build();

    BDDMockito.given(
        translator.fromCreateFormToDTO(BDDMockito.anyString(), BDDMockito.any(OpenGraphMetaElementCreateForm.class)))
        .willReturn(dtoToCreate);
    BDDMockito.given(openGraphMetaElementService.createEntity(BDDMockito.any(OpenGraphMetaElementDTO.class)))
        .willReturn(dtoToCreate);

    MetaElementResponse response = MetaElementResponseBuilder.create().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(OpenGraphMetaElementDTO.class))).willReturn(response);

    dispatcher.createEntity("123456789", form, Locale.FRANCE);

    BDDMockito.verify(translator, BDDMockito.times(1)).fromCreateFormToDTO(BDDMockito.anyString(),
        BDDMockito.any(OpenGraphMetaElementCreateForm.class));
    BDDMockito.verify(openGraphMetaElementService, BDDMockito.times(1)).createEntity(
        BDDMockito.any(OpenGraphMetaElementDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(OpenGraphMetaElementDTO.class));
  }

  @Test
  public void testCreateEntityStringOpenGraphMetaElementCreateFormLocale_Error() throws Exception {
    ErrorCause errorCause = ErrorCauseBuilder.create().message("someError").code("someCode").build();
    Error error = ErrorBuilder.create().causes(Lists.newArrayList(errorCause)).code("someCode").build();
    BDDMockito.given(
        validator.validateCreate(BDDMockito.anyString(), BDDMockito.any(OpenGraphMetaElementCreateForm.class),
            BDDMockito.any(Locale.class))).willReturn(error);

    MetaElementResponse result = dispatcher.createEntity("123456789", OpenGraphMetaElementCreateFormBuilder.create()
        .build(), Locale.FRANCE);
    Assert.assertEquals(error, result.getError());
  }

  @Test
  public void testCreateEntityStringMetaElementCreateFormLocale_No_Error() throws Exception {
    MetaElementDTO dtoToCreate = MetaElementDTOBuilder.create().build();
    MetaElementCreateForm form = MetaElementCreateFormBuilder.create().build();

    BDDMockito.given(
        translator.fromCreateFormToDTO(BDDMockito.anyString(), BDDMockito.any(MetaElementCreateForm.class)))
        .willReturn(dtoToCreate);
    BDDMockito.given(metaElementService.createEntity(BDDMockito.any(MetaElementDTO.class))).willReturn(dtoToCreate);

    MetaElementResponse response = MetaElementResponseBuilder.create().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(MetaElementDTO.class))).willReturn(response);

    dispatcher.createEntity("123456789", form, Locale.FRANCE);

    BDDMockito.verify(translator, BDDMockito.times(1)).fromCreateFormToDTO(BDDMockito.anyString(),
        BDDMockito.any(MetaElementCreateForm.class));
    BDDMockito.verify(metaElementService, BDDMockito.times(1)).createEntity(BDDMockito.any(MetaElementDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(MetaElementDTO.class));
  }

  @Test
  public void testCreateEntityStringMetaElementCreateFormLocale_Error() throws Exception {
    ErrorCause errorCause = ErrorCauseBuilder.create().message("someError").code("someCode").build();
    Error error = ErrorBuilder.create().causes(Lists.newArrayList(errorCause)).code("someCode").build();
    BDDMockito.given(
        validator.validateCreate(BDDMockito.anyString(), BDDMockito.any(MetaElementCreateForm.class),
            BDDMockito.any(Locale.class))).willReturn(error);

    MetaElementResponse result = dispatcher.createEntity("123456789", MetaElementCreateFormBuilder.create().build(),
        Locale.FRANCE);
    Assert.assertEquals(error, result.getError());
  }
}
