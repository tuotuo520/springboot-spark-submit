package com.taihe.springbootsparksubmit.dao;


import com.taihe.springbootsparksubmit.entity.RelationTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 表关联字段关系(RelationTable)表数据库访问层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:08
 */
@Mapper
public interface RelationTableDao {



    List<RelationTable> queryOtherTablesByTableId(RelationTable tableId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RelationTable queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RelationTable> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param relationTable 实例对象
     * @return 对象列表
     */
    List<RelationTable> queryAll(RelationTable relationTable);

    /**
     * 新增数据
     *
     * @param relationTable 实例对象
     * @return 影响行数
     */
    int insert(RelationTable relationTable);

    /**
     * 修改数据
     *
     * @param relationTable 实例对象
     * @return 影响行数
     */
    int update(RelationTable relationTable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}