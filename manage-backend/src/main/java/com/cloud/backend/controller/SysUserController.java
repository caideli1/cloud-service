package com.cloud.backend.controller;

import com.cloud.backend.service.SysPermissionService;
import com.cloud.backend.service.SysUserService;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.LoginSysUser;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysUser;
import com.cloud.service.feign.notification.SmsClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SmsClient smsClient;

    /**
     * 当前登录用户 LoginSysUser
     */
    @GetMapping("/users/current")
    public LoginSysUser getLoginAppUser() {
        return AppUserUtil.getLoginSysUser();
    }

    /**
     * 当前登录用户 SysUser
     */
    @GetMapping("/sysUser/current")
    public JsonResult whoAmI() {
//        SysUser sysUser = sysUserService.allSysUser(null, AppUserUtil.getLoginSysUser().getId(), null).get(0);
        SysUser sysUser = sysUserService.findById(AppUserUtil.getLoginSysUser().getId());
        return JsonResult.ok(sysUser);
    }

    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginSysUser findByUsername(String username) {
        return sysUserService.findByUsername(username);
    }

    /**
     * 用户查询
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:user:query') or hasAuthority('permission:all')")
    @GetMapping("/users")
    public Page<SysUser> findUsers(@RequestParam Map<String, Object> params) {
        return sysUserService.findUsers(params);
    }

    @PreAuthorize("hasAuthority('back:user:query') or hasAuthority('permission:all')")
    @GetMapping("/users/{id}")
    public SysUser findUserById(@PathVariable Long id) {
        return sysUserService.findById(id);
    }

    /**
     * 根据用户id获取用户单条记录
     * created by caideli 2019/8/19
     * @param userId
     * @return
     */
    @GetMapping("/users-anon/getOneSysUserById")
    public SysUser getOneSysUserById(@RequestParam Integer userId) {
        return sysUserService.getOneSysUserById(userId);
    }

    /**
     * 根据已经分组的催收员用户Id查出未分组的催收员用户列表
     * created by caideli 2019/8/19
     * @param groupUserIdList
     * @return
     */
    @GetMapping("/users-anon/queryNotGroupUsers")
    public List<SysUser> queryNotGroupUsers(@RequestParam(required = false) List<Integer> groupUserIdList) {
        return sysUserService.queryNotGroupUsers(groupUserIdList);
    }

//    /**
//     * 添加用户,根据用户名注册
//     *
//     * @param sysUser
//     */
//    @PostMapping("/users-anon/register")
//    public SysUser register(@RequestBody SysUser sysUser) {
//        // 用户名等信息的判断逻辑挪到service了
//        sysUserService.addSysUser(sysUser);
//
//        return sysUser;
//    }

    /**
     * 管理后台修改用户
     *
     * @param sysUser
     */
    @LogAnnotation(module = "修改用户")
    @PreAuthorize("hasAuthority('back:user:update') or hasAuthority('permission:all')")
    @PutMapping("/sysUser")
    public JsonResult updateMe(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return JsonResult.ok("修改成功");
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @LogAnnotation(module = "修改密码")
    @PutMapping(value = "/sysUser/password", params = {"oldPassword", "newPassword"})
    public JsonResult updatePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword)) {
            throw new IllegalArgumentException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalArgumentException("新密码不能为空");
        }

        SysUser user = AppUserUtil.getLoginSysUser();
        sysUserService.updatePassword(user.getId(), oldPassword, newPassword);

        return JsonResult.ok("修改成功");
    }


    /**
     * 修改自己的头像
     */
    @PostMapping("/sysUser/avatar")
    public JsonResult updateAvatar(@RequestBody String avatarUrl) {
        if (StringUtils.isBlank(avatarUrl)) {
            throw new IllegalArgumentException("url不能为空");
        }

        SysUser user = AppUserUtil.getLoginSysUser();
        sysUserService.updateAvatar(user.getId(), avatarUrl);
        return JsonResult.ok("修改成功");
    }

