package com.cloud.user.config;

import io.sentry.spring.SentryServletContextInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author yoga
 * @Description: SentryConfig
 * @date 2019-06-2611:48
 */
@Configuration
public class SentryConfig {
    @Bean
    @Profile({"prod"})
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver() {
            @Override
            public int getOrder() {
                // ensure we can get some resolver earlier than this
                return 10;
            }

//            @Override
//            public ModelAndView resolveException(HttpServletRequest request,
//                                                 HttpServletResponse response,
//                                                 Object handler,
//                                                 Exception ex) {
//                Throwable rootCause = ex;
//
//                while (rootCause .getCause() != null && rootCause.getCause() != rootCause) {
//                    rootCause = rootCause.getCause();
//                }
//
//                if (!rootCause.getMessage().contains("Broken pipe")) {
//                    super.resolveException(request, response, handler, ex);
//                }
//                return null;
//            }
        };
    }

//    @Bean
//    @Profile({"dev", "prod"})
//    public ServletContextInitializer sentryServletContextInitializer() {
//        return new SentryServletContextInitializer();
//    }
}
