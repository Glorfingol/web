package com.cmpl.web.style;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.media.MediaDTOBuilder;

@RunWith(MockitoJUnitRunner.class)
public class StyleDispatcherImplTest {

  @Mock
  private StyleService styleService;
  @Mock
  private StyleTranslator translator;

  @Spy
  @InjectMocks
  private StyleDispatcherImpl dispatcher;

  @Test
  public void testUpdateEntity() throws Exception {
    StyleDTO translated = new StyleDTO();
    BDDMockito.given(translator.fromUpdateFormToDTO(BDDMockito.any(StyleForm.class))).willReturn(translated);

    StyleDTO updatedDTO = new StyleDTO();
    BDDMockito.given(styleService.updateEntity(BDDMockito.any(StyleDTO.class))).willReturn(updatedDTO);

    StyleResponse response = new StyleResponse();
    response.setStyle(updatedDTO);
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(StyleDTO.class))).willReturn(response);

    StyleDTO dtoOfForm = new StyleDTOBuilder().content("someContent")
        .media(new MediaDTOBuilder().name("someName").id(123456789l).build()).build();
    Assert.assertEquals(response, dispatcher.updateEntity(new StyleForm(dtoOfForm), Locale.FRANCE));
  }

}
