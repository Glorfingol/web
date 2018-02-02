package com.cmpl.web.front.ui.widgets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.cmpl.web.core.factory.DisplayFactory;

@Controller
public class WidgetController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WidgetController.class);
  private final DisplayFactory displayFactory;

  public WidgetController(DisplayFactory displayFactory){
    this.displayFactory = displayFactory;
  }

}
