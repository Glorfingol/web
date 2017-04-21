package cmpl.web.controller.front;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cmpl.web.model.BaseException;
import cmpl.web.service.SitemapService;

@Controller
public class SitemapController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RobotsController.class);

  private final SitemapService sitemapService;

  @Autowired
  SitemapController(SitemapService sitemapService) {
    this.sitemapService = sitemapService;
  }

  @RequestMapping(value = {"/sitemap.xml"}, produces = "application/xml")
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
