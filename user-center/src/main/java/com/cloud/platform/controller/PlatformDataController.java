package com.cloud.platform.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.service.PlatformDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * 数据管理
 *
 * @author bjy
 * @date 2019/3/12 0012 14:17
 */
@Slf4j
@RestController
@RequestMapping("/users-anon/platform")
public class PlatformDataController {

    @Autowired
    private PlatformDataService dataService;

    /**
     * 查询内部Aadhaar数据
     *
     * @param params
     * @return
     */
    @GetMapping("/getInnerAadhaar")
    public JsonResult getInnerAadhaar(@RequestParam Map<String, Object> params) {

        return dataService.getInnerAadhaar(params);
    }

    /**
     * 查询外部Aadhaar数据
     *
     * @param params
     * @return
     */
    @GetMapping("/getOuterAadhaar")
    public JsonResult getOuterAadhaar(@RequestParam Map<String, Object> params) {
        return dataService.getOuterAadhaar(params);

    }

    /**
     * 上传之后导入数据到外部Aadhaar
     *
     * @param params
     * @return
     */

    @PostMapping("/uploadOutAadhaar")
    public JsonResult uploadOutAadhaar(@RequestBody Map<String, Object> params) throws IOException {
        return dataService.uploadOutAadhaar(params);
    }

    /**
     * 筛选重复Aadhaar
     *
     * @param params
     * @return
     */
    @GetMapping("/screenOutAadhaar")
    public JsonResult screenOutAadhaar(@RequestParam Map<String, Object> params) {
        return dataService.screenOutAadhaar(params);
    }

    /**
     * 删除重复Aadhaar
     *
     * @param params
     * @return
     */
    @PostMapping("/deleteRepeatAadhaar")
    public JsonResult deleteRepeatAadhaar(@RequestBody Map<String, Object> params) {
        return dataService.deleteRepeatAadhaar(params);
    }
}
