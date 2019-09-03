package com.cloud.backend.controller;

import com.cloud.backend.model.NoOffModel;
import com.cloud.backend.service.NoOffService;
import com.cloud.common.dto.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backend-anon")
public class NoOffController {

    @Autowired
    private NoOffService noOffService;

    /**
     * 第三方接口开关控制
     *
     * @param nooffname
     * @return
     */
    //开关控制列表
    @GetMapping("/findNoOffInfo")
    public JsonResult findNoOffInfo(@RequestParam("nooffname") String nooffname) {
        if (nooffname.equals("")) {
            List<NoOffModel> list = noOffService.queryNoOffInfo();
            return JsonResult.ok(list, list.size());
        }
        NoOffModel nooffmodel = noOffService.getNoOffInfo(nooffname);
        List<NoOffModel> list = new ArrayList<>();
        if (nooffmodel != null) {
            list.add(nooffmodel);
        }
        return JsonResult.ok(list, list.size());
    }

    //新增第三方接口
    @GetMapping("/addNoOffInfo")
    public JsonResult addNoOffInfo(@RequestParam("nooffcode") String nooffcode, @RequestParam("nooffname") String nooffname, @RequestParam("nooffvalue") String nooffvalue) {
        if (nooffname.equals("")) {
            return JsonResult.errorMsg("Parameter name cannot be empty!");
        }
        NoOffModel offModel = noOffService.getNoOffInfo(nooffname);
        if (offModel != null) {
            return JsonResult.errorMsg("The current parameter name already exists, please confirm and re-enter!");
        }
        int noOffModel = noOffService.addNoOffInfo(nooffcode, nooffname, nooffvalue);
        return JsonResult.ok(noOffModel);
    }

    //修改第三方接口
    @GetMapping("/updateNoOffInfo")
    public JsonResult updateNoOffInfo(@RequestParam("id") Integer id, @RequestParam("nooffcode") String nooffcode, @RequestParam("nooffname") String nooffname, @RequestParam("nooffvalue") String nooffvalue, @RequestParam("nooffstatus") Integer nooffstatus) {
        if (nooffname.equals("")) {
            return JsonResult.errorMsg("Parameter name cannot be empty!");
        }
        NoOffModel offModel = noOffService.getNoOffInfo(nooffname);
        if (offModel != null) {
            if (offModel.getId() != id) {
                return JsonResult.errorMsg("The current parameter name already exists, please confirm and re-enter!");
            }
        }
        int noOffModel = noOffService.updateNoOffInfo(id, nooffcode, nooffname, nooffvalue, nooffstatus);
        return JsonResult.ok(noOffModel);
    }

    //查询是否启用第三方API接口
    @GetMapping("/queryNoOffInfo")
    public boolean queryNoOffInfo(@RequestParam("nooffname") String nooffname) {
        NoOffModel noOffModel = noOffService.getNoOffInfo(nooffname);
        boolean falg = false;
        if (noOffModel == null) {
            return falg;
        }
        if (noOffModel.getStatus() == 0) {
            falg = true;
        }
        return falg;
    }
}
