package com.taihe.springbootsparksubmit.dao;

import com.taihe.springbootsparksubmit.entity.AnalysisSchema;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字段与表的关系(AnalysisSchema)表数据库访问层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:03
 */
@Mapper
public interface AnalysisSchemaDao {

    /**
     * 通过ID查询单条数据
     *
     * @param  analysisSchema
     * @return 实例对象
     */
    AnalysisSchema queryById(AnalysisSchema analysisSchema);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param analysisSchema 实例对象
     * @return 对象列表
     */
    List<AnalysisSchema> queryAll(AnalysisSchema analysisSchema);

    /**
     * 新增数据
     *
     * @param analysisSchema 实例对象
     * @return 影响行数
     */
    int insert(AnalysisSchema analysisSchema);

    /**
     * 修改数据
     *
     * @param analysisSchema 实例对象
     * @return 影响行数
     */
    int update(AnalysisSchema analysisSchema);

    /**
     * 通过主键删除数据
     *
     * @param  analysisSchema
     * @return 影响行数
     */
    int deleteById(AnalysisSchema analysisSchema);

    /**
     * 通过表id查询
     * @param analysisSchema
     * @return
     */
    List<AnalysisSchema> querySchemasByTableId(AnalysisSchema analysisSchema);

    /**
     * 通过表数据库名查询字段信息
     * @param analysisSchema
     * @return
     */
    AnalysisSchema querySchemaInfoByTableName(AnalysisSchema analysisSchema);
}