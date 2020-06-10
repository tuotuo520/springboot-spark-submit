package com.taihe.springbootsparksubmit.dao;

import com.taihe.springbootsparksubmit.entity.AnalysisSchema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * @param  主键
     * @return 实例对象
     */
    AnalysisSchema queryById( @Param("id") Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AnalysisSchema> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @param  主键
     * @return 影响行数
     */
    int deleteById( );

    /**
     * 通过表id查询
     * @param tableId
     * @return
     */
    List<AnalysisSchema> querySchemasByTableId(AnalysisSchema tableId);
}