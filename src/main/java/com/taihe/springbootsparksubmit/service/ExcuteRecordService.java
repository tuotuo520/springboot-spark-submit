package com.taihe.springbootsparksubmit.service;

import com.github.pagehelper.PageInfo;
import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.result.PageRequest;
import com.taihe.springbootsparksubmit.result.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ExcuteRecord> queryAllByLimit(int offset, int limit);

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
     * 查询所有的执行记录
     * @return
     */
    Result<PageInfo<ExcuteRecord>> queryAlLRecord(ExcuteRecord excuteRecord);

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
    List<Object> queryByTableId(Integer id);

}