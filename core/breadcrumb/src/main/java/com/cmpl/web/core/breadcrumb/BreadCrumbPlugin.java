package com.cmpl.web.core.breadcrumb;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

@Qualifier(value = "breadCrumbs")
public interface BreadCrumbPlugin extends Plugin<String> {

}
