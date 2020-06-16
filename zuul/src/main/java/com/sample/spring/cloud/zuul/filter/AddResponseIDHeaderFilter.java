package com.sample.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AddResponseIDHeaderFilter extends ZuulFilter {
    int id = 1;

    @Override
    public String filterType() {
        return "post"; //pre, route, post, error
    }

    @Override
    public int filterOrder() {
        return 10; //우선순위
    }

    @Override
    public boolean shouldFilter() {
        return true; //
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = context.getResponse();
        servletResponse.addHeader("X-Response-ID", String.valueOf(id++));
        log.info("AddResponseIDHeaderFilter invoked, id:" + id);
        return null;
    }
}
