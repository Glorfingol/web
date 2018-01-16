package com.cmpl.web.page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PageTranslatorImplTest {

  @Spy
  @InjectMocks
  private PageTranslatorImpl translator;

  @Test
  public void testFromCreateFormToDTO() throws Exception {
    PageCreateForm form = PageCreateFormBuilder.create().body("someBody").menuTitle("someMenuTitle").name("name")
        .withNews(true).build();

    PageDTO result = translator.fromCreateFormToDTO(form);
    Assert.assertEquals(form.getBody(), result.getBody());
    Assert.assertEquals(form.getMenuTitle(), result.getMenuTitle());
    Assert.assertEquals(form.getName(), result.getName());
    Assert.assertEquals(form.isWithNews(), result.isWithNews());
  }

  @Test
  public void testFromDTOToResponse() throws Exception {
    PageDTO dto = PageDTOBuilder.create().build();
    PageResponse response = translator.fromDTOToResponse(dto);
    Assert.assertEquals(dto, response.getPage());
  }
}
