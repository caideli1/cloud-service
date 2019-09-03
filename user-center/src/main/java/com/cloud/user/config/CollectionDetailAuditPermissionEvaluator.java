package com.cloud.user.config;

import com.cloud.model.user.LoginSysUser;
import com.cloud.service.feign.collection.CollectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yoga
 * @Description: 审核员订单详情产看权限
 * @date 2019-06-0516:48
 */
@Slf4j
public class CollectionDetailAuditPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    CollectionClient collectionClient;


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // targetDomainObject 应该传 orderNo 进来

        if (!(targetDomainObject instanceof String)) {
            return false;
        }

        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginSysUser) {
                    System.out.println(principal);
                }

                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");
                Integer sysUserId = (Integer) map.getOrDefault("id", 0);
                log.info("审核员订单详情产看 -- 查询用户: {} 有无: {} 的查看权限", sysUserId, targetDomainObject);
                return collectionClient.hasCollDetailsAuditPermission(sysUserId, (String) targetDomainObject);
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}