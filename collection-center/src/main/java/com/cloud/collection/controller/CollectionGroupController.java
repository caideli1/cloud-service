package com.cloud.collection.controller;

import com.cloud.collection.service.CollectionGroupService;
import com.cloud.collection.service.CollectionRecordService;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.collection.CollectionGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 15:08
 * 描述：
 */
@Slf4j
@RestController
public class CollectionGroupController {
    @Autowired
    private CollectionRecordService collectionRecordService;

    @Autowired
    private CollectionGroupService collectionGroupService;

    /**
     * 批量分配的时候，获取所有的分组和每个组的组员
     * 获取下拉分组和组员信息,崔收列表的下拉
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @GetMapping("/collection-group/getAllGroupUsers")
    public JsonResult getAllGroupUsers() {
        return JsonResult.ok(collectionGroupService.getAllGroupUsers());
    }

    /**
     * 获取催收分组列表
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @GetMapping("/collection-group/getCollectionGroupList")
    public JsonResult getCollectionGroupList(Integer groupId) {
        return JsonResult.ok(collectionGroupService.getCollectionGroupList(groupId));
    }

    /**
     * 获取下拉分组和组员信息，组信息的下拉
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @GetMapping("/collection-group/getGroupUsers")
    public JsonResult getGroupUsers() {
        return JsonResult.ok(collectionGroupService.getGroupUsers());
    }

    /**
     * 新增的时候，获取未分组和催收员信息
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @GetMapping("/collection-group/getNotGroupUsers")
    public JsonResult getNotGroupUsers() {
        return JsonResult.ok(collectionGroupService.getNotGroupUsers());
    }

    /**
     * 最终保存，新增分组
     * @param collectionGroupVo
     * @param userIdList
     * @return
     */
    @PostMapping("/collection-group/saveGroupAndUsers")
    public JsonResult saveGroupAndUsers(CollectionGroupVo collectionGroupVo, Integer[] userIdList) {
        collectionGroupService.saveGroupAndUsers(collectionGroupVo, Arrays.asList(userIdList));
        return JsonResult.ok();
    }

    /**
     * 编辑获取此组的组员信息
     * @param groupId
     * @return
     */
    @PostMapping("/collection-group/getGroupAndUsersDetail")
    public JsonResult getGroupAndUsersDetail(@RequestParam Integer groupId) {
        return JsonResult.ok(collectionGroupService.getGroupAndUsersDetailList(groupId));
    }

    /**
     * 最终保存，修改分组分配
     * @param groupId
     * @param userIdList
     * @return
     */
    @PostMapping("/collection-group/updateGroupAndUsers")
    public JsonResult updateGroupAndUsers(@RequestParam Integer groupId,@RequestParam Integer[] userIdList) {
        return JsonResult.ok(collectionGroupService.updateGroupAndUsers(groupId, Arrays.asList(userIdList)));
    }

    /**
     *  删除分组
     * @param groupIdList
     * @return
     */
    @PostMapping("/collection-group/batchDeleteGroups")
    public JsonResult batchDeleteGroups(@RequestParam Integer[] groupIdList) {
        return JsonResult.ok(collectionGroupService.batchDeleteGroups(Arrays.asList(groupIdList)));
    }



}
