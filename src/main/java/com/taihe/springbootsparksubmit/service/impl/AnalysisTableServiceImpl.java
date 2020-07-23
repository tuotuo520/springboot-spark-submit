package com.taihe.springbootsparksubmit.service.impl;


import com.taihe.springbootsparksubmit.dao.AnalysisTableDao;
import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 所有表与库的对应关系(AnalysisTable)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:05
 */
@Service("analysisTableService")
public class AnalysisTableServiceImpl implements AnalysisTableService {
    @Resource
    private AnalysisTableDao analysisTableDao;

    /**
     * 通过ID查询单条数据
     *
     * @param analysisTable 主键
     * @return 实例对象
     */
    @Override
    public AnalysisTable queryById(AnalysisTable analysisTable) {
        return this.analysisTableDao.queryById(analysisTable);
    }


    /**
     * 新增数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnalysisTable insert(AnalysisTable analysisTable) {
        this.analysisTableDao.insert(analysisTable);
        return analysisTable;
    }

    /**
     * 修改数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    @Override
    public AnalysisTable update(AnalysisTable analysisTable) {
        this.analysisTableDao.update(analysisTable);
        return this.queryById(analysisTable);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(AnalysisTable analysisTable) {
        return this.analysisTableDao.deleteById(analysisTable) > 0;
    }

    /**
     * 查询所有所属库
     *
     * @return
     */
    @Override
    public Result<List<String>> queryAllBelongTo(AnalysisTable analysisTable) {
        return Result.ok(this.analysisTableDao.queryAllBelongTo(analysisTable));
    }


    /**
     * 查询该库下所有表
     *
     * @param belongTo
     * @return
     */
    @Override
    public Result<List<AnalysisTable>> queryTablesByBelongTo(AnalysisTable belongTo) {
        return Result.ok(this.analysisTableDao.queryAll(belongTo));
    }

    /**
     * 修改数据
     *
     * @param analysisTable@return 实例对象
     */
    @Override
    public Result<AnalysisTable> updateTableDescById(AnalysisTable analysisTable) {
        this.analysisTableDao.update(analysisTable);
        return Result.ok(this.queryById(analysisTable));
    }
}