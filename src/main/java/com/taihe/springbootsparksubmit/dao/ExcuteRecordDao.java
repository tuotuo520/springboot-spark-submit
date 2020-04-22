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
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ExcuteRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);



    List<ExcuteRecord> queryAlLRecord(ExcuteRecord excuteRecord);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param excuteRecord 实例对象
     * @return 对象列表
     */
    List<ExcuteRecord> queryAll(ExcuteRecord excuteRecord);

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
    List<Object> queryByTableId(@Param("id") Integer id);


}