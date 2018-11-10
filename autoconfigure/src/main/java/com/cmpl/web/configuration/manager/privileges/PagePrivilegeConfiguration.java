package com.cmpl.web.configuration.manager.privileges;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagePrivilegeConfiguration {

    @Bean
    public Privilege pagesReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages", "read");
    }

    @Bean
    public Privilege pagesWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages", "write");
    }

    @Bean
    public Privilege pagesCreatePrivilege() {
        return new SimplePrivilege("webmastering", "pages", "create");
    }

    @Bean
    public Privilege pagesDeletePrivilege() {
        return new SimplePrivilege("webmastering", "pages", "delete");
    }

    @Bean
    public Privilege pagesBodyWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages-body", "write");
    }

    @Bean
    public Privilege pagesBodyReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages-body", "read");
    }

    @Bean
    public Privilege pagesHeaderReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages-header", "write");
    }


    @Bean
    public Privilege pagesHeaderWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages-header", "read");
    }

    @Bean
    public Privilege pagesFooterWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages-footer", "write");
    }


    @Bean
    public Privilege pagesFooterReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages-footer", "read");
    }

    @Bean
    public Privilege pagesMetaWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages-meta", "write");
    }

    @Bean
    public Privilege pagesMetaReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages-meta", "read");
    }

    @Bean
    public Privilege pagesAmpWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages-amp", "write");
    }

    @Bean
    public Privilege pagesAmpReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages-amp", "read");
    }

    @Bean
    public Privilege pagesWidgetsWritePrivilege() {
        return new SimplePrivilege("webmastering", "pages-widgets", "write");
    }

    @Bean
    public Privilege pagesWidgetsReadPrivilege() {
        return new SimplePrivilege("webmastering", "pages-widgets", "read");
    }

    @Bean
    public Privilege pagesWidgetsDeletePrivilege() {
        return new SimplePrivilege("webmastering", "pages-widgets", "delete");
    }
}
