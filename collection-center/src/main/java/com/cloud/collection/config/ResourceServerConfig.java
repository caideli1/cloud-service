package com.cloud.collection.config;

import com.cloud.common.constants.PermitAllUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.TimeZone;

/**
 * 资源服务配置
 * 
 * @author nl
 *
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private AuthExceptionEntryPoint authExceptionEntryPoint;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests()
				.antMatchers(PermitAllUrl.permitAllUrl("/*/**","/*","/collection-anon/**", "/collection/**")).permitAll() // 放开权限的url
				.anyRequest().authenticated().and().httpBasic();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.authenticationEntryPoint(authExceptionEntryPoint);
	}
}
