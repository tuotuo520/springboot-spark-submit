package com.taihe.springbootsparksubmit.service.impl;


import com.taihe.springbootsparksubmit.dao.RelationTableDao;
import com.taihe.springbootsparksubmit.entity.RelationTable;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.RelationTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表关联字段关系(RelationTable)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:08
 */
@Service("relationTableService")
public class RelationTableServiceImpl implements RelationTableService {
    @Resource
    private RelationTableDao relationTableDao;

    /**
     * 查询出所有关联表和未关联表
     *
     * @param tableId
     * @return
     */
    @Override
    public Result<List<RelationTable>> queryOtherTablesByTableId(RelationTable tableId) {
        return Result.ok(this.relationTableDao.queryOtherTablesByTableId(tableId));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RelationTable queryById(Integer id) {
        return this.relationTableDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<RelationTable> queryAllByLimit(int offset, int limit) {
        return this.relationTableDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param relationTable 实例对象
     * @return 实例对象
     */
    @Override
    public RelationTable insert(RelationTable relationTable) {
        this.relationTableDao.insert(relationTable);
        return relationTable;
    }

    /**
     * 修改数据
     *
     * @param relationTable 实例对象
     * @return 实例对象
     */
    @Override
    public RelationTable update(RelationTable relationTable) {
        this.relationTableDao.update(relationTable);
        return this.queryById(relationTable.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.relationTableDao.deleteById(id) > 0;
    }
}