package com.cmpl.web.core.meta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MetaElementTranslatorImplTest {

  @Spy
  @InjectMocks
  private MetaElementTranslatorImpl translator;

  @Test
  public void testFromDTOToResponseOpenGraphMetaElementDTO() throws Exception {
    OpenGraphMetaElementDTO dto = OpenGraphMetaElementDTOBuilder.create().build();
    Assert.assertEquals(dto, translator.fromDTOToResponse(dto).getOpenGraphMetaElement());
  }

  @Test
  public void testFromDTOToResponseMetaElementDTO() throws Exception {
    MetaElementDTO dto = MetaElementDTOBuilder.create().build();
    Assert.assertEquals(dto, translator.fromDTOToResponse(dto).getMetaElement());
  }

  @Test
  public void testFromCreateFormToDTOStringMetaElementCreateForm() throws Exception {
    MetaElementCreateForm form = MetaElementCreateFormBuilder.create().name("test").build();
    Assert.assertEquals(form.getName(), translator.fromCreateFormToDTO("123456789", form).getName());
  }

  @Test
  public void testFromCreateFormToDTOStringOpenGraphMetaElementCreateForm() throws Exception {
    OpenGraphMetaElementCreateForm form = OpenGraphMetaElementCreateFormBuilder.create().property("test").build();
    Assert.assertEquals(form.getProperty(), translator.fromCreateFormToDTO("123456789", form).getProperty());
  }

}
