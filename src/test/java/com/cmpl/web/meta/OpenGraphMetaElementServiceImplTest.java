package com.cmpl.web.meta;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OpenGraphMetaElementServiceImplTest {

  @Spy
  @InjectMocks
  private OpenGraphMetaElementServiceImpl openGraphMetaElementService;

  @Mock
  private OpenGraphMetaElementRepository openGraphMetaElementRepository;

  @Test
  public void testToEntity() throws Exception {
    OpenGraphMetaElementDTO dto = new OpenGraphMetaElementDTOBuilder().build();

    BDDMockito.doNothing().when(openGraphMetaElementService)
        .fillObject(BDDMockito.any(OpenGraphMetaElementDTO.class), BDDMockito.any(OpenGraphMetaElement.class));
    openGraphMetaElementService.toEntity(dto);

    BDDMockito.verify(openGraphMetaElementService, BDDMockito.times(1)).fillObject(
        BDDMockito.any(OpenGraphMetaElementDTO.class), BDDMockito.any(OpenGraphMetaElement.class));
  }

  @Test
  public void testToDTO() throws Exception {
    OpenGraphMetaElement entity = new OpenGraphMetaElementBuilder().build();

    BDDMockito.doNothing().when(openGraphMetaElementService)
        .fillObject(BDDMockito.any(OpenGraphMetaElement.class), BDDMockito.any(OpenGraphMetaElementDTO.class));
    openGraphMetaElementService.toDTO(entity);

    BDDMockito.verify(openGraphMetaElementService, BDDMockito.times(1)).fillObject(
        BDDMockito.any(OpenGraphMetaElement.class), BDDMockito.any(OpenGraphMetaElementDTO.class));
  }

  @Test
  public void testFindMetaElementsByPageId() throws Exception {

    OpenGraphMetaElementDTO result = new OpenGraphMetaElementDTOBuilder().build();

    BDDMockito.doReturn(Lists.newArrayList(result)).when(openGraphMetaElementService).toListDTO(BDDMockito.anyList());
    BDDMockito.given(openGraphMetaElementRepository.findByPageId(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(new OpenGraphMetaElementBuilder().build()));

    Assert.assertEquals(result, openGraphMetaElementService.findOpenGraphMetaElementsByPageId("123456789").get(0));
  }

}
