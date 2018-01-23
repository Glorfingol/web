package com.cmpl.web.configuration.core.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.file.FileServiceImpl;
import com.cmpl.web.core.file.ImageConverterService;
import com.cmpl.web.core.file.ImageConverterServiceImpl;
import com.cmpl.web.core.file.ImageService;
import com.cmpl.web.core.file.ImageServiceImpl;

@Configuration
public class FileConfiguration {

  @Bean
  ImageConverterService imageConverterService() {
    return new ImageConverterServiceImpl();
  }

  @Bean
  ImageService imageService(ContextHolder contextHolder, ImageConverterService imageConverterService) {
    return new ImageServiceImpl(contextHolder, imageConverterService);
  }

  @Bean
  FileService fileService(ContextHolder contextHolder) {
    return new FileServiceImpl(contextHolder);
  }

}
