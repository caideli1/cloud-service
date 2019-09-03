package com.cloud.oauth.common;

import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yoga
 * @Description: MyExceptionHandlerExceptionResolver
 * @date 2019-07-2913:56
 */
public class MyExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {
    private Map<ControllerAdviceBean, ExceptionHandlerMethodResolver> exceptionHandlerAdviceCache = null;

    @Override
    protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        Class<?> handlerType = (handlerMethod != null ? handlerMethod.getBeanType() : null);
        List<ControllerAdviceBean> adviceBeans = ControllerAdviceBean.findAnnotatedBeans(getApplicationContext());
        if (exceptionHandlerAdviceCache==null){
            exceptionHandlerAdviceCache = new LinkedHashMap<ControllerAdviceBean, ExceptionHandlerMethodResolver>();
            for (ControllerAdviceBean adviceBean:adviceBeans){
                ExceptionHandlerMethodResolver resolver = new ExceptionHandlerMethodResolver(adviceBean.getBeanType());
                exceptionHandlerAdviceCache.put(adviceBean, resolver);
            }
        }
        for (Map.Entry<ControllerAdviceBean, ExceptionHandlerMethodResolver> entry : this.exceptionHandlerAdviceCache.entrySet()) {
            if (entry.getKey().isApplicableToBeanType(handlerType)) {
                ExceptionHandlerMethodResolver resolver = entry.getValue();
                Method method = resolver.resolveMethod(exception);
                if (method != null) {
                    return new ServletInvocableHandlerMethod(entry.getKey().resolveBean(), method);
                }
            }
        }
        return null;
    }
}
