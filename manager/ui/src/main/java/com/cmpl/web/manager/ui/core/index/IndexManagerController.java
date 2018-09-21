package com.cmpl.web.manager.ui.core.index;

import com.cmpl.web.core.factory.index.IndexDisplayFactory;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;
import java.util.Locale;
import java.util.Objects;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ManagerController
@RequestMapping(value = "/manager")
public class IndexManagerController {

  private final IndexDisplayFactory displayFactory;

  private final PluginRegistry<BackPage, String> backPages;

  public IndexManagerController(IndexDisplayFactory displayFactory,
      PluginRegistry<BackPage, String> backPages) {

    this.displayFactory = Objects.requireNonNull(displayFactory);
    this.backPages = backPages;
  }

  @GetMapping
  public ModelAndView printIndex() {
    return displayFactory
        .computeModelAndViewForBackPage(computeBackPage("INDEX"), Locale.FRANCE);
  }

  @GetMapping(value = "/")
  public ModelAndView printIndexGlobal() {
    return displayFactory
        .computeModelAndViewForBackPage(computeBackPage("INDEX"), Locale.FRANCE);
  }

  protected BackPage computeBackPage(String pageName) {
    return backPages.getPluginFor(pageName);
  }

}
