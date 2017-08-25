package cmpl.web.sitemap;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cmpl.web.core.model.BaseException;
import cmpl.web.robots.RobotsController;

/**
 * Controller du sitemap
 * 
 * @author Louis
 *
 */
@Controller
public class SitemapController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RobotsController.class);

  private final SitemapService sitemapService;

  @Autowired
  SitemapController(SitemapService sitemapService) {
    this.sitemapService = sitemapService;
  }

  /**
   * Mapping pour le sitemap
   * 
   * @return
   */
  @GetMapping(value = {"/sitemap.xml"}, produces = "application/xml")
  @ResponseBody
  public String printSitemap() {

    LOGGER.info("Accès au sitemap");

    try {
      return sitemapService.createSiteMap(Locale.FRANCE);
    } catch (BaseException e1) {
      LOGGER.error("Impossible de générer le fichier des sitemap", e1);
    }
    return "";

  }

}
