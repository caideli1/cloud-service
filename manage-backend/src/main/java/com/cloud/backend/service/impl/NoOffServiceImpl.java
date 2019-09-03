package com.cloud.backend.service.impl;

import com.cloud.backend.dao.NoOffDao;
import com.cloud.backend.model.NoOffModel;
import com.cloud.backend.service.NoOffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class NoOffServiceImpl implements NoOffService {
    @Autowired
    private NoOffDao noOffDao;

    @Override
    public List<NoOffModel> queryNoOffInfo() {
        return noOffDao.selectAll();
    }

    @Override
    public int addNoOffInfo(String nooffcode, String nooffname, String nooffvalue) {
        NoOffModel noOffModel = new NoOffModel();
        noOffModel.setCode(nooffcode);
        noOffModel.setName(nooffname);
        noOffModel.setValue(nooffvalue);
        noOffModel.setStatus(0);//0-启用，1-未启用
        noOffModel.setCreateTime(LocalDateTime.now());
        return noOffDao.insert(noOffModel);
    }

    @Override
    public int updateNoOffInfo(Integer id, String nooffcode, String nooffname, String nooffvalue, Integer nooffstatus) {
        NoOffModel noOffModel = new NoOffModel();
        noOffModel.setId(id);
        noOffModel.setCode(nooffcode);
        noOffModel.setName(nooffname);
        noOffModel.setValue(nooffvalue);
        noOffModel.setStatus(nooffstatus);//0-启用，1-未启用
        noOffModel.setUpdateTime(LocalDateTime.now());
        return noOffDao.updateNoOffInfo(noOffModel);
    }

    @Override
    public NoOffModel getNoOffInfo(String nooffName) {
        NoOffModel offModel = new NoOffModel();
        offModel.setName(nooffName);
        offModel.setStatus(0);
        return noOffDao.selectOne(offModel);
    }

    @Override
    public NoOffModel getNoOffIdInfo(Integer id) {
        NoOffModel offModel = new NoOffModel();
        offModel.setId(id);
        return noOffDao.selectOne(offModel);
    }

}
