package com.cmpl.web.news;

import com.cmpl.web.core.builder.Builder;

public class NewsTemplateFormBuilder extends Builder<NewsTemplateForm> {

  private String body;

  public NewsTemplateFormBuilder body(String body) {
    this.body = body;
    return this;
  }

  @Override
  public NewsTemplateForm build() {
    NewsTemplateForm form = new NewsTemplateForm();
    form.setBody(body);
    return form;
  }

}
