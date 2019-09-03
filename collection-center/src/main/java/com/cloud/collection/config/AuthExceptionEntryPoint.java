package com.cloud.collection.config;

import com.cloud.common.dto.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yoga
 * @Description: AuthExceptionEntryPoint
 * @date 2019-07-2910:23
 */
@Slf4j
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Throwable cause = authException.getCause();

        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if(cause instanceof InvalidTokenException) {
                response.getWriter().write(JsonResult.build(502, "token无效或过期", null).toString()
                );
            }else{
                response.getWriter().write(JsonResult.build(469, authException.getLocalizedMessage(), null).toString()
                );
            }
        } catch (IOException e) {
            log.info(e.getLocalizedMessage());
        }
    }
}