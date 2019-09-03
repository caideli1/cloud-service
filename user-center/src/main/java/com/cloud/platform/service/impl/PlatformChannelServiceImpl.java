package com.cloud.platform.service.impl;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.dao.PlatformChannelDao;
import com.cloud.platform.model.PlatformChannelCount;
import com.cloud.platform.model.PlatformChannelModel;
import com.cloud.platform.service.PlatformChannelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 渠道服务实现
 * @author bjy
 * @date 2019/3/12 0012 9:41
 */
@Service
public class PlatformChannelServiceImpl implements PlatformChannelService {

    @Autowired
    private PlatformChannelDao channelDao;

    /**
     * 查询推广渠道
     * @param params
     * @return
     */
    @Override
    public JsonResult getPlatformChannel(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params,"page"),MapUtils.getIntValue(params,"limit"));
        List<PlatformChannelModel> list=channelDao.getPlatformChannel(params);
        PageInfo<PlatformChannelModel> page=new PageInfo<PlatformChannelModel>(list);
        return JsonResult.ok(page.getList(),(int)page.getTotal());
    }

    /**
     * 新建推广渠道
     * @param model
     * @return
     */
    @Override
    public JsonResult createPlatformChannel(PlatformChannelModel model) {
        if(channelDao.createPlatformChannel(model)>0){
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:新建推广渠道失败！");
    }

    /**
     * 修改推广渠道
     * @param model
     * @return
     */
    @Override
    public JsonResult modifyPlatformChannel(PlatformChannelModel model) {
        if(channelDao.modifyPlatformChannel(model)>0){
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:修改推广渠道失败!");
    }

    /**
     * 删除推广渠道
     * @param ids
     * @return
     */
    @Override
    public JsonResult deletePlatformChannel(String ids) {
        if(channelDao.deletePlatformChannel(ids)>0){
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:删除推广渠道失败！");
    }

    /**
     * 获取平台渠道统计信息
     * @param params
     * @return
     */
    @Override
    public JsonResult getPlatformCount(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params,"page"),MapUtils.getIntValue(params,"limit"));
        List<PlatformChannelCount> list = channelDao.getPlatformCount(params);
        list.forEach(channel->{
            if(channel.getLoanNo()!=0) {
                channel.setPassedRate(channel.getPassedNo() * 100 / channel.getLoanNo() + "%");
            }
            if(channel.getPassedNo()!=0) {
                channel.setDueRate(channel.getDueNo() * 100 / channel.getPassedNo() + "%");
            }
            if(channel.getPassedNo()!=0) {
                channel.setFirstDueRate(channel.getFirstDueNo() * 100 / channel.getPassedNo() + "%");
            }
        });
        PageInfo<PlatformChannelCount> page=new PageInfo<>(list);
        return JsonResult.ok(page.getList(),(int) page.getTotal());
    }

    /**
     * 上线或者下线渠道
     * @param params
     * @return
     */
    @Override
    public JsonResult upOrDownChannel(Map<String, Object> params) {
        int i=channelDao.upOrDownChannel(params);
        if(i>0){
            return JsonResult.ok();
        }
        return JsonResult.errorMsg("failure:上线或者下线渠道或者失败！");
    }
}
