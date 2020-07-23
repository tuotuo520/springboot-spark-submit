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
     * @param analysisSchema
     * @return 实例对象
     */
    Result<AnalysisSchema> queryById(AnalysisSchema analysisSchema);

    /**
     * 通过表id查询对应的字段
     *
     * @param analysisSchema
     * @return
     */
    Result<List<AnalysisSchema>> querySchemasByTableId(AnalysisSchema analysisSchema);

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
     *
     * @param analysisSchema
     * @return
     */
    Result<AnalysisSchema> updateSchemasById(AnalysisSchema analysisSchema);

    /**
     * 通过主键删除数据
     *
     * @param analysisSchema
     * @return 是否成功
     */
    boolean deleteById(AnalysisSchema analysisSchema);


}