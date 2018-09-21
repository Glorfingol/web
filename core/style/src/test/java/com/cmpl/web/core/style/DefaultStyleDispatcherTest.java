package com.cmpl.web.core.style;

import com.cmpl.web.core.media.MediaDTOBuilder;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultStyleDispatcherTest {

  @Mock
  private StyleService styleService;

  @Mock
  private StyleTranslator translator;

  @Spy
  @InjectMocks
  private DefaultStyleDispatcher dispatcher;

  @Test
  public void testUpdateEntity() throws Exception {
    StyleDTO translated = new StyleDTO();
    BDDMockito.given(translator.fromUpdateFormToDTO(BDDMockito.any(StyleUpdateForm.class)))
        .willReturn(translated);

    StyleDTO updatedDTO = new StyleDTO();
    BDDMockito.given(styleService.updateEntity(BDDMockito.any(StyleDTO.class)))
        .willReturn(updatedDTO);

    StyleResponse response = new StyleResponse();
    response.setStyle(updatedDTO);
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(StyleDTO.class)))
        .willReturn(response);

    StyleDTO dtoOfForm = StyleDTOBuilder.create().content("someContent")
        .media(MediaDTOBuilder.create().name("someName").id(123456789l).build()).build();
    Assert.assertEquals(response,
        dispatcher.updateEntity(new StyleUpdateForm(dtoOfForm), Locale.FRANCE));
  }

}