//    /**
//     * 管理后台，给用户重置密码
//     *
//     * @param id          用户id
//     * @param newPassword 新密码
//     */
//    @LogAnnotation(module = "重置密码")
//    @PreAuthorize("hasAuthority('back:user:password') or hasAuthority('permission:all')")
//    @PutMapping(value = "/users/{id}/password", params = {"newPassword"})
//    public void resetPassword(@PathVariable Long id, String newPassword) {
//        sysUserService.updatePassword(id, null, newPassword);
//    }



//    /**
//     * 管理后台给用户分配角色
//     *
//     * @param id      用户id
//     * @param roleIds 角色ids
//     */
//    @LogAnnotation(module = "分配角色")
//    @PreAuthorize("hasAuthority('back:user:role:set') or hasAuthority('permission:all')")
//    @PostMapping("/users/{id}/roles")
//    public void setRoleToUser(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
//        sysUserService.setRoleToUser(id, roleIds);
//    }
//
//    /**
//     * 获取用户的角色
//     *
//     * @param id 用户id
//     */
//    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid') or hasAuthority('permission:all')")
//    @GetMapping("/users/{id}/roles")
//    public Set<SysRole> findRolesByUserId(@PathVariable Long id) {
//        return sysUserService.findRolesByUserId(id);
//    }



    /**
     * 绑定手机号
     *
     * @param phone
     * @param key
     * @param code
     */
    @LogAnnotation(module = "绑定手机号")
    @PostMapping(value = "/users/binding-phone")
    public void bindingPhone(String phone, String key, String code) {
        if (StringUtils.isBlank(phone)) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key不能为空");
        }

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code不能为空");
        }

        LoginSysUser loginAppUser = AppUserUtil.getLoginSysUser();
        log.info("绑定手机号，key:{},code:{},username:{}", key, code, loginAppUser.getUsername());

        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 30);
        if (value == null) {
            throw new IllegalArgumentException("验证码错误");
        }

        if (phone.equals(value)) {
            sysUserService.bindingPhone(loginAppUser.getId(), phone);
        } else {
            throw new IllegalArgumentException("手机号不一致");
        }
    }

    @PreAuthorize("hasAuthority('permission:all')")
    @GetMapping("/sysUser/all")
    public JsonResult allSysUsers(@RequestParam(required = false) String nickname,
                                  @RequestParam(required = false) Long sysUserId,
                                  @RequestParam(required = false) Long deptId,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<SysUser> users = sysUserService.allSysUser(nickname, sysUserId, deptId);
        PageInfo pageInfo = new PageInfo<>(users);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }


    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/sysUser/save")
    public JsonResult saveSysUserAndSetDept(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUserAndSetDept(sysUser);
        return JsonResult.ok("创建用户成功");
    }


    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/sysUser/{sysUserId}/update/enable")
    public JsonResult updateEnableStatus(@PathVariable Long sysUserId, @RequestParam Integer status) {
        sysUserService.updateEnable(sysUserId, status);
        return JsonResult.ok("更改成功");
    }

    @PreAuthorize("hasAuthority('permission:all')")
    @DeleteMapping("/sysUser/{sysUserId}")
    public JsonResult deleteSysUser(@PathVariable Long sysUserId) {
        sysUserService.deleteSysUser(sysUserId);
        return JsonResult.ok("删除成功");
    }


    /**
     * 管理后台给sysUser分配权限
     */
    @LogAnnotation(module = "分配权限")
    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/sysUser/{id}/permissions")
    public JsonResult setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
        sysPermissionService.setPermissionsToSysUser(id, permissionIds);
        return JsonResult.ok("更改成功");
    }

    /**
     * 获取sysUser的权限
     *
     * @param id
     */
    @PreAuthorize("hasAuthority('permission:all')")
    @GetMapping("/sysUser/{id}/permissions")
    public JsonResult findPermissionsByRoleId(@PathVariable Long id) {
        Set<SysPermission> permissions = sysPermissionService.getUserPermissionsByUserId(id);
        return JsonResult.ok(permissions);
    }


}