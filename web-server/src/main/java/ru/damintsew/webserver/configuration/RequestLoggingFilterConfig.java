package ru.damintsew.webserver.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import ru.damintsew.webserver.rest.SharedIndexServlet;
import ru.damintsew.webserver.service.FileService;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        return filter;
    }

    @Bean
    public ServletRegistrationBean<SharedIndexServlet> exampleServletBean(FileService fileService) {
        ServletRegistrationBean<SharedIndexServlet> bean = new ServletRegistrationBean<>(
                new SharedIndexServlet(fileService), "/shared-index/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
