package com.taihe.springbootsparksubmit.dao;


import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 所有表与库的对应关系(AnalysisTable)表数据库访问层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:04
 */
@Mapper
public interface AnalysisTableDao {

    /**
     * 通过ID查询单条数据
     *
     * @param analysisTable 主键
     * @return 实例对象
     */
    AnalysisTable queryById(AnalysisTable analysisTable);

    /**
     * 通过名称和id查询是否存在
     * @param analysisTable
     * @return
     */
    AnalysisTable queryByNameAndDBid(AnalysisTable analysisTable);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param analysisTable 实例对象
     * @return 对象列表
     */
    List<AnalysisTable> queryAll(AnalysisTable analysisTable);


    /**
     * 查询所有所属库
     * @return
     */
    List<String> queryAllBelongTo(AnalysisTable analysisTable);

    /**
     * 新增数据
     *
     * @param analysisTable 实例对象
     * @return 影响行数
     */
    int insert(AnalysisTable analysisTable);

    /**
     * 修改数据
     *
     * @param analysisTable 实例对象
     * @return 影响行数
     */
    int update(AnalysisTable analysisTable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(AnalysisTable analysisTable);

    /**
     * 通过库id和bi表名查询表信息
   *
     * @param id 主键
     * @return 影响行数
     */
    AnalysisTable queryTableInfoByTableName(AnalysisTable analysisTable);


    /**
     * 判断该字段是否存在
     * @param analysisTable
     * @return
     */
    Integer isTmpTableNameExist(AnalysisTable analysisTable);

}