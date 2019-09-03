package com.cloud.collection.dao;

import com.cloud.collection.model.*;
import com.cloud.collection.model.CollectionRecord;
import com.cloud.model.collection.CollectionRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 催收dao
 *
 * @author zhujingtao
 * @date 2019/2/26 0026  17:03
 */
@Mapper
public interface CollectionDao {
    /**
     * 查询催收列表
     *
     * @param parameter 前端传入参数
     * @return 催收记录集合
     * @author zhujingtao
     */
    List<CollectionRecordVo> queryCollectionList(Map<String, Object> parameter);

    /**
     * 查找我的催收列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    List<CollectionRecordVo> queryMyCollectionList(Map<String, Object> parameter);

    /**
     * 显示我的催收的所有的总金额 催收订单的未还总额=所有本金+所有逾期；涉及减免通过的金额，不减。
     * @param parameter
     * @return
     */
    BigDecimal sumMyCollectionAllAmount(Map<String, Object> parameter);


    /**
     * 查找资产处置列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    List<CollectionRecordVo> queryEndPropertyList(Map<String, Object> parameter);

    /**
     * 查找主管减免罚息列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    List<CollectionRecordVo> queryInterestReductionList(Map<String, Object> parameter);

    /**
     *
     * @param id
     * @return
     */
    CollectionRecord getCollectionByDueId(@Param("id") Integer id);
    /**
     * 查找主管减免罚息列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    List<CollectionRecordVo> queryAssginList(Map<String, Object> parameter);
    /**
     * 插入指派信息
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数
     * @author zhujingtao
     */
    Integer insertAssginCollection(List<CollectionAssginRecordModel> parameter);

    List<Long> findCollectionIdByComment(@Param("comment") String comment);


    /**
     * 插入罚息申请
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数
     * @author zhujingtao
     */
    Integer insertInterestReduction(Map<String, Object> parameter);


    /**
     * 根据罚息Id 查找 反馈记录列表
     *@author zhujingtao
     * @param collectionId 催收ID
     * @return 反馈记录列表
     */
    List<CollectionAccruedRecordModel> queryAccruedListByCollectionId(@Param("collectionId") String collectionId);

    /**
     * 跟新催收次数加一
     * @author zhujingtao
     * @param id 催收表ID
     * @return 修改记录数
     */
    Integer updateCollectionRecordForAccruedCount(@Param("id") Long id);


    /**
     * 插入反馈信息
     * @author zhujingtao
     * @param params 催收记录
     * @return 插入记录数
     */
    Integer  insertAccrued(Map<String, Object> params);

    /**
     * 修改罚息申请信息
     * @author zhujingtao
     * @param params
     * @return 更新减免审批记录条目数
     */
    Integer updateInterestReduction(Map<String, Object> params);


    /**
     * 更新催收表罚息申请
     * @author zhujingtao
     * @param parameter
     * @return 更新减免审批记录条目数
     */
    Integer updateApplyStationForCollectionRecord(Map<String, Object> parameter);


    /**
     * 查找减免记录列表 信息
     * @author zhujingtao
     * @param collectionId 合同ID
     * @return 减免记录列表
     */
    List<CollectionInterestReductionModel> queryInterestReduction(@Param("collectionId") String collectionId,@Param("applyStatus") Integer applyStatus);

    /**
     * 通过用户名称获取对应的用户ID
     *@author zhujingtao
     * @param username
     * @return
     */
    long findIdByName(@Param("username") String username);


    /**
     * 通过id寻找用户名字
     * @author zhujingtao
     * @param id
     * @return
     */
    String findNameById(@Param("id") Long id);

    /**
     * 通过减息申请 查找催收表ID
     * @author zhujingtao
     * @return 催收记录ID 集合
     */
    List<Long>findCollectionIdByInterestReductionId(@Param("ids") String ids);

    /**
     * 通过催收订单ID 获取用户信息
     * @author zhujingtao
     * @param orderNo 催收订单Id
     * @return 用户信息
     */
    Map<String,Object>queryUserInfo(@Param("orderNo") String orderNo);

