package com.cmpl.web.style;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.cmpl.web.media.MediaDTOBuilder;

@RunWith(MockitoJUnitRunner.class)
public class StyleTranslatorImplTest {

  @Spy
  private StyleTranslatorImpl translator;

  @Test
  public void testFromUpdateFormToDTO() throws Exception {
    StyleDTO dtoOfForm = new StyleDTOBuilder().content("someContent")
        .media(new MediaDTOBuilder().name("someName").id(123456789l).build()).build();
    StyleForm form = new StyleForm(dtoOfForm);

    StyleDTO result = translator.fromUpdateFormToDTO(form);
    Assert.assertEquals(form.getId(), result.getId());
    Assert.assertEquals(form.getContent(), result.getContent());
    Assert.assertEquals(form.getMediaId(), result.getMedia().getId());
    Assert.assertEquals(form.getMediaName(), result.getMedia().getName());

  }

  @Test
  public void testFromDTOToResponse() throws Exception {
    StyleDTO dto = new StyleDTOBuilder().id(123456789l).build();

    StyleResponse result = translator.fromDTOToResponse(dto);
    Assert.assertEquals(dto.getId(), result.getStyle().getId());
  }

}
