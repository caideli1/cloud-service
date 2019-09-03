package com.cloud.common.base;

import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 13:36  2019/7/25
 * Description :
 */
public interface BaseService<V, P> {

    V findById(Serializable id);

    V findOne(Example example);

    List<V> getAll();

    List<V> getList(Example example);

    PageInfo<V> getPage(Example example, int pageNum, int pageSize);

    long count(Example example);

    int insert(V vo);

    /**
     * 无id插入，无需返回id
     * @param vo
     * @return
     */
    int insertWithoutId(V vo);

    void insertBatch(List<V> voList);

    int insertUseGeneratedKeys(V vo);

    int update(V vo);

    int updateByExampleSelective(Example example,V vo);

    long saveOrUpdate(V vo);

    int deleteById(Serializable Id);

    int deleteByExample(Example example);

}
