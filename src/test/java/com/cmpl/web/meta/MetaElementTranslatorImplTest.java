package com.cmpl.web.meta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementTranslatorImplTest {

  @Spy
  @InjectMocks
  private MetaElementTranslatorImpl translator;

  @Test
  public void testFromDTOToResponseOpenGraphMetaElementDTO() throws Exception {
    OpenGraphMetaElementDTO dto = new OpenGraphMetaElementDTOBuilder().build();
    Assert.assertEquals(dto, translator.fromDTOToResponse(dto).getOpenGraphMetaElement());
  }

  @Test
  public void testFromDTOToResponseMetaElementDTO() throws Exception {
    MetaElementDTO dto = new MetaElementDTOBuilder().build();
    Assert.assertEquals(dto, translator.fromDTOToResponse(dto).getMetaElement());
  }

  @Test
  public void testFromCreateFormToDTOStringMetaElementCreateForm() throws Exception {
    MetaElementCreateForm form = new MetaElementCreateFormBuilder().name("test").build();
    Assert.assertEquals(form.getName(), translator.fromCreateFormToDTO("123456789", form).getName());
  }

  @Test
  public void testFromCreateFormToDTOStringOpenGraphMetaElementCreateForm() throws Exception {
    OpenGraphMetaElementCreateForm form = new OpenGraphMetaElementCreateFormBuilder().property("test").build();
    Assert.assertEquals(form.getProperty(), translator.fromCreateFormToDTO("123456789", form).getProperty());
  }

}
