package com.taihe.springbootsparksubmit.dao;


import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * @param id 主键
     * @return 实例对象
     */
    AnalysisTable queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AnalysisTable> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
    List<String> queryAllBelongTo();

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
    int deleteById(Integer id);



}