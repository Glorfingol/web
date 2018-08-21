package com.cmpl.web.launcher;

import com.cmpl.web.core.design.DesignBuilder;
import com.cmpl.web.core.design.DesignRepository;
import com.cmpl.web.core.models.Website;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.sitemap.SitemapBuilder;
import com.cmpl.web.core.sitemap.SitemapRepository;
import com.cmpl.web.core.style.StyleRepository;
import com.cmpl.web.core.website.WebsiteBuilder;
import com.cmpl.web.core.website.WebsiteRepository;

public class WebsiteFactory {

  public static void createWebsite(WebsiteRepository websiteRepository,
      PageRepository pageRepository,
      SitemapRepository sitemapRepository, StyleRepository styleRepository,
      DesignRepository designRepository) {

    Website website = WebsiteBuilder.create().name("cmpl").description("a test").build();
    final Website createdWebsite = websiteRepository.save(website);

    pageRepository.findAll().forEach(page -> sitemapRepository
        .save(SitemapBuilder.create().pageId(page.getId()).websiteId(createdWebsite.getId())
            .build()));
    styleRepository.findAll().forEach(style -> designRepository
        .save(DesignBuilder.create().styleId(style.getId()).websiteId(createdWebsite.getId())
            .build()));
  }
}
