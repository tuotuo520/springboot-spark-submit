package com.taihe.springbootsparksubmit.dao;

import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.result.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ExcuteRecord)表数据库访问层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:06
 */
@Mapper
public interface ExcuteRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExcuteRecord queryById(Integer id);
    /**
     * 通过ID查询单条数据无tag
     *
     * @param id 主键
     * @return 实例对象
     */
    ExcuteRecord queryByIdWithNoTag(Integer id);


    /**
     * 查询所有
     * @return
     */
    List<ExcuteRecord> queryAlLRecord(ExcuteRecord excuteRecord);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param excuteRecord 实例对象
     * @return 对象列表
     */
    List<ExcuteRecord> queryByBelongTo(ExcuteRecord excuteRecord);

    /**
     * 新增数据
     *
     * @param excuteRecord 实例对象
     * @return 影响行数
     */
    int insert(ExcuteRecord excuteRecord);

    /**
     * 修改数据
     *
     * @param excuteRecord 实例对象
     * @return 影响行数
     */
    int update(ExcuteRecord excuteRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过表名查询数据
     * @return
     */
    List<Object> queryByTableId(ExcuteRecord excuteRecord);

    /**
     * 查询
     * @param tableName
     * @return
     */
    List<String> querySchemaByTable(@Param("tableName") String tableName);

    /**
     * 查询所有的belongto
     * @return
     */
    List<String> queryAllBelongTo(ExcuteRecord excuteRecord);

    /**
     * 根据tag和名称查询第一次执行的id
     * @param excuteRecord
     * @return excuteRecord.getId
     */
    ExcuteRecord queryFirstIdByNameAndTag(ExcuteRecord excuteRecord);

}