package com.cmpl.web.configuration.modules.facebook;

import com.cmpl.web.modules.social.configuration.SocialProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.social.facebook")
public class FacebookProperties extends SocialProperties {

}