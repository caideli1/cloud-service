package com.cloud.service.feign.backend;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.manage.vo.RepayChannelManagerVo;
import com.cloud.model.user.LoginSysUser;
import com.cloud.model.user.SysUser;
import com.cloud.service.fallback.BackEndFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "manage-backend", fallbackFactory = BackEndFallBackFactory.class)
public interface BackendClient {

    @GetMapping(value = "/users-anon/internal", params = "username")
    LoginSysUser findByUsername(@RequestParam("username") String username);

    @GetMapping("/wechat/login-check")
    void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);

    @GetMapping("/backend-anon/queryNoOffInfo")
    boolean queryNoOffInfo(@RequestParam("nooffname") String nooffname);

    @GetMapping("/backend-anon/internal/blackIPs")
    Set<String> findAllBlackIPs(@RequestParam("params") Map<String, Object> params);

    @GetMapping("/repayChannelManager/getPage")
    JsonResult queryValidRepayChannelList(@RequestParam("status") Integer status);

    @GetMapping("/repayChannelManager/getRepayChannelByName")
    RepayChannelManagerVo getRepayChannelByName(@RequestParam(value = "name") String name);

    @GetMapping("/users-anon/getOneSysUserById")
    SysUser getOneSysUserById(@RequestParam("userId") Integer userId);

    @GetMapping("/users-anon/queryNotGroupUsers")
    List<SysUser> queryNotGroupUsers(@RequestParam(required = false) List<Integer> groupUserIdList);
}
