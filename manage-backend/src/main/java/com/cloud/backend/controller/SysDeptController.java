package com.cloud.backend.controller;

import com.cloud.model.user.SysDept;
import com.cloud.backend.service.SysDeptSerivce;
import com.cloud.backend.service.SysPermissionService;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.SysPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author yoga
 * @Description: SysDeptController
 * @date 2019-05-0518:05
 */
@Slf4j
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {
    @Autowired
    private SysDeptSerivce sysDeptSerivce;
    @Autowired
    private SysPermissionService sysPermissionService;

    @PreAuthorize("hasAuthority('permission:all')")
    @GetMapping("/all")
    public JsonResult allDepts(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int limit) {
        log.info("start api: /sysDept/all");
        return JsonResult.ok(sysDeptSerivce.allDepts());

//        PageHelper.startPage(page, limit);
//        List<SysDept> depts = sysDeptSerivce.allDepts();
//        PageInfo pageInfo = new PageInfo<>(depts);
//        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }

    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/save")
    public JsonResult addDept(@RequestBody SysDept dept) {
        sysDeptSerivce.save(dept);
        return JsonResult.ok("保存成功");
    }

    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/{deptId}/update/enable")
    public JsonResult updateEnableStatus(@PathVariable Long deptId, @RequestParam Integer status) {
        sysDeptSerivce.updateEnable(deptId, status);
        return JsonResult.ok("保存成功");
    }


    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/update")
    public JsonResult updateDept(@RequestBody SysDept dept) {
        sysDeptSerivce.update(dept);
        return JsonResult.ok("保存成功");
    }


    @PreAuthorize("hasAuthority('permission:all')")
    @DeleteMapping("/{deptId}")
    public JsonResult deleteDept(@PathVariable Long deptId) throws RuntimeException {
        int userCount = sysDeptSerivce.userCount(deptId);
        if (userCount > 0) {
            throw new RuntimeException("部门有成员，无法删除");
        }

        sysDeptSerivce.deleteDept(deptId);
        return JsonResult.ok("删除成功");
    }

    /**
     * 管理后台给dept分配权限
     */
    @LogAnnotation(module = "分配权限")
    @PreAuthorize("hasAuthority('permission:all')")
    @PostMapping("/{id}/permissions")
    public JsonResult setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
        sysPermissionService.setPermissionsToSysDept(id, permissionIds);
        return JsonResult.ok("设置成功");
    }

    /**
     * 获取dept的权限
     *
     * @param id
     */
    @PreAuthorize("hasAuthority('permission:all')")
    @GetMapping("/{id}/permissions")
    public JsonResult findPermissionsByRoleId(@PathVariable Long id) {
         Set<SysPermission> res = sysPermissionService.getDeptPermissionsByDeptId(id);
        return JsonResult.ok(res);
    }

}
