package com.taihe.springbootsparksubmit.dao;

import com.taihe.springbootsparksubmit.entity.AnalysisDatabase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 库名(AnalysisDatabase)表数据库访问层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:00
 */
@Mapper
public interface AnalysisDatabaseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AnalysisDatabase queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AnalysisDatabase> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param analysisDatabase 实例对象
     * @return 对象列表
     */
    List<AnalysisDatabase> queryAll(AnalysisDatabase analysisDatabase);

    /**
     * 新增数据
     *
     * @param analysisDatabase 实例对象
     * @return 影响行数
     */
    int insert(AnalysisDatabase analysisDatabase);

    /**
     * 修改数据
     *
     * @param analysisDatabase 实例对象
     * @return 影响行数
     */
    int update(AnalysisDatabase analysisDatabase);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}