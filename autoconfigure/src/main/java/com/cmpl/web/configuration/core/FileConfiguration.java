package com.cmpl.web.configuration.core;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.file.FileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfiguration {

  @Bean
  public FileService fileService(ContextHolder contextHolder) {
    return new FileServiceImpl(contextHolder);
  }

}
