package com.cloud.platform.service.impl;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.dao.PlatformStartPageDao;
import com.cloud.platform.model.PlatformStartPageModel;
import com.cloud.platform.service.PlatformStartPageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 启动页服务实现类
 * @author bjy
 * @date 2019/3/8 0008 17:22
 */
@Slf4j
@Service
public class PlatformStartPageServiceImpl implements PlatformStartPageService {

    @Autowired
    private PlatformStartPageDao startPageDao;
    
    /**
     * 创建启动页
     * @param model
     * @return
     */
    @Override
    public JsonResult createPlatformStartPage(PlatformStartPageModel model) {
        try{
            startPageDao.createPlatformStartPage(model);
            return JsonResult.ok();
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return JsonResult.errorException("failure:"+e.getLocalizedMessage());
        }
    }

    /**
     * 修改启动页
     * @param model
     * @return
     */
    @Override
    public JsonResult updatePlatformStartPage(PlatformStartPageModel model) {
        try{
            startPageDao.updatePlatformStartPage(model);
            return JsonResult.ok();
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return JsonResult.errorException("failure:"+e.getLocalizedMessage());
        }
    }

    /**
     * 获取启动页
     * @param params
     * @return
     */
    @Override
    public JsonResult getPlatformStartPage(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params,"page"),MapUtils.getIntValue(params,"limit"));
        List<PlatformStartPageModel> list=startPageDao.getPlatformStartPage(params);
        PageInfo<PlatformStartPageModel> page=new PageInfo<>(list);
        return JsonResult.ok(page.getList(),(int)page.getTotal());
    }

    /**
     * 启动页上线或者下线
     * @param params
     * @return
     */
    @Override
    public JsonResult upOrDownStartPage(Map<String, Object> params) {
        int i =startPageDao.upOrDownStartPage(params);
        if(i>0){
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:启动页修改失败！");
    }

    /**
     * 删除启动页
     * @param params
     * @return
     */
    @Override
    public JsonResult deleteStartPage(Map<String, Object> params) {
        int i = startPageDao.deleteStartPage(params);
        if(i>0){
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:删除启动页失败");
    }

	
}
