package com.taihe.springbootsparksubmit.service;

import com.taihe.springbootsparksubmit.entity.AnalysisSchema;
import com.taihe.springbootsparksubmit.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字段与表的关系(AnalysisSchema)表服务接口
 *
 * @author Grayson
 * @since 2020-04-17 17:44:03
 */
public interface AnalysisSchemaService {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    Result<AnalysisSchema> queryById(Integer tableId );

    /**
     * 通过表id查询对应的字段
     * @param tableId
     * @return
     */
    Result<List<AnalysisSchema>> querySchemasByTableId(String tableId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AnalysisSchema> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param analysisSchema 实例对象
     * @return 实例对象
     */
    AnalysisSchema insert(AnalysisSchema analysisSchema);

    /**
     * 修改数据
     *
     * @param analysisSchema 实例对象
     * @return 实例对象
     */
    AnalysisSchema update(AnalysisSchema analysisSchema);

    /**
     * 通过id更新字段
     * @param id
     * @return
     */
    Result<AnalysisSchema> updateSchemasById(AnalysisSchema id);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    boolean deleteById( );

}