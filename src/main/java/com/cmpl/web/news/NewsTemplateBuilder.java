package com.cmpl.web.news;

import com.cmpl.web.core.builder.Builder;

public class NewsTemplateBuilder extends Builder<NewsTemplate> {

  private String body;

  public NewsTemplateBuilder body(String body) {
    this.body = body;
    return this;
  }

  @Override
  public NewsTemplate build() {
    NewsTemplate template = new NewsTemplate();
    template.setBody(body);
    return template;
  }

}
