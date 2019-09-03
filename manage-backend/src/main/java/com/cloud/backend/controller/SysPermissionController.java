package com.cloud.backend.controller;

import com.cloud.backend.service.SysPermissionService;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.SysPermission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 管理后台添加权限
	 * 
	 * @param sysPermission
	 * @return
	 */
	@LogAnnotation(module = "添加权限")
	@PreAuthorize("hasAuthority('back:permission:save') or hasAuthority('permission:all')")
	@PostMapping("/permissions")
	public SysPermission save(@RequestBody SysPermission sysPermission) {
		if (StringUtils.isBlank(sysPermission.getPermission())) {
			throw new IllegalArgumentException("权限标识不能为空");
		}
		if (StringUtils.isBlank(sysPermission.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}

		sysPermissionService.save(sysPermission);

		return sysPermission;
	}

	/**
	 * 管理后台修改权限
	 * 
	 * @param sysPermission
	 */
	@LogAnnotation(module = "修改权限")
	@PreAuthorize("hasAuthority('back:permission:update') or hasAuthority('permission:all')")
	@PutMapping("/permissions")
	public SysPermission update(@RequestBody SysPermission sysPermission) {
		if (StringUtils.isBlank(sysPermission.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}

		sysPermissionService.update(sysPermission);

		return sysPermission;
	}

	/**
	 * 删除权限标识
	 * 
	 * @param id
	 */
	@LogAnnotation(module = "删除权限")
	@PreAuthorize("hasAuthority('back:permission:delete') or hasAuthority('permission:all')")
	@DeleteMapping("/permissions/{id}")
	public void delete(@PathVariable Long id) {
		sysPermissionService.delete(id);
	}

	/**
	 * 查询所有的权限标识
	 */
	@GetMapping("/permissions")
	public Page<SysPermission> findPermissions(@RequestParam Map<String, Object> params) {
		return sysPermissionService.findPermissions(params);
	}

	/**
	 * 查询所有的权限标识-分组
	 */
	@GetMapping("/permissions/group")
	public JsonResult findGroupPermissions() {
		Map<String, List<SysPermission>> res = sysPermissionService.findGroupPermissions();
		return JsonResult.ok(res);
	}


}
