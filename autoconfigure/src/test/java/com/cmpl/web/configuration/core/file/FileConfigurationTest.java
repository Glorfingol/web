package com.cmpl.web.configuration.core.file;

import com.cmpl.web.configuration.core.FileConfiguration;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.DefaultFileService;
import com.cmpl.web.core.file.FileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationTest {

  @Mock
  private ContextHolder contextHolder;

  @Spy
  FileConfiguration configuration;

  @Test
  public void testFileService() throws Exception {
    FileService result = configuration.fileService(contextHolder);

    Assert.assertEquals(DefaultFileService.class, result.getClass());
  }

}
