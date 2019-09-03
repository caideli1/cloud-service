package com.cloud.oauth.common;

import com.cloud.common.exception.BusinessException;
import com.cloud.common.utils.StringUtils;
import com.cloud.oauth.enums.AppLoginEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = null;
        try {
            userDetails  = userDetailsService.loadUserByUsername(name);
        } catch (RuntimeException e) {
            log.info(e.getLocalizedMessage());
        }

        if (userDetails == null) {
//            throw new BadCredentialsException("");
            throw new BusinessException(AppLoginEnum.NO_USER.getMessage(), AppLoginEnum.NO_USER);
        }

        if (StringUtils.isBlank(password)) {
            throw new BusinessException(AppLoginEnum.PASSWORD_INCORRECT.getMessage(), AppLoginEnum.PASSWORD_INCORRECT);
        }

        String correctPassword = userDetails.getPassword();
        if (!passwordEncoder.matches(password, correctPassword)) {
            //            throw new BadCredentialsException("");
            throw new BusinessException(AppLoginEnum.PASSWORD_INCORRECT.getMessage(), AppLoginEnum.PASSWORD_INCORRECT);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}