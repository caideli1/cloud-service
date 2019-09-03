package com.cloud.backend.service;

import com.cloud.backend.model.NoOffModel;

import java.util.List;

public interface NoOffService {
    NoOffModel getNoOffInfo(String nooffName);

    NoOffModel getNoOffIdInfo(Integer id);

    List<NoOffModel> queryNoOffInfo();

    int addNoOffInfo(String nooffcode, String nooffname, String nooffvalue);

    int updateNoOffInfo(Integer id, String nooffcode, String nooffname, String nooffvalue, Integer nooffstatus);
}
