package com.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AppTokenFilter extends ZuulFilter {

    @SuppressWarnings("unused")
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String localToken = request.getHeader("Authorization");
        String phone = request.getHeader("code");
        StringBuffer requestURL = request.getRequestURL();
        if (localToken != null) {

            if (localToken.contains("Bearer")) {
                ctx.setSendZuulResponse(true);
                //对请求进行路由
                ctx.setResponseStatusCode(200);
                ctx.set("isSuccess", true);
            } else if (localToken.contains("OAuth2")) {
                ctx.setSendZuulResponse(true);
                //对请求进行路由
                ctx.setResponseStatusCode(200);
                ctx.set("isSuccess", true);
            } else {
                ctx.setSendZuulResponse(false);
                //不对其进行路由
                ctx.setResponseStatusCode(400);
                ctx.setResponseBody("token is empty");
                ctx.set("isSuccess", false);
            }

        }

        return null;

    }

    /*
     * @author nl
     *
     * @desc  是否执行该过滤器，如返回true，说明需要过滤
     *
     * @see com.netflix.zuul.ZuulFilter#filterType()
     */


    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*
     * @author nl
     *
     * @desc filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
     *
     * @see com.netflix.zuul.ZuulFilter#filterType()
     */


    @Override
    public int filterOrder() {
        return 0;
    }



    /*
     * @author nl
     *
     * @desc 可以在请求被路由之前调用
     *
     * @see com.netflix.zuul.ZuulFilter#filterType()
     */


    @Override
    public String filterType() {
        return "pre";
    }


}
