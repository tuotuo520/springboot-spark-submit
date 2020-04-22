package com.taihe.springbootsparksubmit.service.impl;

import com.taihe.springbootsparksubmit.dao.AnalysisSchemaDao;
import com.taihe.springbootsparksubmit.entity.AnalysisSchema;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisSchemaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字段与表的关系(AnalysisSchema)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:03
 */
@Service("analysisSchemaService")
public class AnalysisSchemaServiceImpl implements AnalysisSchemaService {
    @Resource
    private AnalysisSchemaDao analysisSchemaDao;

    /**
     * 通过ID查询该表下的所有字段
     *
     * @param  主键
     * @return 实例对象
     */
    @Override
    public Result<List<AnalysisSchema>> querySchemasByTableId(String tableId) {
        return Result.ok(this.analysisSchemaDao.querySchemasByTableId(tableId));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param tableId@return 实例对象
     */
    @Override
    public Result<AnalysisSchema> queryById(Integer id) {
        return Result.ok(this.analysisSchemaDao.queryById(id));
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<AnalysisSchema> queryAllByLimit(int offset, int limit) {
        return this.analysisSchemaDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param analysisSchema 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnalysisSchema insert(AnalysisSchema analysisSchema) {
        this.analysisSchemaDao.insert(analysisSchema);
        return analysisSchema;
    }

    /**
     * 修改数据
     *
     * @param analysisSchema 实例对象
     * @return 实例对象
     */
    @Override
    public AnalysisSchema update(AnalysisSchema analysisSchema) {
        this.analysisSchemaDao.update(analysisSchema);
        return this.analysisSchemaDao.queryById(analysisSchema.getId());
    }

    /**
     * 通过id更新字段
     *
     * @param id
     * @return
     */
    @Override
    public Result<AnalysisSchema> updateSchemasById(AnalysisSchema id) {
        this.analysisSchemaDao.update(id);
        return Result.ok(this.analysisSchemaDao.queryById(id.getId()));
    }

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById( ) {
        return this.analysisSchemaDao.deleteById() > 0;
    }
}