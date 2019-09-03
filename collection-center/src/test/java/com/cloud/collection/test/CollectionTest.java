package com.cloud.collection.test;

import com.alibaba.fastjson.JSON;
import com.cloud.collection.CollectionCenterApplication;
import com.cloud.collection.service.CollectionGroupService;
import com.cloud.collection.service.CollectionRecordService;
import com.cloud.common.dto.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/25 14:13
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CollectionCenterApplication.class)
public class CollectionTest {

    @Autowired(required = false)
    private CollectionRecordService collectionRecordService;

    @Autowired(required = false)
    private CollectionGroupService collectionGroupService;

    /**
     * 获取所有的分组和每个组的组员
     * @return
     */
    @Test
    public void getAllGroupUsers() {
        System.out.println(JSON.toJSON(JsonResult.ok(collectionGroupService.getAllGroupUsers()).toString()));
    }
}
