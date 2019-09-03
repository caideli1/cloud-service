package com.cloud.repayChannelManager;

import com.alibaba.fastjson.JSON;
import com.cloud.backend.ManageBackendApplication;
import com.cloud.backend.model.RepayChannelManager;
import com.cloud.backend.service.RepayChannelManagerService;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.manage.vo.RepayChannelManagerVo;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/25 14:13
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManageBackendApplication.class)
public class RepayChannelManagerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private RepayChannelManagerService repayChannelManagerService;

    @Test
    public void getPage() {
        Example example = new Example(RepayChannelManager.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",true);
        //paramsMap.put("status", true);
        PageInfo<RepayChannelManagerVo> pageInfo = repayChannelManagerService.getPage(example, 1, 10);
        System.out.println(pageInfo.getList().toArray());
    }

    @Test
    public void save() {
        RepayChannelManagerVo repayChannelManagerVo = new RepayChannelManagerVo();
        repayChannelManagerVo.setStatus(true);
        repayChannelManagerVo.setName("测试线上还款3");
        repayChannelManagerVo.setOfflineStatus(false);
        repayChannelManagerVo.setCreateTime(new Date());
        System.out.println(repayChannelManagerService.insert(repayChannelManagerVo));
        RepayChannelManagerVo repayChannelManagerVo1 = new RepayChannelManagerVo();
        repayChannelManagerVo1.setStatus(true);
        repayChannelManagerVo1.setName("测试线下还款3");
        repayChannelManagerVo1.setOfflineStatus(false);
        repayChannelManagerVo1.setAccount("333333333");
        repayChannelManagerVo1.setAccountAddress("33333333333");
        repayChannelManagerVo1.setAccountName("33333333");
        repayChannelManagerVo1.setIfscCode("33333333333");
        repayChannelManagerVo1.setUpi("333333333333");
        repayChannelManagerVo1.setCreateTime(new Date());
        System.out.println(repayChannelManagerService.insert(repayChannelManagerVo1));
    }

    @Test
    public void update() {
        RepayChannelManagerVo repayChannelManagerVo = new RepayChannelManagerVo();
        repayChannelManagerVo.setId(1);
        repayChannelManagerVo.setStatus(false);
        repayChannelManagerVo.setName("测试修改功能2");
        repayChannelManagerVo.setOfflineStatus(true);
        System.out.println(repayChannelManagerService.update(repayChannelManagerVo));
    }

   /* @LogAnnotation(module = "禁用或者激活还款通道")
    @PostMapping("/repayChannelManager/enableOrDisable")
    public JsonResult enableOrDisable(@RequestParam Integer id,@RequestParam Boolean status) {
        RepayChannelManagerVo repayChannelManagerVo = new RepayChannelManagerVo();
        repayChannelManagerVo.setId(id);
        repayChannelManagerVo.setStatus(status);
        return JsonResult.ok(repayChannelManagerService.insert(repayChannelManagerVo));
    }*/
}
