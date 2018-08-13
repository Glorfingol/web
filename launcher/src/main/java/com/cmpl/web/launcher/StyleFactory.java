package com.cmpl.web.launcher;

import com.cmpl.web.core.models.Style;
import com.cmpl.web.core.style.StyleBuilder;
import com.cmpl.web.core.style.StyleRepository;

public class StyleFactory {

  public static void createStyles(StyleRepository styleRepository) {
    Style styleToCreate = StyleBuilder.create().name("test").build();
    styleRepository.save(styleToCreate);
  }

}
