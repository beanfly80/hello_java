package com.hello.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(
                    "classpath:/META-INF/resources/", "classpath:/resources/",
                    "classpath:/static/", "classpath:/templates/",
                    "classpath:/public/", "classpath:/webjars/")
                .setCachePeriod(10);
    }

	@Bean
	public SpringSecurityDialect securityDialect(){
		return new SpringSecurityDialect();
	}
}