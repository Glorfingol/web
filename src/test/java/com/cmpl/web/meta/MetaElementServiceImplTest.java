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
public class MetaElementServiceImplTest {

  @Spy
  @InjectMocks
  private MetaElementServiceImpl metaElementService;

  @Mock
  private MetaElementRepository metaElementRepository;

  @Test
  public void testToEntity() throws Exception {
    MetaElementDTO dto = new MetaElementDTOBuilder().build();

    BDDMockito.doNothing().when(metaElementService)
        .fillObject(BDDMockito.any(MetaElementDTO.class), BDDMockito.any(MetaElement.class));
    metaElementService.toEntity(dto);

    BDDMockito.verify(metaElementService, BDDMockito.times(1)).fillObject(BDDMockito.any(MetaElementDTO.class),
        BDDMockito.any(MetaElement.class));
  }

  @Test
  public void testToDTO() throws Exception {
    MetaElement entity = new MetaElementBuilder().build();

    BDDMockito.doNothing().when(metaElementService)
        .fillObject(BDDMockito.any(MetaElement.class), BDDMockito.any(MetaElementDTO.class));
    metaElementService.toDTO(entity);

    BDDMockito.verify(metaElementService, BDDMockito.times(1)).fillObject(BDDMockito.any(MetaElement.class),
        BDDMockito.any(MetaElementDTO.class));
  }

  @Test
  public void testFindMetaElementsByPageId() throws Exception {

    MetaElementDTO result = new MetaElementDTOBuilder().build();

    BDDMockito.doReturn(Lists.newArrayList(result)).when(metaElementService)
        .toListDTO(BDDMockito.anyListOf(MetaElement.class));
    BDDMockito.given(metaElementRepository.findByPageId(BDDMockito.anyString())).willReturn(
        Lists.newArrayList(new MetaElementBuilder().build()));

    Assert.assertEquals(result, metaElementService.findMetaElementsByPageId("123456789").get(0));
  }

}
