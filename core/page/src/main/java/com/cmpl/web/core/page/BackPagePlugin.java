package com.cmpl.web.core.page;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

@Qualifier(value = "backPages")
public interface BackPagePlugin extends Plugin<String> {

}
