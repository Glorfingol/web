package com.cmpl.web.style;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StyleTranslatorImplTest {

  @Spy
  private StyleTranslatorImpl translator;

  @Test
  public void testFromUpdateFormToDTO() throws Exception {
    StyleForm form = new StyleForm();
    form.setId(123456789l);
    form.setContent("someContent");
    form.setMediaId(123456789l);
    form.setMediaName("someName");

    StyleDTO result = translator.fromUpdateFormToDTO(form);
    Assert.assertEquals(form.getId(), result.getId());
    Assert.assertEquals(form.getContent(), result.getContent());
    Assert.assertEquals(form.getMediaId(), result.getMedia().getId());
    Assert.assertEquals(form.getMediaName(), result.getMedia().getName());

  }

  @Test
  public void testFromDTOToResponse() throws Exception {
    StyleDTO dto = new StyleDTO();
    dto.setId(123456789l);

    StyleResponse result = translator.fromDTOToResponse(dto);
    Assert.assertEquals(dto.getId(), result.getStyle().getId());
  }

}
