package com.cloud.backend.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cloud.backend.dao.*;
import com.cloud.backend.service.SysUserService;
import com.cloud.backend.service.SysPermissionService;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.model.user.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.constants.CredentialType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private SysUserCredentialsDao sysUserCredentialsDao;
    @Autowired
    private SysUserDeptDao sysUserDeptDao;
    @Autowired
    private SysUserPermissionDao sysUserPermissionDao;
    @Autowired
    private SysDeptDao deptDao;


    @Transactional
    @Override
    public void saveSysUserAndSetDept(SysUser sysUser) {
        String username = sysUser.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("账号名不能为空");
        }

        if (PhoneUtil.checkPhone(username)) {// 防止用手机号直接当用户名，手机号要发短信验证
            throw new IllegalArgumentException("用户名要包含英文字符");
        }

        if (username.contains("@")) {// 防止用邮箱直接当用户名，邮箱也要发送验证（暂未开发）
            throw new IllegalArgumentException("用户名不能包含@");
        }

        if (username.contains("|")) {
            throw new IllegalArgumentException("用户名不能包含|字符");
        }

        if (StringUtils.isBlank(sysUser.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (StringUtils.isBlank(sysUser.getEmail())) {
            throw new IllegalArgumentException("邮箱不能为空");
        }

        if (!StringUtils.isBlank(sysUser.getNickname())) {
            sysUser.setNickname(username);
        }

        if (StringUtils.isBlank(sysUser.getNickname())) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (null == sysUser.getComment()) {
            sysUser.setComment("");
        }

        UserCredential userCredential = sysUserCredentialsDao.findByUsername(sysUser.getUsername());
        if (userCredential != null) {
            throw new IllegalArgumentException("账号名已存在");
        }

        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword())); // 加密密码
        sysUser.setEnabled(Boolean.TRUE);
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(sysUser.getCreateTime());

        sysUserDao.save(sysUser);

        sysUserCredentialsDao
                .save(new UserCredential(sysUser.getUsername(), CredentialType.BACKEND_USERNAME.name(), sysUser.getId()));
        log.info("添加用户：{}", sysUser);

        // 设置部门
        Long deptId = sysUser.getDept().getId();
        SysDept dept = deptDao.findById(deptId);
        if (null == dept) {
            throw new IllegalArgumentException("找不到该部门");
        }
        sysUserDeptDao.insertSysUserToDept(sysUser.getId(), deptId);
        log.info("设置user部门：{} - {}", sysUser, deptId);

        // 继承部门权限
        Set<Long> deptPermissionIds = sysPermissionService.getDeptPermissionsByDeptId(deptId).stream().map(SysPermission::getId).collect(Collectors.toSet());
        sysPermissionService.setPermissionsToSysUser(sysUser.getId(), deptPermissionIds);
        log.info("设置user的权限：{} - {}", sysUser, deptPermissionIds);
    }



    @Transactional
    @Override
    public void updateSysUser(SysUser sysUser) {

        String originUserName = sysUserDao.findById(sysUser.getId()).getUsername();
        String newUserName = sysUser.getUsername();

        if ( !StringUtils.isBlank(newUserName) && !StringUtils.equals(originUserName, newUserName) ) {
            UserCredential userCredential = sysUserCredentialsDao.findByUsername(newUserName);
            if (userCredential != null) {
                throw new IllegalArgumentException("新修改的账号名已存在");
            }
            sysUserCredentialsDao.updateUserName(sysUser.getId(), newUserName);
        }

        sysUser.setUpdateTime(new Date());
        if (null == sysUser.getComment()) {
            sysUser.setComment("");
        }
        if (!StringUtils.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }

        sysUserDao.update(sysUser);

        if (null != sysUser.getDept() && null != sysUser.getDept().getId()) {
            Long deptId = sysUser.getDept().getId();
            sysUserDeptDao.updateSysUserToDept(sysUser.getId(), deptId);

            // 更改相应权限
            sysUserPermissionDao.deleteUserAllPermission(sysUser.getId());
            Set<Long> deptPermissionIds = sysPermissionService.getDeptPermissionsByDeptId(deptId).stream().map(SysPermission::getId).collect(Collectors.toSet());
            sysPermissionService.setPermissionsToSysUser(sysUser.getId(), deptPermissionIds);
        }

        log.info("修改用户：{}", sysUser);
    }

    @Transactional
    @Override
    public LoginSysUser findByUsername(String username) {
        SysUser sysUser = sysUserCredentialsDao.findUserByUsername(username);
        if (sysUser != null) {
            LoginSysUser loginAppUser = new LoginSysUser();
            BeanUtils.copyProperties(sysUser, loginAppUser);

            Set<String> sysPermissions = sysPermissionService.getUserPermissionsByUserId(sysUser.getId())
                    .stream().map(i -> i.getPermission()).collect(Collectors.toSet());
            loginAppUser.setPermissions(sysPermissions);
            return loginAppUser;
        }

        return null;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserDao.findById(id);
    }

    /**
     * 给用户设置角色<br>
     * 这里采用先删除老角色，再插入新角色
     */
    @Transactional
    @Override
    public void setRoleToUser(Long id, Set<Long> roleIds) {
        SysUser sysUser = sysUserDao.findById(id);
        if (sysUser == null) {
            throw new IllegalArgumentException("the user does not exist");
        }

        userRoleDao.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                userRoleDao.saveUserRoles(id, roleId);
            });
        }

        log.info("修改用户：{}的角色，{}", sysUser.getUsername(), roleIds);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     */
    @Transactional
    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        SysUser sysUser = sysUserDao.findById(id);
        if (StringUtils.isNoneBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, sysUser.getPassword())) { // 旧密码校验
                throw new IllegalArgumentException("旧密码错误");
            }
        }

        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword)); // 加密密码

        sysUserDao.update(user);
        log.info("修改密码：{}", user);
    }

    @Override
    public Page<SysUser> findUsers(Map<String, Object> params) {
        int total = sysUserDao.count(params);
        List<SysUser> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);

            list = sysUserDao.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public Set<SysRole> findRolesByUserId(Long userId) {
        return userRoleDao.findRolesByUserId(userId);
    }

    /**
     * 绑定手机号
     */
    @Transactional
    @Override
    public void bindingPhone(Long userId, String phone) {
        UserCredential userCredential = sysUserCredentialsDao.findByUsername(phone);
        if (userCredential != null) {
            throw new IllegalArgumentException("手机号已被绑定");
        }

        SysUser sysUser = sysUserDao.findById(userId);
        sysUser.setPhone(phone);

        updateSysUser(sysUser);
        log.info("绑定手机号成功,username:{}，phone:{}", sysUser.getUsername(), phone);

        // 绑定成功后，将手机号存到用户凭证表，后续可通过手机号+密码或者手机号+短信验证码登陆
        sysUserCredentialsDao.save(new UserCredential(phone, CredentialType.APP_SMS.name(), userId));
    }

    @Override
    public void setSysUserIntoDept(Long sysUserId, Long deptId) {
        sysUserDeptDao.insertSysUserToDept(sysUserId, deptId);
    }

    @Transactional
    @Override
    public void deleteSysUser(Long sysUserId) {
        sysUserDeptDao.deleteSysUserFromDept(sysUserId);
        sysUserPermissionDao.deleteUserAllPermission(sysUserId);
        sysUserCredentialsDao.deleteBySysUserId(sysUserId);
        sysUserDao.deleteSysUser(sysUserId);
    }

    @Override
    public void updateEnable(Long sysUserId, Integer status) {
        sysUserDao.upateEnable(status, sysUserId);
    }


    @Override
    public List<SysUser> allSysUser(String nickname, Long sysUserId, Long deptId) {
        return sysUserDao.allSysUsers(nickname, sysUserId, deptId);
    }

    @Override
    public void updateAvatar(Long sysUserId, String avatarUrl) {
         sysUserDao.updateAvatar(sysUserId, avatarUrl);
    }

    @Override
    public SysUser getOneSysUserById(Integer id) {
        return sysUserDao.getOneSysUserById(id);
    }

    @Override
    public List<SysUser> queryNotGroupUsers(List<Integer> groupUserIdList) {
        return sysUserDao.queryNotGroupUsers(groupUserIdList);
    }
}
