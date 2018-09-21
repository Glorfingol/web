package com.cmpl.web.configuration.core;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.DefaultFileService;
import com.cmpl.web.core.file.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfiguration {

  @Bean
  public FileService fileService(ContextHolder contextHolder) {
    return new DefaultFileService(contextHolder);
  }

}
