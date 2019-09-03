package com.cloud.order.dao;

import com.cloud.model.user.UserDataStatisModel;
import org.apache.ibatis.annotations.Mapper;

/**
* @Description:    用户数据统计类
* @Author:         wza
* @CreateDate:     2019/4/1 16:00
* @Version:        1.0
*/
@Mapper
public interface UserDataDao {
    int saveUserData(UserDataStatisModel model);

    UserDataStatisModel findUserDataByImi(String startImi);
}
