package com.cmpl.web.front.ui.sitemap;

import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;

/**
 * Controller du sitemap
 * 
 * @author Louis
 *
 */
@Controller
public class SitemapController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SitemapController.class);

  private final RenderingSitemapService renderingSitemapService;

  public SitemapController(RenderingSitemapService renderingSitemapService) {

    this.renderingSitemapService = Objects.requireNonNull(renderingSitemapService);

  }

  /**
   * Mapping pour le sitemap
   * 
   * @return
   */
  @GetMapping(value = {"/sitemap.xml"}, produces = "application/xml")
  @ResponseBody
  public String printSitemap(Locale locale) {

    LOGGER.info("Accès au sitemap");

    try {
      return renderingSitemapService.createSiteMap(locale);
    } catch (BaseException e1) {
      LOGGER.error("Impossible de générer le fichier des sitemap", e1);
    }
    return "";

  }

}