    /**
     * 通过前端传入 ID 组 来 处置订单
     * @author zhujingtao
     * @param ids id组
     * @return  修改数量
     */
    Integer   updateHandleStatus(@Param("ids") String ids);


    /**
     * 通过催收订单ID 获取催收组金额信息
     * @author zhujingtao
     * @param collectionId 催收订单ID
     * @return 催收租金额 信息
     */
    Map<String,Object> queryCollectionAmount(@Param("collectionId") String collectionId);

    /**
     * 通过催收订单ID 获取放款基本信息
     * @author zhujingtao
     * @param collectionId 催收订单ID
     * @return 放款基本信息
     */
    Map<String,Object> queryLoanBaseInfo(@Param("collectionId") String collectionId);

    /**
     * 获取 除了拒绝状态的 罚息申请单
     * @param collectionId
     * @return 数量
     */
    int  queryNotRefuseInterestReductionCount(@Param("collectionId") String collectionId);


    /**
     * 获取催收人员集合
     * @author zhujingtao
     * @return 催收人员集合
     */
    List<Map<String,Object>> queryCollectionMan();


    /**
     * 通过前端传入 ID 组 来 处置订单
     * @author zhujingtao
     * @param ids id组
     * @return  修改数量
     */
    int updateCollectionStation(@Param("ids") String ids,@Param("collectionStation") int collectionStation);


    /**
     * 通过用户ID 寻找名称
     * @param contactId
     * @return
     */
     String  queryContactNameByContactId(@Param("contactId") String contactId);

    /**
     * 获取图片信息
     * @param orderNo 用户ID
     * @return
     */
    Map<String,Object> getImgInfo(@Param("orderNo") String orderNo);

    /**
     * 获取 认证信息
     * @param orderNo
     * @return
     */
    Map<String,Object> getConformFile(@Param("orderNo") String orderNo);

    /**
     * 获取催收订单
     * @param collectionId
     * @return
     */
    String getOrderNoById(@Param("id") Integer collectionId);

    /**
     * 获取所有的标签
     * @params
     * @return
     */
    List<CollectionTag>  queryAllTags(Map<String, Object> params);

    @Select("select  *  from   collection_record where id=#{id}")
    CollectionRecord queryCollectionRecordById(@Param("id") String id);

    @Select("select  *  from   collection_tags where id in (${ids})")
   List<CollectionTag>  queryAllTagsByIds(@Param("ids") String ids);

    @Select("select *  from collection_assgin_record    where collection_id=#{collectionId}   ")
    List<CollectionAssginRecordModel> queryAssginRecordModelByCollectionId(@Param("collectionId") String collectionId);

    @Select(" select  *  from collection_accrued_record   " +
            "  where collection_id in  (SELECT  id   from  collection_record  where  due_id=#{dueId})")
    List<CollectionAccruedRecordModel> queryAccruedRecordByDueId(@Param("dueId") Integer dueId);

    List<CollectionRecord>  queryAllCollectionRecordByIds(@Param("ids") String ids);

    String queryUserContactRepairedByUserIdAndContactType(@Param("userId") String userId, @Param("contactType") Integer contactType);

    @Update(" update   collection_record  set follow_up_status=#{followUpStatus},follow_up_time=#{now}" +
            "where  id=#{collectionId}  ")
    int   updateFollowUpStatusByCollectionId(@Param("followUpStatus") String followUpStatus, @Param("collectionId") Long collectionId
            ,@Param("now") Date now);

    int  updateUserLoanStatusByLoanNums(@Param("loanNumbers") String loanNumbers, Integer loanStatus);


    List<CollectionOrderStatusMode> queryOrderStatusList(@Param("mobile") String mobile);


    /**
     * @Author : caideli
     * @Email : 1595252552@qq.com
     * @Date : 19:29  2019/8/8
     * Description :根据条件获取罚息减免记录
     */
    CollectionInterestReductionModel getOneCollectionInterestReductionByParams(Map<String,Object> params);


}
