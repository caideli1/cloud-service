package com.cloud.service.fallback;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.manage.vo.RepayChannelManagerVo;
import com.cloud.model.user.LoginSysUser;
import com.cloud.model.user.SysUser;
import com.cloud.service.feign.backend.BackendClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * backend服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/23 0023
 * @since 1.0.0
 */
@Component
public class BackEndFallBackFactory implements FallbackFactory<BackendClient> {
    @Override
    public BackendClient create(Throwable throwable) {
        return new BackendClient() {
            @Override
            public LoginSysUser findByUsername(String username) {
                return null;
            }

            @Override
            public void wechatLoginCheck(String tempCode, String openid) {

            }

            @Override
            public boolean queryNoOffInfo(String nooffname) {
                return false;
            }

            @Override
            public Set<String> findAllBlackIPs(Map<String, Object> params) {
                return null;
            }

            @Override
            public JsonResult queryValidRepayChannelList(Integer status) {
                return null;
            }

            @Override
            public RepayChannelManagerVo getRepayChannelByName(String name) {
                return null;
            }

            @Override
            public SysUser getOneSysUserById(Integer userId) {
                return null;
            }

            @Override
            public List<SysUser> queryNotGroupUsers(List<Integer> groupUserIdList) {
                return null;
            }
        };
    }
}
