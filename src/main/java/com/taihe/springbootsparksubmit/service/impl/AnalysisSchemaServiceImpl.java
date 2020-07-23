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
     * @param  analysisSchema
     * @return 实例对象
     */
    @Override
    public Result<List<AnalysisSchema>> querySchemasByTableId(AnalysisSchema analysisSchema) {
        return Result.ok(this.analysisSchemaDao.querySchemasByTableId(analysisSchema));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param analysisSchema@return 实例对象
     */
    @Override
    public Result<AnalysisSchema> queryById(AnalysisSchema analysisSchema) {
        return Result.ok(this.analysisSchemaDao.queryById(analysisSchema));
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
        return this.analysisSchemaDao.queryById(analysisSchema);
    }

    /**
     * 通过id更新字段
     *
     * @param id
     * @return
     */
    @Override
    public Result<AnalysisSchema> updateSchemasById(AnalysisSchema analysisSchema) {
        this.analysisSchemaDao.update(analysisSchema);
        return Result.ok(this.analysisSchemaDao.queryById(analysisSchema));
    }

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(AnalysisSchema analysisSchema) {
        return this.analysisSchemaDao.deleteById(analysisSchema) > 0;
    }
}