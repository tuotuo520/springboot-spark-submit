package com.taihe.springbootsparksubmit.service;


import com.taihe.springbootsparksubmit.entity.RelationTable;
import com.taihe.springbootsparksubmit.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表关联字段关系(RelationTable)表服务接口
 *
 * @author Grayson
 * @since 2020-04-17 17:44:08
 */
public interface RelationTableService {
    /**
     * 查询出所有关联表和未关联表
     * @param tableId
     * @return
     */
    Result<List<RelationTable>> queryOtherTablesByTableId(RelationTable tableId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RelationTable queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RelationTable> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param relationTable 实例对象
     * @return 实例对象
     */
    RelationTable insert(RelationTable relationTable);

    /**
     * 修改数据
     *
     * @param relationTable 实例对象
     * @return 实例对象
     */
    RelationTable update(RelationTable relationTable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}