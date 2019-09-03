package com.cloud.common.base;


import com.cloud.common.mapper.CommonMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 13:36  2019/7/25
 * Description :
 */
public class BaseServiceImpl<V, P> implements BaseService<V, P> {

    protected CommonMapper<P> commonMapper;

    private Class<V> vClass;

    private Class<P> pClass;

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

    public BaseServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        vClass = (Class<V>) pt.getActualTypeArguments()[0];
        pClass = (Class<P>) pt.getActualTypeArguments()[1];
    }

    public V findById(Serializable id) {
        try {
            P po = commonMapper.selectByPrimaryKey(id);
            if (po == null) {
                return null;
            }
            V vo = null;
            Constructor cons = null;
            try {
                cons = vClass.getDeclaredConstructor();
                cons.setAccessible(true);
                vo = (V)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                vo = vClass.newInstance();
            }
            BeanUtils.copyProperties(po, vo);
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public V findOne(Example example) {
        try {
            P po = commonMapper.selectOneByExample(example);
            if (po == null) {
                return null;
            }
            V vo = null;
            Constructor cons = null;
            try {
                cons = vClass.getDeclaredConstructor();
                cons.setAccessible(true);
                vo = (V)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                vo = vClass.newInstance();
            }
            BeanUtils.copyProperties(po, vo);
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    private List<V> copyList(List<P> sourceList) {
        if (sourceList == null) {
            return null;
        }
        List<V> targetList = new ArrayList<>();
        V vo;
        try {
            for (P po : sourceList) {
                /*try {
                    Method builderMethod = vClass.getMethod("builder");
                    builderMethod.invoke(vClass);
                    setIdMethod.invoke(vo, getIdMethod.invoke(po));
                    vo =
                } catch (NoSuchMethodException e) {

                }*/
                Constructor cons = null;
                try {
                    cons = vClass.getDeclaredConstructor();
                    cons.setAccessible(true);
                    vo = (V)cons.newInstance();
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    vo = vClass.newInstance();
                }
                BeanUtils.copyProperties(po, vo);
                targetList.add(vo);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return targetList;
    }

    public List<V> getList(Example example) {
        try {
            List<P> poList = commonMapper.selectByExample(example);
            return this.copyList(poList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<V> getAll() {
        try {
            List<P> poList = commonMapper.selectAll();
            return copyList(poList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public PageInfo<V> getPage(Example example, int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            Page<P> page = (Page<P>) commonMapper.selectByExample(example);

            List<V> voList = this.copyList(page);

            PageInfo<V> pageInfo = new PageInfo<>();
            pageInfo.setPageNum(page.getPageNum());
            pageInfo.setPageSize(page.getPageSize());
            pageInfo.setTotal(page.getTotal());
            pageInfo.setList(voList);
            return pageInfo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public long count(Example example) {

        try {
            return commonMapper.selectCountByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int insert(V vo) {
        try {
            P po = null;
            Constructor cons = null;
            try {
                cons = pClass.getDeclaredConstructor();
                cons.setAccessible(true);
                po = (P)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                po = pClass.newInstance();
            }
            BeanUtils.copyProperties(vo, po);
            int ret = commonMapper.insertSelective(po);
            Method setIdMethod = null;
            try {
                setIdMethod = vClass.getMethod("setId", Integer.class);
            } catch (NoSuchMethodException e) {
                setIdMethod = vClass.getMethod("setId", Long.class);
            }
            Method getIdMethod = pClass.getMethod("getId", null);
            setIdMethod.invoke(vo, getIdMethod.invoke(po));
            return ret;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }
    public int insertWithoutId(V vo) {
        try {
            P po = null;
            Constructor cons = null;
            try {
                cons = pClass.getDeclaredConstructor();
                cons.setAccessible(true);
                po = (P)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                po = pClass.newInstance();
            }
            BeanUtils.copyProperties(vo, po);
            int ret = commonMapper.insertSelective(po);
            return ret;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public int update(V vo) {
        try {
            P po = null;
            Constructor cons = null;
            try {
                cons = pClass.getDeclaredConstructor();
                cons.setAccessible(true);
                po = (P)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                po = pClass.newInstance();
            }
            BeanUtils.copyProperties(vo, po);
            return commonMapper.updateByPrimaryKeySelective(po);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateByExampleSelective(Example example, V vo) {
        try {
            P po = null;
            Constructor cons = null;
            try {
                cons = pClass.getDeclaredConstructor();
                cons.setAccessible(true);
                po = (P)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                po = pClass.newInstance();
            }
            BeanUtils.copyProperties(vo, po);
            return commonMapper.updateByExampleSelective(po,example);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public long saveOrUpdate(V vo) {
        try {
            Method getIdMethod = vClass.getMethod("getId");
            Object id = getIdMethod.invoke(vo);
            int ret;
            if (id == null) {
                ret = this.insert(vo);
            } else {
                ret = this.update(vo);
            }
            return ret;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void insertBatch(List<V> voList) {
        if (voList == null || voList.size() == 0) {
            return;
        } else {
            try {
                List<P> poList = new ArrayList<P>();
                P po;
                for (V vo : voList) {
                    Constructor cons = null;
                    try {
                        cons = pClass.getDeclaredConstructor();
                        cons.setAccessible(true);
                        po = (P)cons.newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        po = pClass.newInstance();
                    }
                    BeanUtils.copyProperties(vo, po);
                    poList.add(po);
                }
                commonMapper.insertList(poList);
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }
        }
    }

    @Override
    public int insertUseGeneratedKeys(V vo) {
        try {
            P po = null;
            Constructor cons = null;
            try {
                cons = pClass.getDeclaredConstructor();
                cons.setAccessible(true);
                po = (P)cons.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException e) {
                po = pClass.newInstance();
            }
            BeanUtils.copyProperties(vo, po);
            int ret = commonMapper.insertUseGeneratedKeys(po);
            Method setIdMethod = null;
            try {
                setIdMethod = vClass.getMethod("setId", Integer.class);
            } catch (NoSuchMethodException e) {
                setIdMethod = vClass.getMethod("setId", Long.class);
            }
            Method getIdMethod = pClass.getMethod("getId", null);
            setIdMethod.invoke(vo, getIdMethod.invoke(po));
            return ret;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public int deleteById(Serializable id) {
        try {
            return commonMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public int deleteByExample(Example example) {
        try {
            return commonMapper.deleteByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}