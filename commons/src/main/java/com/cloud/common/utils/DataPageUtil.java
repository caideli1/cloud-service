package com.cloud.common.utils;

import java.util.ArrayList;
import java.util.List;

public class DataPageUtil {

  public  static <T>List<T> pageLimit(List<T> dataList,Integer page ,Integer limit )
    {
        List<T> returnList= new ArrayList<>();
        if (page==null||limit==null){
            return  returnList;
        }
        if ((page - 1) * limit >= dataList.size()) {
            return returnList;
        }
        returnList=dataList.subList((page-1) * limit,
                ( page  * limit)<dataList.size()? ( page * limit): (dataList.size()));
        return returnList;

    }
}
