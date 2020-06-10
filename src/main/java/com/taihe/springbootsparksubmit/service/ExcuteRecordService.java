package com.taihe.springbootsparksubmit.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.result.Result;

import java.util.List;

/**
 * (ExcuteRecord)表服务接口
 *
 * @author Grayson
 * @since 2020-04-17 17:44:07
 */
public interface ExcuteRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExcuteRecord queryById(Integer id);




    /**
     * 新增数据
     *
     * @param excuteRecord 实例对象
     * @return 实例对象
     */
    ExcuteRecord insert(ExcuteRecord excuteRecord);

    /**
     * 修改数据
     *
     * @param excuteRecord 实例对象
     * @return 实例对象
     */
    ExcuteRecord update(ExcuteRecord excuteRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    /**
     * 保存执行记录
     * @param excuteRecord
     * @return
     */
    Result<ExcuteRecord> saveExecuteRecord(ExcuteRecord excuteRecord);


    /**
     * 执行记录
     * @param excuteRecord
     * @return
     */
    Result<ExcuteRecord> executeRecord(ExcuteRecord excuteRecord);

    /**
     * 根据表id查询结果
     * @param id
     * @return
     */
    Result<JSONObject> queryByTableId(ExcuteRecord id);


    /**
     * 查询结果记录中有多少belongTo
     * @return
     */
    Result<List<String>> queryAllBelongTo();


    /**
     * 根据belongTo查询记录
     * @return
     */
    Result<PageInfo<ExcuteRecord>> queryByBelongTo(ExcuteRecord excuteRecord);

}